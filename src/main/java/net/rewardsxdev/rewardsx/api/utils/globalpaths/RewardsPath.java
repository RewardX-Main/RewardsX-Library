package net.rewardsxdev.rewardsx.api.utils.globalpaths;

public class RewardsPath {

    // ---------------- //

    // PATHS
    public final static String WEEKLY_PATH = "weekly-rewards";
    public final static String DAILY_PATH = "daily-rewards";
    public final static String MONTHLY_PATH = "monthly-rewards";
    public final static String STREAK_PATH = "streak-rewards";
    public final static String ADVENTCALENDAR_PATH = "adventcalendar-rewards";
    public final static String SYSTEMS_PATH = "systems-rewards";
    public final static String AFK_PATH = SYSTEMS_PATH + ".afk";
    public final static String REFERRAL_PATH = SYSTEMS_PATH + ".referral";
    public final static String REFERRAL_HOST_PATH = REFERRAL_PATH + ".host";
    public final static String REFERRAL_GUEST_PATH = REFERRAL_PATH + ".guest";
    public final static String PLAYTIME_PATH = SYSTEMS_PATH + ".playtime";

    // ---------------- //

    // COMPLETE PATHS
    public final static String WEEKLY_REWARD_ID_ENABLED = WEEKLY_PATH + ".%key%.enabled";
    public final static String WEEKLY_REWARD_ID_NAME = WEEKLY_PATH + ".%key%.name";
    public final static String WEEKLY_REWARD_ID_LORE = WEEKLY_PATH + ".%key%.lore";
    public final static String WEEKLY_REWARD_ID_COMMANDS = WEEKLY_PATH + ".%key%.commands";

    public final static String DAILY_REWARD_ID_ENABLED = DAILY_PATH + ".%key%.enabled";
    public final static String DAILY_REWARD_ID_NAME = DAILY_PATH + ".%key%.name";
    public final static String DAILY_REWARD_ID_LORE = DAILY_PATH + ".%key%.lore";
    public final static String DAILY_REWARD_ID_COMMANDS = DAILY_PATH + ".%key%.commands";

    public final static String MONTHLY_REWARD_ID_ENABLED = MONTHLY_PATH + ".%key%.enabled";
    public final static String MONTHLY_REWARD_ID_NAME = MONTHLY_PATH + ".%key%.name";
    public final static String MONTHLY_REWARD_ID_LORE = MONTHLY_PATH + ".%key%.lore";
    public final static String MONTHLY_REWARD_ID_COMMANDS = MONTHLY_PATH + ".%key%.commands";

    public final static String STREAK_REWARD_ID_ENABLED = STREAK_PATH + ".%key%.enabled";
    public final static String STREAK_REWARD_ID_NAME = STREAK_PATH + ".%key%.name";
    public final static String STREAK_REWARD_ID_LORE = STREAK_PATH + ".%key%.lore";
    public final static String STREAK_REWARD_ID_CONDITION = STREAK_PATH + ".%key%.condition";
    public final static String STREAK_REWARD_ID_AMOUNT = STREAK_PATH + ".%key%.amount";
    public final static String STREAK_REWARD_ID_COMMANDS = STREAK_PATH + ".%key%.commands";

    public final static String ADVENTCALENDAR_REWARD_ID_ENABLED = ADVENTCALENDAR_PATH + ".%event%.%key%.enabled";
    public final static String ADVENTCALENDAR_REWARD_ID_NAME = ADVENTCALENDAR_PATH + ".%event%.%key%.name";
    public final static String ADVENTCALENDAR_REWARD_ID_LORE = ADVENTCALENDAR_PATH + ".%event%.%key%.lore";
    public final static String ADVENTCALENDAR_REWARD_ID_COMMANDS = ADVENTCALENDAR_PATH + ".%event%.%key%.commands";

    public final static String AFK_REWARD_ID_AFKTIME = AFK_PATH + ".%key%.afk-time";
    public final static String AFK_REWARD_ID_ENABLED = AFK_PATH + ".%key%.enabled";
    public final static String AFK_REWARD_ID_NAME = AFK_PATH + ".%key%.name";
    public final static String AFK_REWARD_ID_LORE = AFK_PATH + ".%key%.lore";
    public final static String AFK_REWARD_ID_COMMANDS = AFK_PATH + ".%key%.commands";

    public final static String REFERRAL_HOST_REWARD_ID_ENABLED = REFERRAL_HOST_PATH + ".%key%.enabled";
    public final static String REFERRAL_HOST_REWARD_ID_NAME = REFERRAL_HOST_PATH + ".%key%.name";
    public final static String REFERRAL_HOST_REWARD_ID_LORE = REFERRAL_HOST_PATH + ".%key%.lore";
    public final static String REFERRAL_HOST_REWARD_ID_COMMANDS = REFERRAL_HOST_PATH + ".%key%.commands";
    public final static String REFERRAL_GUEST_REWARD_ID_ENABLED = REFERRAL_GUEST_PATH + ".%key%.enabled";
    public final static String REFERRAL_GUEST_REWARD_ID_NAME = REFERRAL_GUEST_PATH + ".%key%.name";
    public final static String REFERRAL_GUEST_REWARD_ID_LORE = REFERRAL_GUEST_PATH + ".%key%.lore";
    public final static String REFERRAL_GUEST_REWARD_ID_COMMANDS = REFERRAL_GUEST_PATH + ".%key%.commands";

    public final static String PLAYTIME_REWARD_ID_ENABLED = PLAYTIME_PATH + ".%key%.enabled";
    public final static String PLAYTIME_REWARD_ID_PLAYTIME = PLAYTIME_PATH + ".%key%.playtime";
    public final static String PLAYTIME_REWARD_ID_NAME = PLAYTIME_PATH + ".%key%.name";
    public final static String PLAYTIME_REWARD_ID_LORE = PLAYTIME_PATH + ".%key%.lore";
    public final static String PLAYTIME_REWARD_ID_COMMANDS = PLAYTIME_PATH + ".%key%.commands";

}
