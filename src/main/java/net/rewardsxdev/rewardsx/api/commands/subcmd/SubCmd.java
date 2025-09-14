package net.rewardsxdev.rewardsx.api.commands.subcmd;


import net.rewardsxdev.rewardsx.api.player.RPlayer;

import java.util.*;

public interface SubCmd {
    Map<SubCmd, List<String>> ALIASES_MAP = new WeakHashMap<>();

    String name();
    String description();
    String permission();
    boolean execute(RPlayer p, String[] args);

    default void setAlias(SubCmd cmd, String alias) {
        ALIASES_MAP.computeIfAbsent(cmd, k -> new ArrayList<>()).add(alias.toLowerCase());
    }

    default List<String> getAliases(SubCmd cmd) {
        return ALIASES_MAP.getOrDefault(cmd, Collections.emptyList());
    }
}



