package net.rewardsxdev.rewardsx.api.instance;

import java.nio.file.Path;

public interface PlatformAdapter {


    enum Type { SPIGOT, BUNGEECORD, VELOCITY }

    Type type();

    // logger indipendente da SLF4J / java.util
    void info(String msg);
    void error(String msg);
    void debug(String msg);
    void severe(String msg);

    String getName();

    String getPluginVersion();

    boolean isDbEnabled();

    Path getDataPath();

    /** Ritorna l’oggetto “plugin” da dare al costruttore di SQLite */
    Object pluginInstance();
}

