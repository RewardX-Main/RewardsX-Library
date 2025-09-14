package net.rewardsxdev.rewardsx.api.events;

import lombok.Getter;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerLangChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    /**
     * -- GETTER --
     *  Check if event is cancelled
     */
    @lombok.Setter
    private boolean cancelled = false;
    /**
     * -- GETTER --
     *  Get Player
     */
    private Player player;
    /**
     * -- GETTER --
     *  Get old Language iso
     */
    private String oldLang, /**
     * -- GETTER --
     *  Get new Language iso
     */
            newLang;

    /**
     * Called when a Player changes his language
     */
    public PlayerLangChangeEvent(Player p, String oldLang, String newLang) {
        this.player = p;
        this.oldLang = oldLang;
        this.newLang = newLang;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

