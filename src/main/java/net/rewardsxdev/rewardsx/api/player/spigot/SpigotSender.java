package net.rewardsxdev.rewardsx.api.player.spigot;

import lombok.Getter;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

@Getter
public final class SpigotSender implements RPlayer {

    private final CommandSender handle;

    public SpigotSender(CommandSender handle) {
        this.handle = handle;
    }

    @Override
    public boolean isPlayer() {
        return handle instanceof Player;
    }

    @Override
    public Object getPlayer() {
        if(handle instanceof Player){
            return ((Player) handle).getPlayer();
        } else {
            return null;
        }
    }

    /* ---------- identificazione ---------- */
    @Override
    public UUID getUniqueId() {
        return (handle instanceof Player) ? ((Player) handle).getUniqueId() : null;
    }

    @Override
    public String getPlayerName() {
        return handle.getName();               // "CONSOLE" per la console
    }

    /* ---------- interazione ---------- */
    @Override
    public void sendMessage(String msg) {
        handle.sendMessage(msg);
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if(handle instanceof Player) {
            ((Player) handle).sendTitle(title, subtitle, fadeIn, stay, fadeOut);  // solo titolo, nessun sottotitolo
        }
    }

    @Override
    public void sendTitle(String title, String subtitle) {
        if(handle instanceof Player) {
            ((Player) handle).sendTitle(title, subtitle, 10, 70, 10);  // solo titolo, nessun sottotitolo
        }
    }

    @Override
    public void performCommand(String cmd) {
        if (handle instanceof Player) {
            ((Player) handle).performCommand(cmd); // senza slash
        } else {
            Bukkit.dispatchCommand(handle, cmd);
        }
    }

    @Override
    public void closeInventory(){
        if(handle instanceof Player){
            ((Player) handle).closeInventory();
        }
    }

    @Override
    public void openInventory(Object inv){
        if(handle instanceof Player){
            ((Player) handle).openInventory((Inventory) inv);
        }
    }

    @Override
    public boolean hasPermission(String perm) {
        return handle.hasPermission(perm);     // console ha sempre tutti i permessi
    }

    @Override
    public boolean isOnline() {
        if(handle instanceof Player)
            return ((Player) handle).isOnline();
        return true;
    }
}
