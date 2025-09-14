package net.rewardsxdev.rewardsx.api.features.rewards;

import org.bukkit.entity.Player;

public interface Playtime {
    void updateActivity(Player player);
    long getPlayTime(Player player);
    void setPlayTime(Player player, long time);
    void setPlayTime(Player player, int time);
    String getName();
    long getLastActivity(Player p);
}
