package net.rewardsxdev.rewardsx.api.features.afk;

import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public interface AFK {
    void updateActivity(Player player);
    long getAfkTime(Player player);

    void setAfk(Player player, boolean afk);
    boolean isAFK(Player player);
    String getName();
    long getAfkTimeout();
    long getLastActivity(Player p);
}
