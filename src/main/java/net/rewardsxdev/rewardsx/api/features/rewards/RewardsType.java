package net.rewardsxdev.rewardsx.api.features.rewards;

import lombok.Getter;

/** Tipologie di premi gestite dal plugin. */
@Getter
public enum RewardsType {
    DAILY("daily"),
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

}


