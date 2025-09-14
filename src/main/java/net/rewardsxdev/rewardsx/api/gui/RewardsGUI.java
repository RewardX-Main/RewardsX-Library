package net.rewardsxdev.rewardsx.api.gui;

import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.sessions.TradeSession;
import net.rewardsxdev.rewardsx.api.spigot.configuration.ConfigManager;
import net.rewardsxdev.rewardsx.api.utils.menu.MenuType;
import net.rewardsxdev.rewardsx.api.utils.menu.ProfileItem;
import org.bukkit.entity.Player;

import java.util.List;

public interface RewardsGUI {
    void openGui(RPlayer player, String menuName);
    List<ProfileItem> createProfileItems(ConfigManager config, int size);
    void openGUIByType(RPlayer p, MenuType type);

    void openTradeInventory(Player player, TradeSession session, boolean isOwner);
}
