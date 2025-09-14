package net.rewardsxdev.rewardsx.api.features.rewards;

import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.entity.Player;

/**
 * Minimal contract that every reward must fulfill.
 * Concrete implementations (DailyReward, WeeklyReward, …)
 * define the specific delivery logic.
 */

public interface Reward {
    void init();

    /** Return type of reward (daily, weekly, …). */
    RewardsType type();

    /** Give reward to player. */
    void give(RPlayer player, String rewardKey);

    /** Verify that player can claim reward. */
    boolean canClaim(RPlayer player, String rewardId);
}
