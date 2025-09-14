package net.rewardsxdev.rewardsx.api.utils.globalpaths;

public class TradePath {
    public final static String ALLOWED_BLOCK_PATH = "allowed-blocks";
    public final static String ALLOWED_BLOCKS_REWARDS_PATH = "rewards";


    public final static String TRADE_ITEM_ENABLED = ALLOWED_BLOCK_PATH + ".%name%.enabled";
    public final static String TRADE_ITEM_ITEM = ALLOWED_BLOCK_PATH + ".%name%.item";
    public final static String TRADE_REWARDS = ALLOWED_BLOCK_PATH + ".%name%." + ALLOWED_BLOCKS_REWARDS_PATH + ".commands";
}
