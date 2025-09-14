package net.rewardsxdev.rewardsx.api.utils.globalpaths;

public class JobsPath {
    public final static String JOBS_LEVEL_PATH = "levels";
    public final static String JOBS_REWARDS_PATH = "rewards";
    public final static String SETTINGS_JOBS_PATH = "jobs";

    public final static String JOBS_NAME = "name";
    public final static String JOBS_ACTION = "action";
    public final static String JOBS_LEVEL_ITEM = JOBS_LEVEL_PATH + ".%level%.%name%" + ".item";
    public final static String JOBS_AMOUNT_ITEM = JOBS_LEVEL_PATH + ".%level%.%name%" + ".amount";
    public final static String JOBS_LEVEL_REWARDS = JOBS_LEVEL_PATH + ".%level%.%name%." + JOBS_REWARDS_PATH + ".commands";

    public final static String SETTINGS_JOBS_PATH_NAME_ENABLED = SETTINGS_JOBS_PATH + ".%name%.enable";
}
