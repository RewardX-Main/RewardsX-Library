package net.rewardsxdev.rewardsx.api.addon;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;

/**
 * Base-class for Bukkit / Paper add-ons that also want the full
 * {@link JavaPlugin} feature-set (events, commands, scheduler…).
 *
 * The RewardsX core injects the correct data folder
 * <pre>
 * plugins/RewardsX/Addons/&lt;AddonName&gt;/
 * </pre>
 * before calling {@code onEnable(AddonContext)}.
 *
 * Add-on authors simply extend this class and register the instance in a
 * static block:
 *
 * <pre>
 * public final class MyAddon extends AddonJavaPlugin {
 *     static { AddonRegistry.register(new MyAddon()); }
 *
 *     // …
 * }
 * </pre>
 */
public abstract class AddonJavaPlugin extends JavaPlugin implements RewardsXAddon {

    /** Set by the core plug-in; never null after registration succeeds. */
    private Path redirectedFolder;

    /* ------------------------------------------------------------------ */
    /*  Internal – called only by RewardsX core, do NOT call yourself.    */
    /* ------------------------------------------------------------------ */
    void _setAddonFolder(Path folder) {
        this.redirectedFolder = folder;
    }

    /* ------------------------------------------------------------------ */
    /*  Overrides the default Bukkit location so every helper             */
    /*  (saveDefaultConfig, getResource, etc.) uses the add-on directory. */
    /* ------------------------------------------------------------------ */
    @Override
    public File getMainDataFolder() {
        return redirectedFolder != null
                ? redirectedFolder.toFile()
                : super.getDataFolder();          // fallback (should never happen)
    }


}
