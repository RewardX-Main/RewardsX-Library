package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
public class StreakReward extends RewardBase {
    private Timestamp lastReward;

    public StreakReward(UUID p, String r, boolean c, Timestamp lastClaimed, Timestamp lr) {
        super(p, r, c, lastClaimed);
        this.lastReward = lr;
    }
}
