package net.rewardsxdev.rewardsx.api.instance.server;

import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.proxy.ProxyServer;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.instance.RServer;
import net.rewardsxdev.rewardsx.api.player.RPlayerOffline;
import net.rewardsxdev.rewardsx.api.player.velocity.VelocitySender;

import java.util.UUID;

/* ---------- RServer implementation ---------- */
public final class VelocityServer implements RServer {

    private final ProxyServer proxy;
    private final EventManager events;

    public VelocityServer(ProxyServer proxy) {
        this.proxy = proxy;
        this.events = proxy.getEventManager();
    }

    @Override
    public RPlayer getPlayerExact(String name) {
        return proxy.getPlayer(name)                     // Optional<Player>
                .map(player -> new VelocitySender(player, proxy))
                .orElse(null);
    }

    @Override
    public RPlayer getPlayer(UUID uuid) {
        return proxy.getPlayer(uuid)                     // Optional<Player>
                .map(player -> new VelocitySender(player, proxy))
                .orElse(null);
    }

    @Override
    public RPlayer getPlayer(String name) {
        return proxy.getPlayer(name)                     // Optional<Player>
                .map(player -> new VelocitySender(player, proxy))
                .orElse(null);
    }

    @Override
    public RPlayerOffline getOfflinePlayer(String name) {
        return null;
    }

    @Override
    public RPlayerOffline getOfflinePlayer(UUID uuid) {
        return null;
    }


    @Override
    public void runSync(Runnable task) {
        proxy.getScheduler()
                .buildTask(proxy.getPluginManager().getPlugin("RewardsX_Velocity"), task)
                .schedule();                      // immediato → thread main
    }

    @Override
    public void dispatchConsoleCommand(String cmd) {
        proxy.getCommandManager().executeAsync(proxy.getConsoleCommandSource(), cmd);
    }
    @Override
    public void registerEvents(Object... listeners) {
        for (Object listener : listeners) {
            events.register(proxy.getPluginManager().getPlugin("RewardsX_Velocity"), listener);  // Velocity accepts raw Object
        }
    }


}
