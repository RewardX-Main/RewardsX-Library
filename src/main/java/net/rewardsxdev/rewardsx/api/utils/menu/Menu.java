package net.rewardsxdev.rewardsx.api.utils.menu;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static net.rewardsxdev.rewardsx.api.API.MENU_GUI_PATH;

public class Menu {
    private static Logger log;
    public static MenuConfig configSpigot;
    private static final Map<String, MenuConfig> configsSpigot = new HashMap<>();

    // Metodo per Spigot
    public static void setConfigsSpigot(org.bukkit.plugin.Plugin plugin, String fileName, boolean type, String typeName){
        log = plugin.getLogger();
        File menusFolder = new File(plugin.getDataFolder(), MENU_GUI_PATH.replace("/", ""));
        configSpigot = new MenuConfig(plugin, fileName, menusFolder.getPath() + "/", type, typeName);
        configsSpigot.put(fileName, configSpigot);
    }
    public static void createMenuFile(org.bukkit.plugin.Plugin plugin, String fileName, boolean type, String typeName) {
        log = plugin.getLogger();
        File menusFolder = new File(plugin.getDataFolder(), MENU_GUI_PATH.replace("/", ""));
        if (!menusFolder.exists()) {
            menusFolder.mkdirs();
        }
        if(!configsSpigot.containsKey(fileName)) {
            MenuConfig configSpigot = new MenuConfig(plugin, fileName, menusFolder.getPath() + "/", type, typeName);
            configsSpigot.put(fileName, configSpigot);
        } else {
            log.warning(fileName + " menu already exist! Skipping creation");
        }
    }


    public static MenuConfig getMenuFileSpigot(String fileName) {
        return configsSpigot.get(fileName);
    }

}
