package net.rewardsxdev.rewardsx.api.utils;

import lombok.Getter;
import org.bukkit.Bukkit;

public class Version {

    @Getter
    private static final String version;          // es. v1_20_R1

    static {
        String pkg = Bukkit.getServer().getClass().getPackage().getName();   // org.bukkit.craftbukkit.v1_20_R1
        String[] parts = pkg.split("\\.");

        if (parts.length > 3 && parts[3].startsWith("v")) {
            version = parts[3];                               // v1_20_R1 (Spigot/CraftBukkit)
        } else {
            String mc = Bukkit.getBukkitVersion().split("-")[0]; // --> 1.20.6
            String[] n = mc.split("\\.");                        // [ "1", "20", "6" ]
            version = "v" + n[0] + "_" + n[1] + "_R1";           // --> v1_20_R1
        }
    }

    public static String getForCurrentVersion(String v18, String v12, String v13) {
        switch (getVersion()) {
            case "v1_8_R3":
                return v18;
            case "v1_12_R1":
                return v12;
        }
        return v13;
    }
}
