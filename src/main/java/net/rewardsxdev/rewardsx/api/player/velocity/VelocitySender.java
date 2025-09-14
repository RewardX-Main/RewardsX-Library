package net.rewardsxdev.rewardsx.api.player.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.rewardsxdev.rewardsx.api.player.RPlayer;

import java.time.Duration;
import java.util.UUID;

public final class VelocitySender implements RPlayer {
    @Getter
    private final CommandSource handle;
    private final ProxyServer proxy;

    Title.Times TIMES = Title.Times.times(Duration.ofMillis(10 * 50L),   // fade-in 10 tick
                    Duration.ofMillis(70 * 50L),   // stay    70 tick
                    Duration.ofMillis(10 * 50L));  // fade-out 20 tick

    public VelocitySender(CommandSource handle, ProxyServer proxy) {
        this.handle = handle;
        this.proxy  = proxy;
    }

    @Override
    public boolean isPlayer() {
        return handle instanceof Player;
    }

    @Override
    public Object getPlayer() {
        return handle;
    }

    @Override
    public UUID getUniqueId() {
        return (handle instanceof Player) ? ((Player) handle).getUniqueId() : null;
    }

    @Override
    public String getPlayerName() {
        return (handle instanceof Player)
                ? ((Player) handle).getUsername()
                : "CONSOLE";
    }
    /* ---------- interazione ---------- */
    @Override
    public void sendMessage(String msg) {
        handle.sendMessage(Component.text(msg));
    }

    @Override
    public void sendTitle(String titleMsg, String subtitleMsg, int fadeIn, int stay, int fadeOut) {
        if (handle instanceof Player) {
            Title.Times TIMES =
                    Title.Times.times(Duration.ofMillis(fadeIn * 50L),   // fade-in 10 tick
                            Duration.ofMillis(stay * 50L),   // stay    70 tick
                            Duration.ofMillis(fadeOut * 50L));  // fade-out 20 tick
            Title t = Title.title(Component.text(titleMsg), Component.text(subtitleMsg), TIMES);
            ((Player) handle).showTitle(t);
        }
    }

    @Override
    public void sendTitle(String titleMsg, String subtitleMsg) {
        if (handle instanceof Player) {
            Title t = Title.title(Component.text(titleMsg), Component.text(subtitleMsg), TIMES);
            ((Player) handle).showTitle(t);
        }
    }


    @Override
    public void performCommand(String cmd) {
        if (handle instanceof Player) {
            proxy.getCommandManager().executeAsync(((Player) handle), cmd);
        } else {
            proxy.getCommandManager().executeAsync(handle, cmd);
        }
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public void openInventory(Object inv) {

    }

    @Override
    public boolean hasPermission(String perm) {
        return handle.hasPermission(perm);
    }

    @Override
    public boolean isOnline() {
        return handle instanceof Player && ((Player) handle).isActive();
    }


}
