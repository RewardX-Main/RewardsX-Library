package net.rewardsxdev.rewardsx.api.instance.server;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.instance.RServer;
import net.rewardsxdev.rewardsx.api.player.bungee.BungeeSender;

import java.util.UUID;

/* ---------- RServer implementation ---------- */
public final class BungeeServer implements RServer {
    
    private final ProxyServer proxy;

    public BungeeServer(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Override
    public RPlayer getPlayerExact(String name) {
        ProxiedPlayer p = proxy.getPlayer(name);
        return p != null ? new BungeeSender(p) : null;
    }

    @Override
    public RPlayer getPlayer(UUID uuid) {
        ProxiedPlayer p = proxy.getPlayer(uuid);
        return p != null ? new BungeeSender(p) : null;
    }

    @Override
    public RPlayer getPlayer(String name) {
        ProxiedPlayer p = proxy.getPlayer(name);
        return p != null ? new BungeeSender(p) : null;
    }

    @Override
    public void runSync(Runnable task) {
        proxy.getScheduler().runAsync(proxy.getPluginManager().getPlugin("RewardsX_Bungee"), task);
        // se usi un altro nome di plugin, cambialo di conseguenza
    }

    @Override
    public void dispatchConsoleCommand(String cmd) {
        proxy.getPluginManager().dispatchCommand(proxy.getConsole(), cmd);
    }
    public void registerEvents(Listener... listeners) {
        for(Listener l : listeners) {
            proxy
                    .getPluginManager()
                    .registerListener(proxy.getPluginManager().getPlugin("RewardsX_Bungee"), l);   // var-args helper added in 2021

        }
    }
}
