package net.rewardsxdev.rewardsx.api.instance;

import com.velocitypowered.api.event.EventManager;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.event.Listener;

import java.sql.SQLException;
import java.util.UUID;

/* ---- servizi di piattaforma ---- */
public interface RServer {
    RPlayer getPlayerExact(String name);
    RPlayer getPlayer(UUID uuid);
    RPlayer getPlayer(String name);
    void runSync(Runnable task);
    void dispatchConsoleCommand(String cmd);
}

