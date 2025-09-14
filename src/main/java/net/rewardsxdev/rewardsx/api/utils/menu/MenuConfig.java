package net.rewardsxdev.rewardsx.api.utils.menu;

import net.rewardsxdev.rewardsx.api.spigot.configuration.ConfigManager;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.RewardsGUIPath;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.rewardsxdev.rewardsx.api.utils.Version.getForCurrentVersion;
import static net.rewardsxdev.rewardsx.api.utils.color.Color.safeTranslate;

public class MenuConfig extends ConfigManager {
    public MenuConfig(Plugin plugin, String name, String dirPath, boolean hasType, String typeName) {
        super(plugin, name, dirPath);

        YamlConfiguration yml = getYml();

        if (yml.getKeys(true).isEmpty()) {
            if (hasType) {
                yml.addDefault(RewardsGUIPath.MENU_TYPE, typeName);
            }

            if (typeName.equalsIgnoreCase("daily") || typeName.equalsIgnoreCase("weekly")
                    || typeName.equalsIgnoreCase("monthly")) {

                String capitalizedTypeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase();
                yml.addDefault(RewardsGUIPath.MENU_TYPE, typeName);
                yml.addDefault(RewardsGUIPath.MENU_TITLE, "&8" + capitalizedTypeName + " Menu");
                yml.addDefault(RewardsGUIPath.MENU_SIZE, 45);

                String[] keys = {"10", "11", "12", "13", "14", "15", "16"};
                String[] names = {
                        "&aReward 1", "&bReward 2", "&cReward 3",
                        "&dReward 4", "&eReward 5", "&6Reward 6", "&7Reward 7"
                };
                List<String>[] lores = new List[]{
                        Arrays.asList("Receive 10 coins"),
                        Arrays.asList("Receive 1 gem"),
                        Arrays.asList("Receive 5 XP points"),
                        Arrays.asList("Receive an XP booster"),
                        Arrays.asList("Receive a special item"),
                        Arrays.asList("Receive a random prize"),
                        Arrays.asList("Bonus reward!")
                };
                String[] materials = {
                        getMaterial("diamond_block"),
                        getMaterial("emerald_block"),
                        getMaterial("gold_block"),
                        getMaterial("lapis_block"),
                        getMaterial("redstone_block"),
                        getMaterial("coal_block"),
                        getMaterial("iron_block")
                };
                int[] slots = {10, 11, 12, 13, 14, 15, 16};
                int[] data = {0, 0, 0, 0, 0, 0, 0};
                boolean[] hideEnchant = {false, false, false, false, false, false, false};
                List<String>[] enchantments = new List[]{
                        Arrays.asList("DURABILITY;1"),
                        Arrays.asList(),
                        Arrays.asList("SHARPNESS;2"),
                        Arrays.asList(),
                        Arrays.asList(),
                        Arrays.asList(),
                        Arrays.asList()
                };
                // ----- Costruzione comandi -----
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    yml.addDefault(RewardsGUIPath.MENU_NAME.replace("%key%", key), names[i]);
                    yml.addDefault(RewardsGUIPath.MENU_LORE.replace("%key%", key), lores[i]);
                    yml.addDefault(RewardsGUIPath.MENU_SLOT.replace("%key%", key), slots[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM.replace("%key%", key), materials[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_DATA.replace("%key%", key), data[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ENCHANTMENTS.replace("%key%", key), enchantments[i]);
                    yml.addDefault(RewardsGUIPath.MENU_HIDE_ENCHANTMENTS.replace("%key%", key), hideEnchant[i]);
                    // --- classic command list ---
                    List<String> commandsList = Arrays.asList("[" + typeName.toLowerCase() + "reward] reward" + (i + 1));
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_PERFORM.replace("%key%", key), commandsList);
                }

            } else if (typeName.equalsIgnoreCase("job")) {

                String capitalizedTypeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase();
                yml.addDefault(RewardsGUIPath.MENU_TYPE, typeName);
                yml.addDefault(RewardsGUIPath.MENU_TITLE, "&8" + capitalizedTypeName + " Menu");
                yml.addDefault(RewardsGUIPath.MENU_SIZE, 45);
                String[] keys = {
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                };
                String[] jobNames = {
                        "breeder", "builder", "crafter", "eater", "enchanter", "fisherman", "killer",
                        "milker", "minator", "shearer", "tamer", "walker"
                };
                // Puoi aggiungere altri job qui, garantendo corrispondenza tra keys e jobNames

                for (int i = 0; i < jobNames.length; i++) {
                    String key = keys[i];
                    String jobName = jobNames[i];
                    yml.addDefault(RewardsGUIPath.MENU_NAME.replace("%key%", key), "&e" + jobName.substring(0, 1).toUpperCase() + jobName.substring(1));
                    List<String> lore = Arrays.asList(
                            "",
                            "&fLevel: &b%rewardsx_joblevel_" + jobName + "%",
                            "&fCompletion: &a%rewardsx_jobpercentraw_" + jobName + "% / 100%",
                            "&fStarted: %rewardsx_jobstarted_" + jobName + "%"
                    );
                    yml.addDefault(RewardsGUIPath.MENU_LORE.replace("%key%", key), lore);
                    yml.addDefault(RewardsGUIPath.MENU_SLOT.replace("%key%", key), 9 + i);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM.replace("%key%", key), getMaterial("diamond_pickaxe"));
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_DATA.replace("%key%", key), 0);
                    yml.addDefault(RewardsGUIPath.MENU_ENCHANTMENTS.replace("%key%", key), Arrays.asList());
                    yml.addDefault(RewardsGUIPath.MENU_HIDE_ENCHANTMENTS.replace("%key%", key), false);

                    // left/right click
                    Map<String, List<String>> clickCommands = new LinkedHashMap<>();
                    clickCommands.put("left_click", Arrays.asList("[jobstart] " + jobName, "[close]"));
                    clickCommands.put("right_click", Arrays.asList("[jobleave] " + jobName, "[close]"));
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_PERFORM.replace("%key%", key), clickCommands);
                }
            } else if (typeName.equalsIgnoreCase("streak")) {

                String capitalizedTypeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase();
                yml.addDefault(RewardsGUIPath.MENU_TYPE, typeName);
                yml.addDefault(RewardsGUIPath.MENU_TITLE, "&8" + capitalizedTypeName + " Menu");
                yml.addDefault(RewardsGUIPath.MENU_SIZE, 45);

                String[] keys = {"1", "2", "5", "10"};
                String[] names = {
                        "1° Day",
                        "2° Day",
                        "5° Day",
                        "10° Day"
                };
                String[] conditions = {"LOGIN", "LOGIN", "EAT", "LOGIN"};
                int[] amounts = {1, 2, 5, 1};
                String[] rewardsDesc = {
                        "1 Diamond",
                        "2 Diamonds",
                        "5 Diamonds & 1 Emerald",
                        "Elytra"
                };
                String[] materials = {
                        getMaterial("diamond"),
                        getMaterial("diamond"),
                        getMaterial("emerald"),
                        getMaterial("elytra")
                };
                int[] slots = {10, 12, 14, 16};

                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    String condition = conditions[i];

                    // Usa il placeholder personalizzato: %rewardsx_hasstreak_<condition>_<key>%
                    // Es: %rewardsx_hasstreak_LOGIN_1%
                    String claimPlaceholder = "%rewardsx_hasstreak_" + condition + "_" + key + "%";

                    List<String> lore = Arrays.asList(
                            "",
                            "&7Requirement: &e" + names[i],
                            "&7Action: &a" + condition,
                            "&7Reward: &b" + rewardsDesc[i],
                            "",
                            "&fCompleted: &e" + claimPlaceholder.replace("%key%", key).replace("%condition%", condition)
                    );

                    yml.addDefault(RewardsGUIPath.MENU_NAME.replace("%key%", key), names[i]);
                    yml.addDefault(RewardsGUIPath.MENU_LORE.replace("%key%", key), lore);
                    yml.addDefault(RewardsGUIPath.MENU_SLOT.replace("%key%", key), slots[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM.replace("%key%", key), materials[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_COMPLETED.replace("%key%", key), "REDSTONE_BLOCK");
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_CONDITION.replace("%key%", key), claimPlaceholder.replace("%key%", key).replace("%condition%", condition));
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_DATA.replace("%key%", key), 0);
                    yml.addDefault(RewardsGUIPath.MENU_ENCHANTMENTS.replace("%key%", key), Arrays.asList());
                    yml.addDefault(RewardsGUIPath.MENU_HIDE_ENCHANTMENTS.replace("%key%", key), false);
                    // Nessun comando su click!
                }
            } else if (typeName.equalsIgnoreCase("trade")) {

                yml.addDefault(RewardsGUIPath.MENU_TYPE, typeName);
                yml.addDefault(RewardsGUIPath.MENU_SIZE, 54);
                yml.addDefault(RewardsGUIPath.MENU_TRADE_SEPARATOR_ITEM, getForCurrentVersion("STAINED_GLASS_PANE", "STAINED_GLASS_PANE", "BLACK_STAINED_GLASS_PANE"));
                yml.addDefault(RewardsGUIPath.MENU_TRADE_SEPARATOR_ITEM_DATA, 0);
                yml.addDefault(RewardsGUIPath.MENU_TRADE_ACCEPT_BUTTON, getForCurrentVersion("WOOL", "WOOL", "GREEN_WOOL"));
                yml.addDefault(RewardsGUIPath.MENU_TRADE_ACCEPT_BUTTON_DATA, 0);
                yml.addDefault(RewardsGUIPath.MENU_TRADE_CANCEL_BUTTON, getForCurrentVersion("WOOL", "WOOL", "RED_WOOL"));
                yml.addDefault(RewardsGUIPath.MENU_TRADE_CANCEL_BUTTON_DATA, 0);
                yml.addDefault(RewardsGUIPath.MENU_TRADE_CANCEL_BUTTON_SLOT, 48);
                yml.addDefault(RewardsGUIPath.MENU_TRADE_CONFIRM_BUTTON, getForCurrentVersion("STONE_BUTTON", "STONE_BUTTON", "STONE_BUTTON"));
                yml.addDefault(RewardsGUIPath.MENU_TRADE_CONFIRM_BUTTON_DATA, 0);
                yml.addDefault(RewardsGUIPath.MENU_TRADE_CONFIRM_BUTTON_SLOT, 49);

            } else {
                yml.addDefault(RewardsGUIPath.MENU_TITLE, "&8Example Title");
                yml.addDefault(RewardsGUIPath.MENU_SIZE, 27);
                String[] keys = {"1", "2", "3"};
                String[] names = {"name1", "name2", "name3"};
                List<String>[] lores = new List[]{
                        Arrays.asList("lore1", "anotherLore1"),
                        Arrays.asList("lore2", "anotherLore2"),
                        Arrays.asList("lore3", "anotherLore3")
                };
                List<String>[] enchantments = new List[]{
                        Arrays.asList("DURABILITY;1", "SHARPNESS;1"),
                        Arrays.asList("DURABILITY;1", "SHARPNESS;1"),
                        Arrays.asList("DURABILITY;1", "SHARPNESS;1")
                };
                int[] slots = {1, 2, 3};
                int[] data = {0, 0, 0};
                boolean[] hideEnchant = {true, false, true};
                String[] materials = {getMaterial("grass_block"), "iron_block", "gold_block"};
                List<String>[] commands = new List[]{
                        Arrays.asList("[openguimenu] menu2", "[message] &ahello!"),
                        Arrays.asList("[openguimenu] menu2", "[message] &ahello!"),
                        Arrays.asList("[openguimenu] menu2", "[message] &ahello!")
                };

                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    yml.addDefault(RewardsGUIPath.MENU_NAME.replace("%key%", key), names[i]);
                    yml.addDefault(RewardsGUIPath.MENU_LORE.replace("%key%", key), lores[i]);
                    yml.addDefault(RewardsGUIPath.MENU_SLOT.replace("%key%", key), slots[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM.replace("%key%", key), materials[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_DATA.replace("%key%", key), data[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ITEM_PERFORM.replace("%key%", key), commands[i]);
                    yml.addDefault(RewardsGUIPath.MENU_ENCHANTMENTS.replace("%key%", key), enchantments[i]);
                    yml.addDefault(RewardsGUIPath.MENU_HIDE_ENCHANTMENTS.replace("%key%", key), hideEnchant[i]);
                }
            }
            save();
        }
    }

    public String getMaterial(String name) {
        return getInternalVersion() <= 113 ? "grass" : name;
    }

    public static int getInternalVersion() {
        String version = Bukkit.getBukkitVersion(); // example: "1.20.4-R0.1-SNAPSHOT"
        String[] parts = version.split("\\.");
        try {
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);
            return major * 100 + minor; // 1.13 -> 113
        } catch (Exception e) {
            return -1; // parsing error
        }
    }
}
