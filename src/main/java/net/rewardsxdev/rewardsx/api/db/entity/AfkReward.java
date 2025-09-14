package net.rewardsxdev.rewardsx.api.db.entity;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class AfkReward extends RewardBase {
    public AfkReward(UUID player, String reward, boolean claimed, Timestamp lastClaimed) {
        super(player, reward, claimed, lastClaimed);
    }
}

