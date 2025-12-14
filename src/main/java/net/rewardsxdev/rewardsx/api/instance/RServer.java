package net.rewardsxdev.rewardsx.api.instance;

import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.player.RPlayerOffline;

import java.util.UUID;

/* ---- servizi di piattaforma ---- */
public interface RServer {
    RPlayer getPlayerExact(String name);
    RPlayer getPlayer(UUID uuid);
    RPlayer getPlayer(String name);
    RPlayerOffline getOfflinePlayer(String name);
    RPlayerOffline getOfflinePlayer(UUID uuid);
    void runSync(Runnable task);
    void dispatchConsoleCommand(String cmd);
    void registerEvents(Object... listeners);
}

