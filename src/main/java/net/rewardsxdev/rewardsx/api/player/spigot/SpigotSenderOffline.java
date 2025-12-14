package net.rewardsxdev.rewardsx.api.player.spigot;

import lombok.Getter;
import net.rewardsxdev.rewardsx.api.player.RPlayerOffline;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Getter
public final class SpigotSenderOffline implements RPlayerOffline {

    private final OfflinePlayer handle;

    public SpigotSenderOffline(OfflinePlayer handle) {
        this.handle = handle;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public Object getPlayer() {
        return handle.getPlayer();
    }

    /* ---------- identificazione ---------- */
    @Override
    public UUID getUniqueId() {
        return handle.getUniqueId();
    }

    @Override
    public String getPlayerName() {
        return handle.getName();               // "CONSOLE" per la console
    }

    @Override
    public void sendMessage(String msg) {

    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {

    }

    @Override
    public void sendTitle(String title, String subtitle) {

    }

    @Override
    public void performCommand(String cmd) {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public void openInventory(Object inv) {

    }

    @Override
    public boolean hasPermission(String perm) {
        return false;
    }

    @Override
    public boolean isOnline() {
        return false;
    }

}
