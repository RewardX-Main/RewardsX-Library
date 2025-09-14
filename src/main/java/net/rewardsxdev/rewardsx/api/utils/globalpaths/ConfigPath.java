package net.rewardsxdev.rewardsx.api.utils.globalpaths;

public class ConfigPath {

    // ---------------- //

    // PATHS

     // CONFIG
    public final static String CONFIG_PATH = "config";
    public final static String CONFIG_DB_PATH = CONFIG_PATH + ".database";
    public final static String CONFIG_AFK_PATH = CONFIG_PATH + ".afk";

     // SETTINGS
    public final static String SETTINGS_PATH = "settings";
    public final static String SETTINGS_CALENDAR_MODE = SETTINGS_PATH + ".adventcalendar-mode";
    public final static String SETTINGS_STREAK_MODE = SETTINGS_PATH + ".streak-mode";
    public final static String SETTINGS_REWARDS_PATH = SETTINGS_PATH + ".rewards";
    public final static String SETTINGS_SYSTEM_PATH = SETTINGS_PATH + ".systems";

    // ---------------- //

    // CONFIG PATH
    public final static String CONFIG_BUNGEE = CONFIG_PATH + ".bungee";
    public final static String CONFIG_PREFIX = CONFIG_PATH + ".prefix";
    public final static String CONFIG_DEBUG = CONFIG_PATH + ".debug";
    public final static String CONFIG_lANG = CONFIG_PATH + ".lang";
    public final static String CONFIG_TIME_ZOME = CONFIG_PATH + ".time-zone";
    public final static String CONFIG_AFK_TIME_TO_START = CONFIG_AFK_PATH + ".start";
    public final static String CONFIG_AFK_INTERVAL_ENABLED = CONFIG_AFK_PATH + ".enable-interval";
    public final static String CONFIG_AFK_INTERVAL = CONFIG_AFK_PATH + ".interval";

    // CONFIG DB
    public final static String CONFIG_DB_ENABLED = CONFIG_DB_PATH + ".enabled";
    public final static String CONFIG_DB_HOST = CONFIG_DB_PATH + ".host";
    public final static String CONFIG_DB_NAME = CONFIG_DB_PATH + ".database";
    public final static String CONFIG_DB_PORT = CONFIG_DB_PATH + ".port";
    public final static String CONFIG_DB_USER = CONFIG_DB_PATH + ".user";
    public final static String CONFIG_DB_PASSWORD = CONFIG_DB_PATH + ".password";
    public final static String CONFIG_DB_SSL = CONFIG_DB_PATH + ".ssl";
    public final static String CONFIG_DB_CERTIFICATE = CONFIG_DB_PATH + ".verify-certificate";
    public final static String CONFIG_DB_POOL = CONFIG_DB_PATH + ".pool-size";
    public final static String CONFIG_DB_LIFETIME = CONFIG_DB_PATH + ".max-lifetime";

    // ---------------- //

    // SETTINGS PATH
    public final static String SETTINGS_STREAK_CONDITION = SETTINGS_STREAK_MODE + ".condition";
    public final static String SETTINGS_CALENDAR_CHRISTMAS = SETTINGS_CALENDAR_MODE + ".christmas";
    public final static String SETTINGS_CALENDAR_HALLOWEEN = SETTINGS_CALENDAR_MODE + ".halloween";
    public final static String SETTINGS_CALENDAR_EASTER = SETTINGS_CALENDAR_MODE + ".easter";
    public final static String SETTINGS_ACTIVATE_OPTION_REWARDS = SETTINGS_REWARDS_PATH + ".activate-%path%";
    public final static String SETTINGS_ACTIVATE_OPTION_SYSTEM = SETTINGS_SYSTEM_PATH + ".activate-%path%";
}
