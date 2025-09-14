package net.rewardsxdev.rewardsx.api.player.bungee;

import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/* ---------- RPlayer wrapper ---------- */
@Getter
public final class BungeeSender implements RPlayer {
    private final CommandSender handle;
    public BungeeSender(ProxiedPlayer handle) { this.handle = handle; }

    @Override
    public boolean isPlayer() {
        return handle instanceof ProxiedPlayer;
    }

    @Override
    public Object getPlayer() {
        if(handle instanceof ProxiedPlayer){
            return handle;
        } else {
            return null;
        }
    }

    @Override
    public UUID getUniqueId() {
        return (handle instanceof ProxiedPlayer) ? ((ProxiedPlayer) handle).getUniqueId() : null;
    }
    @Override public String getPlayerName()            { return handle.getName(); }
    @Override
    public void sendMessage(String msg) {
        handle.sendMessage(TextComponent.fromLegacyText(msg));
    }
    @Override
    public void performCommand(String cmd) {
        if (handle instanceof ProxiedPlayer) {
            ((ProxiedPlayer) handle).chat("/" + cmd);
        } else {
            ProxyServer.getInstance()
                    .getPluginManager()
                    .dispatchCommand(handle, cmd);
        }
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public void openInventory(Object inv) {

    }

    @Override
    public void sendTitle(String titleMsg, String subtitleMsg) {
        if (handle instanceof ProxiedPlayer) {
            Title title = ProxyServer.getInstance().createTitle(); // oppure new Title()
            title.title(TextComponent.fromLegacyText(titleMsg));
            title.subTitle(TextComponent.fromLegacyText(subtitleMsg));      // nessun sottotitolo
            title.fadeIn(10).stay(70).fadeOut(10);                 // tempi in tick
            ((ProxiedPlayer) handle).sendTitle(title);
        }
    }

    @Override
    public void sendTitle(String titleMsg, String subtitleMsg, int fadeIn, int stay, int fadeOut) {
        if (handle instanceof ProxiedPlayer) {
            Title title = ProxyServer.getInstance().createTitle(); // oppure new Title()
            title.title(TextComponent.fromLegacyText(titleMsg));
            title.subTitle(TextComponent.fromLegacyText(subtitleMsg));      // nessun sottotitolo
            title.fadeIn(fadeIn).stay(stay).fadeOut(fadeOut);                 // tempi in tick
            ((ProxiedPlayer) handle).sendTitle(title);
        }
    }
    @Override public boolean hasPermission(String perm) { return handle.hasPermission(perm); }

    @Override
    public boolean isOnline() {
        if(handle instanceof ProxiedPlayer)
            return ((ProxiedPlayer) handle).isConnected();
        return true;
    }

}
