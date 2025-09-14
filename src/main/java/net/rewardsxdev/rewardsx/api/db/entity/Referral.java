package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class Referral {
    private UUID playerHost;
    private int hostCountInvited;
    private UUID playerGuest;
    private String refCodeHost;
    private String targetName;
    private boolean accepted;

    public Referral(UUID playerHost, int hostCountInvited,
                    UUID playerGuest, String refCodeHost, String targetName, boolean accepted) {
        this.playerHost        = playerHost;
        this.hostCountInvited  = hostCountInvited;
        this.playerGuest       = playerGuest;
        this.refCodeHost       = refCodeHost;
        this.targetName        = targetName;
        this.accepted          = accepted;
    }

}
