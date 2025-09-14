package net.rewardsxdev.rewardsx.api.utils.actions;

import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.db.Database;
import net.rewardsxdev.rewardsx.api.db.entity.Job;
import net.rewardsxdev.rewardsx.api.features.jobs.JobSystem;
import net.rewardsxdev.rewardsx.api.features.rewards.Reward;
import net.rewardsxdev.rewardsx.api.features.rewards.RewardManager;
import net.rewardsxdev.rewardsx.api.features.rewards.RewardsType;
import net.rewardsxdev.rewardsx.api.gui.RewardsGUI;
import net.rewardsxdev.rewardsx.api.instance.PlatformAdapter;
import net.rewardsxdev.rewardsx.api.instance.RServer;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.spigot.configuration.ConfigManager;
import net.rewardsxdev.rewardsx.api.spigot.configuration.Language;
import net.rewardsxdev.rewardsx.api.support.papi.SupportPAPI;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.ConfigPath;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.JobsPath;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.MessagesPath;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;

import static net.rewardsxdev.rewardsx.api.utils.color.Color.safeTranslate;

public class Actions {
    private static RServer server;
    private static API api;
    private static PlatformAdapter adapter;
    private static Database db;
    private static RewardsGUI guiHandler;

    public Actions(API api, RServer server, RewardsGUI guiHandler){
        Actions.api = api;
        Actions.server = server;
        Actions.guiHandler = guiHandler;
        Actions.adapter = api.getInstance();
        Actions.db = api.getRemoteDatabase();

    }
    public static void executeCommands(RPlayer player, String action, String... error) {

        for (String prefix : RewardManager.getRegisteredPrefixes()) {
            if (action.startsWith(prefix)) {
                String rewardKey = action.replace(prefix, "").trim();
                Reward reward = RewardManager.getReward(prefix);
                if(reward != null) {
                    reward.init();
                    if(reward.canClaim(player, rewardKey)) {
                        reward.give(player, rewardKey);
                    } else {
                        // Se error è stato passato, invia i suoi messaggi
                        if (error != null && error.length > 0) {
                            for (String e : error) {
                                player.sendMessage(e.replace("%reward%", rewardKey).replace("%rewardtype%", reward.type().name().toLowerCase()));
                            }
                        }
                    }
                } else {
                    adapter.debug("Nessun Reward per il prefix: " + prefix);
                }
                return;
            }
        }

        if (action.startsWith("[openguimenu]")) {

            String menuToOpen = action.replace("[openguimenu]", "").trim();
            adapter.debug("Apro menu: " + menuToOpen);
            guiHandler.openGui(player, menuToOpen);

        } else if (action.startsWith("[message]")) {

            String rawMsg = action.replace("[message]", "").trim();
            String msg = SupportPAPI.getSupportPAPI().replace(player, rawMsg);
            adapter.debug("Invio messaggio: " + msg);
            player.sendMessage(safeTranslate(msg));

        } else if (action.startsWith("[console]")) {

            String rawConsoleCmd = action.replace("[console]", "").trim()
                    .replace("{player}", player.getPlayerName())
                    .replace("%player%", player.getPlayerName())
                    .replace("%player_name%", player.getPlayerName());

            String consoleCmd = SupportPAPI.getSupportPAPI().replace(player, rawConsoleCmd);
            adapter.debug("Eseguo comando console: " + consoleCmd);
            server.dispatchConsoleCommand(safeTranslate(consoleCmd));

        } else if (action.startsWith("[player]")) {

            String rawPlayerCmd = action.replace("[player]", "").trim()
                    .replace("{player}", player.getPlayerName())
                    .replace("%player%", player.getPlayerName());
            String playerCmd = SupportPAPI.getSupportPAPI().replace(player, rawPlayerCmd);
            adapter.debug("Eseguo comando player: " + playerCmd);
            player.performCommand(safeTranslate(playerCmd));

        } else if (action.startsWith("[jobstart]")){
            try {
                YamlConfiguration yml = api.getConfigs().getJobsSettings().getYml();
                YamlConfiguration configYml = api.getConfigs().getSpigotConfig().getYml();
                String rawJob = action.replace("[jobstart]", "").trim();

                boolean jobEnabled = configYml.getBoolean(ConfigPath.SETTINGS_ACTIVATE_OPTION_SYSTEM.replace("%path%", RewardsType.JOBS.getName()));

                if(jobEnabled) {

                    Job job = db.getJob(player.getUniqueId(), rawJob);

                    if (job == null) {
                        job = new Job(player.getUniqueId(), rawJob, 1, false);
                        db.saveJob(job);
                    }

                    String enabledPath = JobsPath.SETTINGS_JOBS_PATH_NAME_ENABLED.replace("%name%", rawJob);

                    boolean enabled = yml.getBoolean(enabledPath);

                    if (enabled) {
                        if (!job.isStarted()) {
                            job.setStarted(true);
                            player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_JOB_STARTED).replace("%job%", job.getChoiceType())));
                            api.getJobManager().addActiveJob(player.getUniqueId(), rawJob);
                            db.saveJob(job);
                        } else {
                            player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_JOB_ALREADY_STARTED)));
                        }
                    } else {
                        player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_JOB_NOT_ENABLED).replace("%path%", enabledPath)));
                    }
                } else {
                    player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_SETTINGS_REWARDS_NOT_ENABLED.replace("%path%", RewardsType.JOBS.getName()))));
                }
            } catch (SQLException ex){
                ex.printStackTrace();
            }


        } else if(action.startsWith("[jobleave]")) {
            try {
                String rawJob = action.replace("[jobleave]", "").trim();
                YamlConfiguration configYml = api.getConfigs().getSpigotConfig().getYml();

                boolean jobEnabled = configYml.getBoolean(ConfigPath.SETTINGS_ACTIVATE_OPTION_SYSTEM.replace("%path%", RewardsType.JOBS.getName()));

                if(jobEnabled) {

                    Job job = db.getJob(player.getUniqueId(), rawJob);

                    if (job == null) {
                        return;
                    }

                    if (job.isStarted()) {
                        job.setStarted(false);
                        player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_JOB_QUIT).replace("%job%", job.getChoiceType())));
                        api.getJobManager().removeActiveJob(player.getUniqueId(), rawJob);
                        db.saveJob(job);
                    } else {
                        player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_JOB_NOT_STARTED)));
                    }
                } else {
                    player.sendMessage(SupportPAPI.getSupportPAPI().replace(player, Language.getMsg(player, MessagesPath.MESSAGES_SETTINGS_REWARDS_NOT_ENABLED.replace("%path%", RewardsType.JOBS.getName()))));
                }
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        } else if (action.startsWith("[close]")){
            player.closeInventory();
        } else {
            adapter.debug("Azione non riconosciuta: " + action);
        }
    }
}
