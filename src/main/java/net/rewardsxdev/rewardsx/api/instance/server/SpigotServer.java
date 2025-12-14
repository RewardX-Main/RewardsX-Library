package net.rewardsxdev.rewardsx.api.instance.server;

import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.instance.RServer;
import net.rewardsxdev.rewardsx.api.player.RPlayerOffline;
import net.rewardsxdev.rewardsx.api.player.spigot.SpigotSender;
import net.rewardsxdev.rewardsx.api.player.spigot.SpigotSenderOffline;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/* ---------- RServer implementation ---------- */
public final class SpigotServer implements RServer {

    private final JavaPlugin plugin;           // istanza principale del tuo plugin

    public SpigotServer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public RPlayer getPlayerExact(String name) {
        Player p = Bukkit.getPlayerExact(name);
        return p != null ? new SpigotSender(p) : null;
    }

    @Override
    public RPlayer getPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        return p != null ? new SpigotSender(p) : null;
    }

    @Override
    public RPlayer getPlayer(String name) {
        Player p = Bukkit.getPlayer(name);
        return p != null ? new SpigotSender(p) : null;
    }

    @Override
    public RPlayerOffline getOfflinePlayer(String name) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(name);
        return p != null ? new SpigotSenderOffline(p) : null;
    }

    @Override
    public RPlayerOffline getOfflinePlayer(UUID uuid) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
        return p != null ? new SpigotSenderOffline(p) : null;
    }

    @Override
    public void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    @Override
    public void dispatchConsoleCommand(String cmd) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
    @Override
    public void registerEvents(Object... listeners) {
        for (Object obj : listeners) {
            if (obj instanceof Listener) {
                Listener listener = (Listener) obj;
                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
            } else {
                // opzionale: debug in caso di tipo errato
                plugin.getLogger().warning("[RewardsX] Tried to register non-listener object: " + obj);
            }
        }
    }

}
