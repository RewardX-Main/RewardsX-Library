package net.rewardsxdev.rewardsx.api.instance.server;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class VersionSupport {
    private static String name2;
    public static String PLUGIN_TAG_GENERIC_KEY = "RewardsX";
    private final Plugin plugin;

    public VersionSupport(Plugin plugin, String versionName) {
        name2 = versionName;
        this.plugin = plugin;
    }
    /**
     * Get in had item-stack
     */
    public abstract ItemStack getItemInHand(Player p);

    public abstract void sendActionBar(Player player, String message);


    /**
     * Add custom data to an ItemStack
     */
    public abstract ItemStack addCustomData(ItemStack i, String data);

    public abstract ItemStack setTag(ItemStack itemStack, String key, String value);

    public abstract ItemStack createItemStack(String material, int amount, short data);

    /**
     * Get a custom item tag.
     *
     * @return null if not present.
     */
    public abstract String getTag(ItemStack itemStack, String key);

    public abstract Statistic getPlayTimeStatistic();

    public abstract int getVersion();

    public static String getName() {
        return name2;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Get the NBTTag from a RewardsX item
     */
    public abstract String getCustomData(ItemStack i);
}
