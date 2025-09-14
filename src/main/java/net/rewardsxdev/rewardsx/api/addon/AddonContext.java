package net.rewardsxdev.rewardsx.api.addon;

import net.rewardsxdev.rewardsx.api.API;

import java.nio.file.Path;

/**
 * Runtime information passed by the core RewardsX plug-in to every add-on.
 * <p>Implementations are supplied only by the core; add-ons must never create
 * their own {@code AddonContext} instances.</p>
 */
@FunctionalInterface
public interface AddonContext {

    /**
     * Absolute path to the add-on’s dedicated data folder:
     * <pre>
     * plugins/RewardsX/Addons/&lt;AddonName&gt;/
     * </pre>
     * Every file the add-on writes (configs, databases, logs…) should live
     * inside this directory.
     *
     * @return a {@link Path} pointing to the add-on data folder
     */
    Path getMainDataFolder();
}
