package net.rewardsxdev.rewardsx.api.commands.maincmd;

import lombok.Getter;
import net.rewardsxdev.rewardsx.api.API;
import net.rewardsxdev.rewardsx.api.commands.subcmd.SubCmd;
import net.rewardsxdev.rewardsx.api.db.Database;
import net.rewardsxdev.rewardsx.api.instance.Instance;
import net.rewardsxdev.rewardsx.api.instance.PlatformAdapter;
import net.rewardsxdev.rewardsx.api.player.RPlayer;
import net.rewardsxdev.rewardsx.api.spigot.configuration.Language;
import net.rewardsxdev.rewardsx.api.support.papi.SupportPAPI;
import net.rewardsxdev.rewardsx.api.utils.globalpaths.MessagesPath;
import org.bukkit.command.Command;

import java.util.*;

public final class MainCmd {

    private static final Map<String, SubCmd> subCmds = new HashMap<>();
    @Getter
    public static API api;
    @Getter
    public static Database db;
    @Getter
    public static PlatformAdapter adapter;

    public MainCmd(API api1, SubCmd... commands) {
        api = api1;
        db = api.getRemoteDatabase();
        adapter = api.getInstance();
        for (SubCmd cmd : commands) {
            subCmds.put(cmd.name().toLowerCase(), cmd);
            for (String alias : cmd.getAliases(cmd)) {
                subCmds.put(alias.toLowerCase(), cmd);
            }
        }
    }

    /**
     * Entry-point unico: chiamato dagli adapter Spigot/Bungee/Velocity.
     */
    public boolean onCommand(RPlayer sender, String label, String[] args) {

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            helper(sender, label);
            return true;
        }

        String subName = args[0].toLowerCase();
        if (args.length >= 2) {
            String twoWord = (args[0] + " " + args[1]).toLowerCase();
            if (subCmds.containsKey(twoWord)) {
                subName = twoWord;
                args = java.util.Arrays.copyOfRange(args, 1, args.length); // riduci args di uno in più
            }
        }

        SubCmd sub = subCmds.get(subName);

        if (sub == null) {
            sender.sendMessage(SupportPAPI.getSupportPAPI().replace(sender, Language.getMsg(sender, MessagesPath.MESSAGES_COMMANDS_SUB_UNKNOWN)));
            helper(sender, label);
            return true;
        }

        // controllo permessi (se definiti)
        String perm = sub.permission();
        if (perm != null && !perm.isEmpty() && !sender.hasPermission(perm)) {
            sender.sendMessage(SupportPAPI.getSupportPAPI().replace(sender, Language.getMsg(sender, MessagesPath.MESSAGES_NO_PERMISSION)) + ": " + perm);
            return true;
        }

        String[] remaining = java.util.Arrays.copyOfRange(args, 1, args.length);
        boolean ok = sub.execute(sender, remaining);

        if (!ok) sender.sendMessage("§eSyntax Error. Try /" + label + " " + subName + " help");
        return true;
    }

    private void helper(RPlayer sender, String label) {
        List<String> baseLines = Language.getList(sender, MessagesPath.MESSAGES_COMMANDS_HELP);

        Set<String> mainCommands = new HashSet<>();
        for (SubCmd cmd : subCmds.values()) {
            mainCommands.add(cmd.name().toLowerCase());
        }

        final String softwareType = Instance.getInstance().toString();

        for (String line : baseLines) {
            if (line.contains("%cmd%")) {
                for (String cmdName : mainCommands) {
                    SubCmd cmd = subCmds.get(cmdName); // prendi SubCmd
                    String perm = cmd.permission();
                    if (perm == null || perm.isEmpty() || sender.hasPermission(perm)) {
                        String formatted = line
                                .replace("%software%", softwareType)
                                .replace("%cmd%",label + " " + cmd.name())
                                .replace("%description%", Language.getMsg(sender, cmd.description()));
                        sender.sendMessage(formatted);
                    }
                }
            } else {
                sender.sendMessage(line
                        .replace("%software%", softwareType)
                        .replace("%v%", adapter.getPluginVersion()));
            }
        }
    }

    /**
     * Registry a new subcmd.
     */
    public static void registerSubCommand(SubCmd cmd) {
        String name = cmd.name().toLowerCase();
        subCmds.put(name, cmd);

        for (String alias : cmd.getAliases(cmd)) {
            subCmds.put(alias.toLowerCase(), cmd);
        }
    }



}
