package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class RewardBase {
    private UUID player;
    private String reward;
    private boolean claimed;
    private Timestamp lastClaimed;

    public RewardBase(UUID player, String reward, boolean claimed, Timestamp lastClaimed) {
        this.player  = player;
        this.reward  = reward;
        this.claimed = claimed;
        this.lastClaimed = lastClaimed;
    }
}
