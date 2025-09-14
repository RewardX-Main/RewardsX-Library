package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class RewardADSModule {
    private int idReward;
    private String playerName;
    private UUID playerUUID;
    private String rewardName;
    private double cost;
    private Timestamp claimedAt;

    public RewardADSModule(int idReward, String playerName, UUID playerUUID,
                           String rewardName, double cost, Timestamp claimedAt) {
        this.idReward    = idReward;
        this.playerName  = playerName;
        this.playerUUID  = playerUUID;
        this.rewardName  = rewardName;
        this.cost        = cost;
        this.claimedAt   = claimedAt;
    }
}
