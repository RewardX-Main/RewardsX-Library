package net.rewardsxdev.rewardsx.api.utils.menu;


import lombok.Getter;
import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.instance.PlatformAdapter;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.RewardsGUIPath;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.RewardsPath;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static net.rewardsxdev.rewardsx.api.utils.color.Color.safeTranslate;
import static org.bukkit.Bukkit.getLogger;

public class LoadMenu {
    private static API api;
    @Getter
    public static final Map<String, MenuConfig> loadedMenus = new HashMap<>();
    @Getter
    public static final Map<String, MenuType> menuTypes = new HashMap<>();


    public LoadMenu(API api1){
        api = api1;
    }

    public static final Map<String, Map<String, ProfileItem>> spigotMenus = new HashMap<>();

    public static Map<String, ProfileItem> getSpigotMenus(String menu) {

        Map<String, ProfileItem> result = spigotMenus.get(menu);

        if (result == null) {
            for (Map.Entry<String, Map<String, ProfileItem>> entry : spigotMenus.entrySet()) {
                String outerKey = entry.getKey();
                Map<String, ProfileItem> innerMap = entry.getValue();

                if (innerMap != null) {
                    for (Map.Entry<String, ProfileItem> innerEntry : innerMap.entrySet()) {
                        String innerKey = innerEntry.getKey();
                        ProfileItem item = innerEntry.getValue();
                    }
                }
            }

        }

        return result;
    }




    public static void loadMenusSpigot(Logger log, File menusFolder) {
        PlatformAdapter adapter = api.getInstance();
        File[] files = menusFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            String fileName = file.getName().replace(".yml", "");

            Menu.setConfigsSpigot((Plugin) adapter.pluginInstance(), fileName, false, null);

            MenuConfig config = Menu.getMenuFileSpigot(fileName);
            if (config == null) {
                continue;
            }

            YamlConfiguration yml = config.getYml();
            if (yml == null) {
                continue;
            }

            // Identificazione del tipo di menu (daily, weekly, ecc.)
            String typeStr = yml.getString(RewardsGUIPath.MENU_TYPE, "unknown").toUpperCase();
            MenuType menuType;
            try {
                menuType = MenuType.valueOf(typeStr);
            } catch (IllegalArgumentException e) {
                menuType = MenuType.UNKNOWN;
            }
            menuTypes.put(fileName.toLowerCase(), menuType);

            String title = safeTranslate(yml.getString(RewardsGUIPath.MENU_TITLE, "Menu"));
            int size = yml.getInt(RewardsGUIPath.MENU_SIZE, 27);

            log.info("Loaded menu: " + fileName);

            Map<String, ProfileItem> items = new HashMap<>();

            if (yml.isConfigurationSection(RewardsGUIPath.CONFIG_PATH)) {
                ConfigurationSection menuSection = yml.getConfigurationSection(RewardsGUIPath.CONFIG_PATH);

                for (String key : menuSection.getKeys(false)) {
                    String name = safeTranslate(yml.getString(RewardsGUIPath.MENU_NAME.replace("%key%", key), "&r"));
                    List<String> lore = yml.getStringList(RewardsGUIPath.MENU_LORE.replace("%key%", key));
                    String rawSlot = Menu.configSpigot.getYml().getString(RewardsGUIPath.MENU_SLOT.replace("%key%", key));
                    List<String> enchantments = yml.getStringList(RewardsGUIPath.MENU_ENCHANTMENTS.replace("%key%", key));
                    boolean hide_enchantments = yml.getBoolean(RewardsGUIPath.MENU_HIDE_ENCHANTMENTS.replace("%key%", key));
                    List<Integer> slots = new ArrayList<>();
                    if (rawSlot != null && !rawSlot.isEmpty()) {
                        String[] parts = rawSlot.split(",");
                        for (String part : parts) {
                            try {
                                slots.add(Integer.parseInt(part.trim()));
                            } catch (NumberFormatException e) {
                                Bukkit.getLogger().warning("[RewardsX] Slot not valid on file: " + part);
                            }
                        }
                    }
                    String materialName = yml.getString(RewardsGUIPath.MENU_ITEM.replace("%key%", key), "STONE");

                    String materialCompletedName = null, streakCondition = null;

                    if(typeStr.equalsIgnoreCase("streak")){

                        materialCompletedName = yml.getString(RewardsGUIPath.MENU_ITEM_COMPLETED.replace("%key%", key), "REDSTONE_BLOCK");
                        streakCondition = yml.getString(RewardsGUIPath.MENU_ITEM_CONDITION.replace("%key%", key), "%rewardsx_hasstreak_LOGIN_1%");

                    }

                    int materialData = yml.getInt(RewardsGUIPath.MENU_ITEM_DATA.replace("%key%", key), 0);

                    // -------- PARSING COMMANDS --------
                    List<String> commands = null;
                    Map<String, List<String>> clickCommands = null;
                    String commandPath = RewardsGUIPath.MENU_ITEM_PERFORM.replace("%key%", key);

                    if (yml.isList(commandPath)) {
                        // Standard (list) commands
                        commands = yml.getStringList(commandPath);
                    } else if (yml.isConfigurationSection(commandPath)) {
                        // Section: left_click/right_click commands
                        clickCommands = new HashMap<>();
                        ConfigurationSection commandSection = yml.getConfigurationSection(commandPath);
                        for (String clickTypeKey : commandSection.getKeys(false)) {
                            List<String> clickList = commandSection.getStringList(clickTypeKey);
                            clickCommands.put(clickTypeKey, clickList);
                        }
                    }

                    // -------- COSTRUISCI ProfileItem --------
                    ProfileItem profileItem = new ProfileItem(
                            name, lore, slots, materialName, materialCompletedName, materialData,
                            commands, enchantments, hide_enchantments, title, size, streakCondition
                    );
                    // Set clickCommands map if present
                    if (clickCommands != null) {
                        profileItem.setClickCommands(clickCommands);
                    }

                    items.put(key, profileItem);
                }
            }

            spigotMenus.put(fileName, items);
        }
    }


    public static MenuType getMenuType(String menuName) {
        return menuTypes.getOrDefault(menuName.toLowerCase(), MenuType.UNKNOWN);
    }



}
