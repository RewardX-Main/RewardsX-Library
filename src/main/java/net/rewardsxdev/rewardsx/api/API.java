package net.rewardsxdev.rewardsx.api;

import net.rewardsxdev.rewardsx.api.db.Database;
import net.rewardsxdev.rewardsx.api.db.entity.AfkReward;
import net.rewardsxdev.rewardsx.api.features.jobs.JobSystem;
import net.rewardsxdev.rewardsx.api.gui.RewardsGUI;
import net.rewardsxdev.rewardsx.api.instance.PlatformAdapter;
import net.rewardsxdev.rewardsx.api.instance.server.BungeeServer;
import net.rewardsxdev.rewardsx.api.instance.server.SpigotServer;
import net.rewardsxdev.rewardsx.api.instance.server.VelocityServer;
import net.rewardsxdev.rewardsx.api.manager.TradeManager;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.spigot.configuration.ConfigManager;
import net.rewardsxdev.rewardsx.api.utils.menu.MenuConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public interface API {
    void loadLibrary();
    void initDb();
    Database getRemoteDatabase();

    String JOBS_LIST_PATH = "/Jobs/list";
    String MENU_GUI_PATH = "/Menus";
    String JOBS_PATH = "/Jobs";
    String ADDONS_PATH = "/Addons";
    String TRADE_PATH = "/Trade";
    String STREAK_PATH = "/Streak";
    String MESSAGES_PATH = "/Lang";

    void copyResource(Class<?> resourceOwner, String resourcePath, File destination);

    void logAddOnBanner(String name, boolean enabling);

    RewardsGUI getGUI();

    JobSystem getJobManager();

    interface Configs {
        /**
         * Get plugin main configuration.
         */
        ConfigManager getSpigotConfig();

        /**
         * Get plugin main configuration.
         */
        net.rewardsxdev.rewardsx.api.bungee.configuration.ConfigManager getBungeeConfig();

        /**
         * Get plugin main configuration.
         */
        net.rewardsxdev.rewardsx.api.velocity.configuration.ConfigManager getVelocityConfig();

        /**
         * Get Rewards Config.
         */
        ConfigManager getRewardsConfig();

        /**
         * Get Jobs Settings.
         */
        ConfigManager getJobsSettings();

        /**
         * Get GUI Config Rewards.
         */
        MenuConfig getMenusGUI(String menuName);

        /**
         * Get Trade Rewards.
         */
        ConfigManager getTradeRewards();

        ConfigManager getJobsYml();
        YamlConfiguration getJobsYml(String jobName);

    }

    Configs getConfigs();
    static void copyResources(Class<?> clazz, String resourcePath, File destination) {
        try (InputStream in = clazz.getResourceAsStream("/" + resourcePath)) {
            if (in == null) {
                System.out.println("❌ Resource not found: " + resourcePath);
                return;
            }

            if (!destination.getParentFile().exists()) {
                destination.getParentFile().mkdirs();
            }

            Files.copy(in, destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✅ Copied " + resourcePath + " to " + destination.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error copying " + resourcePath + ": " + e.getMessage());
            e.fillInStackTrace();
        }
    }

    TradeManager getTradeManager();

    void displayCustomerDetails(RPlayer player);

    /* -------------------------------------------------------------------- */
    /* Utility banner (optional)                                            */
    /* -------------------------------------------------------------------- */
    default void logAddOnBanner(String name) {
        PlatformAdapter adapter = getInstance();
        adapter.info("╔════════════════════════════════════════════════════════════════════════╗");
        adapter.info("║                                                                        ║");
        adapter.info(String.format ("            Enabled add-on %-40s        ", name));
        adapter.info("║                                                                        ║");
        adapter.info("╚════════════════════════════════════════════════════════════════════════╝");
    }

    default void printRewardsX() {
        PlatformAdapter adapter = getInstance();
        String[] art = new String[] {
                "██████╗ ███████╗██╗    ██╗ █████╗ ██████╗ ██████╗ ███████╗██╗  ██╗",
                "██╔══██╗██╔════╝██║    ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝╚██╗██╔╝",
                "██████╔╝█████╗  ██║ █╗ ██║███████║██████╔╝██║  ██║███████╗ ╚███╔╝ ",
                "██╔══██╗██╔══╝  ██║███╗██║██╔══██║██╔══██╗██║  ██║╚════██║ ██╔██╗ ",
                "██║  ██║███████╗╚███╔███╔╝██║  ██║██║  ██║██████╔╝███████║██╔╝ ██╗",
                "╚═╝  ╚═╝╚══════╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═╝"
        };

        adapter.info("");
        // Se vuoi stampare su console standard
        for (String line : art) {
            adapter.info(line);
        }
        adapter.info("");
    }

    interface RServer {
        SpigotServer getSpigotRserver();
        BungeeServer getBungeeRserver();
        VelocityServer getVelocityRserver();
    }

    RServer getRserver();


    PlatformAdapter getInstance();
}
