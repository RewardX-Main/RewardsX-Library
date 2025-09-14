package net.rewardsxdev.rewardsx.api.instance;

public class Instance {

    public enum InstanceType {
        SPIGOT("Spigot"),
        PAPER("Paper"),
        BUNGEECORD("BungeeCord"),
        VELOCITY("Velocity"),
        UNKNOWN("Unknown");

        private final String displayName;

        InstanceType(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }


    private static InstanceType instanceType = null;

    public static InstanceType getInstance() {
        if (instanceType != null) return instanceType;

        try {
            Class.forName("org.bukkit.Bukkit");
            instanceType = InstanceType.SPIGOT;
            return instanceType;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            instanceType = InstanceType.PAPER;
            return instanceType;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("net.md_5.bungee.api.plugin.Plugin");
            instanceType = InstanceType.BUNGEECORD;
            return instanceType;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("com.velocitypowered.api.plugin.PluginContainer");
            instanceType = InstanceType.VELOCITY;
            return instanceType;
        } catch (ClassNotFoundException ignored) {}

        instanceType = InstanceType.UNKNOWN;
        return instanceType;
    }

}
