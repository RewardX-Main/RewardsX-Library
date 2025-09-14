package net.rewardsxdev.rewardsx.api.addon;

import net.rewardsxdev.rewardsx.api.API;

import java.io.File;

/**
 * Public contract that every RewardsX add-on **must** implement.
 * <p>
 * The core plug-in discovers the class at runtime, registers it in
 * {@code AddonRegistry}, then drives its life-cycle in three steps:
 *
 * <ol>
 *   <li>{@code onLoad()} ‑ called very early (database and services not
 *       yet available). Use it only for lightweight static setup.</li>
 *   <li>{@code onEnable(AddonContext ctx)} ‑ the core is fully ready and
 *       {@code ctx.getDataFolder()} points to
 *       <kbd>plugins/RewardsX/Addons/&lt;AddonName&gt;/</kbd>.</li>
 *   <li>{@code onDisable()} ‑ invoked when the server shuts down or the
 *       plug-in is reloaded. Free resources and unregister listeners
 *       here.</li>
 * </ol>
 */
public interface RewardsXAddon {

    /** A short identifier (directory name). Must be unique. */
    String getAddonName();

    String getVersion();

    /** Optional hook executed before the core finishes loading. */
    default void onLoad() { /* no-op */ }

    /**
     * Main start-up hook.
     *
     * @param ctx run-time information, including the dedicated data folder
     */
    void onEnable(AddonContext ctx);

    /** Called once when RewardsX (or the server) is being disabled. */
    void onDisable();

    /* ------------------------------------------------------------------ */
    /*  Overrides the default Bukkit location so every helper             */
    /*  (saveDefaultConfig, getResource, etc.) uses the add-on directory. */
    /* ------------------------------------------------------------------ */
    File getMainDataFolder();
}
