package net.rewardsxdev.rewardsx.api.features.rewards;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/** Tipologie di premi gestite dal plugin. */
@Getter
public enum RewardsType {
    DAILY("daily"),
    DISCORD("discord"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    STREAK("streak"),
    TRADE("trade"),
    JOBS("jobs"),
    PLAYTIME("playtime"),
    AFK("afk"),
    CHRISTMAS("christmas"),
    HALLOWEEN("halloween"),
    EASTER("easter");

    private final String name;

    RewardsType(String name) {
        this.name = name;
    }

    private static final Map<String, String> CUSTOM_TYPES = new HashMap<>();

    public static void registerCustomType(String key) {
        CUSTOM_TYPES.put(key.toLowerCase(), key);
    }

    public static boolean exists(String key) {
        String lower = key.toLowerCase();
        for (RewardsType type : values()) {
            if (type.name.equalsIgnoreCase(lower)) return true;
        }
        return CUSTOM_TYPES.containsKey(lower);
    }

}


