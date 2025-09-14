package net.rewardsxdev.rewardsx.api.addon;

import lombok.Getter;
import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.instance.PlatformAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.rewardsxdev.rewardsx.api.API.ADDONS_PATH;

/** Central registry used by RewardsX to manage every add-on. */
public final class AddonRegistry {

    /* ────────────────  FIELDS  ──────────────── */
    private static Path addonsRoot;                        // …/RewardsX/Addons
    private final API api;
    private static PlatformAdapter adapter;

    @Getter
    private static final Map<String, RewardsXAddon> ADDONS = new ConcurrentHashMap<>();
    private static final Map<RewardsXAddon, AddonContext> CTX = new HashMap<>();

    /* holds add-ons that registered *before* setup() */
    private static final List<RewardsXAddon> PENDING = new ArrayList<>();

    public AddonRegistry(API api) {
        this.api = api;
        adapter = api.getInstance();
    }  // no-instantiation

    /* ─────────────────────────────────────────────── */
    /* 1) CALLED ONCE BY THE CORE                     */
    /* ─────────────────────────────────────────────── */
    public static synchronized void setup(Path coreData) throws IOException {
        long t0 = System.nanoTime();
        adapter.debug("[Registry] setup(coreData=" + coreData + ") invoked");

        if (addonsRoot != null) {
            adapter.debug("[Registry] setup() skipped: already initialised (" + addonsRoot + ")");
            return;
        }

        Objects.requireNonNull(coreData, "core data folder");
        addonsRoot = coreData.resolve(ADDONS_PATH.replace("/", ""));
        Files.createDirectories(addonsRoot);
        adapter.debug("[Registry] Add-ons root set to " + addonsRoot);

        // carica tutto ciò che esiste già nella cartella
        AddonLoader.loadAll(addonsRoot);

        /* flush early registrations */
        if (!PENDING.isEmpty()) {
            adapter.debug("[Registry] Flushing "+ PENDING.size() + " add-on(s) queued during bootstrap…");
            PENDING.forEach(AddonRegistry::internalRegister);
            PENDING.clear();
        }

        adapter.debug("[Registry] setup() completed in " + (System.nanoTime() - t0) / 1_000_000D + " ms");
    }

    /* ─────────────────────────────────────────────── */
    /* 2) INVOKED BY EVERY ADD-ON                      */
    /* ─────────────────────────────────────────────── */
    public static synchronized void register(RewardsXAddon addon) {
        Objects.requireNonNull(addon, "addon");
        adapter.debug("[Registry] register(" + addon.getAddonName() +")");

        /* setup not ready yet → queue the add-on and return */
        if (addonsRoot == null) {
            PENDING.add(addon);
            adapter.debug("[Registry] Add-on " + addon.getAddonName() + " queued (setup not finished). Pending=" +PENDING.size());
            return;
        }
        internalRegister(addon);
    }

    /* ─────────────────────────────────────────────── */
    /* 3) COMMON INTERNAL LOGIC                       */
    /* ─────────────────────────────────────────────── */
    private static void internalRegister(RewardsXAddon addon) {
        String key = addon.getAddonName().toLowerCase(Locale.ROOT);
        adapter.debug("[Registry] internalRegister(" + addon.getAddonName() +") key=" + key);

        // evita duplicati
        if (ADDONS.putIfAbsent(key, addon) != null) {
            adapter.error("[Registry] Duplicate add-on " + addon.getAddonName() + " ignored (already present)");
            return;
        }

        // crea la cartella dedicata
        Path folder = addonsRoot.resolve(addon.getAddonName());
        try {
            Files.createDirectories(folder);
        } catch (IOException ioe) {
            adapter.error("[Registry] Cannot create folder " + folder + " for add-on " + addon.getAddonName() + ioe);
        }

        // salva il contesto
        CTX.put(addon, () -> folder);

        adapter.info("[Registry] Add-on " + addon.getAddonName() + " v" + addon.getVersion() + " registered – total loaded: " + ADDONS.size());
    }

    /* ─────────────────────────────────────────────── */
    /* 4) RUNTIME UTILITIES                           */
    /* ─────────────────────────────────────────────── */

    /** Rescans the Addons folder and loads any new JAR dropped in at runtime. */
    public static synchronized void reloadAll() {
        if (addonsRoot == null) {
            adapter.info("[Registry] reloadAll() called before setup – ignored");
            return;
        }
        adapter.debug("[Registry] Reloading add-ons from " + addonsRoot);
        AddonLoader.loadAll(addonsRoot);
        dumpState();
    }

    /** Prints a diagnostic dump of the current internal state. */
    public static void dumpState() {
        adapter.info("──── AddonRegistry STATE DUMP ───────────────────────────────");
        adapter.info("root path   : " +  addonsRoot);
        adapter.info("add-ons     : " + ADDONS.size());
        ADDONS.values().forEach(a ->
                adapter.info("  • " + a.getAddonName() + " v" + a.getVersion()));
        adapter.info("pending     : " + PENDING.size());
        PENDING.forEach(a -> adapter.info("  • pending " + a.getAddonName()));
        adapter.info("─────────────────────────────────────────────────────────────");
    }

    /* ─────────────────────────────────────────────── */
    /* 5) READ-ONLY ACCESSORS                         */
    /* ─────────────────────────────────────────────── */
    public static Collection<RewardsXAddon> getAll() {
        return Collections.unmodifiableCollection(ADDONS.values());
    }

    public static AddonContext getContext(RewardsXAddon addon) {
        return CTX.get(addon);
    }
}
