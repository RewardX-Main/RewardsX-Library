package net.rewardsxdev.rewardsx.api.db.entity;

import java.sql.Timestamp;
import java.util.UUID;

public class WeeklyReward  extends RewardBase {
    public WeeklyReward(UUID p, String r, boolean c, Timestamp lastClaimed) {
        super(p, r, c, lastClaimed);
    }
}
