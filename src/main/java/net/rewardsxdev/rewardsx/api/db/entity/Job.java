package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
@Setter
@Getter
public class Job {
    private UUID playerUUID;
    private String choiceType;
    private int levelJob;
    private boolean started;

    public Job(UUID playerUUID, String choiceType, int levelJob, boolean started) {
        this.playerUUID     = playerUUID;
        this.choiceType = choiceType;
        this.levelJob   = levelJob;
        this.started = started;
    }

}
