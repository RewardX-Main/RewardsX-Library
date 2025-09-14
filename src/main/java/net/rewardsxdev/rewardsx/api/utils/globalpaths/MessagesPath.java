package net.rewardsxdev.rewardsx.api.utils.globalpaths;

public class MessagesPath {
    /* --------- PATH ------- */
    public final static String MESSAGES_NAME = "name";
    public final static String PREFIX = "prefix";
    public final static String MESSAGES_GLOBAL_PATH = "messages";
    public final static String MESSAGES_GENERAL = MESSAGES_GLOBAL_PATH + ".general";
    public final static String MESSAGES_REFERRAL_PATH = MESSAGES_GLOBAL_PATH + ".referral";
    public final static String MESSAGES_TRADE_PATH = MESSAGES_GLOBAL_PATH + ".trade";
    public final static String MESSAGES_JOB_PATH = MESSAGES_GLOBAL_PATH + ".job";
    public final static String MESSAGES_LANG_PATH = MESSAGES_GLOBAL_PATH + ".lang";
    public final static String MESSAGES_MENU_PATH = MESSAGES_GLOBAL_PATH + ".menu";
    public final static String MESSAGES_SETTINGS_PATH = MESSAGES_GLOBAL_PATH + ".settings";
    public final static String MESSAGES_ADDONS_PATH = MESSAGES_GLOBAL_PATH + ".addons";
    public final static String MESSAGES_TYPE_TITLE_PATH = ".title";
    public final static String MESSAGES_TYPE_SUBTITLE_PATH = ".subtitle";
    public final static String MESSAGES_REFERRAL_INVITE_PATH = MESSAGES_REFERRAL_PATH + ".invite";
    public final static String MESSAGES_REFERRAL_ACCEPT_PATH = MESSAGES_REFERRAL_PATH + ".accept";
    public final static String MESSAGES_REFERRAL_STATS_PATH = MESSAGES_REFERRAL_PATH + ".stats";
    public final static String MESSAGES_REFERRAL_HOST_PATH = MESSAGES_REFERRAL_INVITE_PATH + ".host";
    public final static String MESSAGES_REFERRAL_GUEST_PATH = MESSAGES_REFERRAL_INVITE_PATH + ".guest";
    public final static String MESSAGES_REWARDS_PATH = MESSAGES_GLOBAL_PATH + ".rewards";


    /* --------- VARIABLES PATH (general) ------- */
    public final static String MESSAGES_PLAYER_NOT_ONLINE = MESSAGES_GENERAL + ".player-not-online";
    public final static String MESSAGES_NO_PERMISSION = MESSAGES_GENERAL + ".no-permission";
    public final static String MESSAGES_PLAYER_NOT_FOUND = MESSAGES_GENERAL + ".player-not-found";
    public final static String MESSAGES_RELOAD_MSG = MESSAGES_GENERAL + ".reload";
    public final static String MESSAGES_COMMANDS = MESSAGES_GENERAL + ".commands";
    public final static String MESSAGES_REWARD_CANT_CLAIM = MESSAGES_REWARDS_PATH + ".cant-claim";
    public final static String MESSAGES_COMMANDS_HELP = MESSAGES_COMMANDS + ".help";
    public final static String MESSAGES_COMMAND_USAGE = MESSAGES_COMMANDS + ".usage";
    public final static String MESSAGES_COMMANDS_SUB_UNKNOWN = MESSAGES_COMMANDS + ".cmd-unknown";
    public final static String MESSAGES_COMMANDS_SUB_DESC = MESSAGES_COMMANDS + ".%key%.desc";


    /* --------- VARIABLES PATH (settings) ------- */
    public final static String MESSAGES_SETTINGS_REWARDS_NOT_ENABLED = MESSAGES_SETTINGS_PATH + ".not-enabled";


    /* --------- VARIABLES PATH (invite) ------- */
    public final static String MESSAGES_REFERRAL_HOST_INVITE_MSG = MESSAGES_REFERRAL_HOST_PATH + ".message";
    public final static String MESSAGES_REFERRAL_HOST_INVITE_ALREADY_MSG = MESSAGES_REFERRAL_HOST_PATH + ".already-message";
    public final static String MESSAGES_REFERRAL_HOST_ALREADY_REGISTERED = MESSAGES_REFERRAL_HOST_PATH + ".already-registered";
    public final static String MESSAGES_REFERRAL_HOST_CANNOT_YOURSELF = MESSAGES_REFERRAL_HOST_PATH + ".invite-self";
    public final static String MESSAGES_REFERRAL_HOST_INVITE_TITLE = MESSAGES_REFERRAL_HOST_PATH + MESSAGES_TYPE_TITLE_PATH;
    public final static String MESSAGES_REFERRAL_HOST_INVITE_SUBTITLE = MESSAGES_REFERRAL_HOST_PATH + MESSAGES_TYPE_SUBTITLE_PATH;
    public final static String MESSAGES_REFERRAL_GUEST_RECEIVE_MSG = MESSAGES_REFERRAL_GUEST_PATH + ".receive-message";
    public final static String MESSAGES_REFERRAL_GUEST_RECEIVE_TITLE = MESSAGES_REFERRAL_GUEST_PATH + MESSAGES_TYPE_TITLE_PATH;
    public final static String MESSAGES_REFERRAL_GUEST_RECEIVE_SUBTITLE = MESSAGES_REFERRAL_GUEST_PATH + MESSAGES_TYPE_SUBTITLE_PATH;


    /* --------- VARIABLES PATH (lang) --------- */
    public final static String COMMAND_LANG_LIST_HEADER = MESSAGES_LANG_PATH + ".header";
    public final static String COMMAND_LANG_LIST_FORMAT = MESSAGES_LANG_PATH + ".format";
    public final static String COMMAND_LANG_SELECTED_SUCCESSFULLY = MESSAGES_LANG_PATH + ".success";
    public final static String COMMAND_LANG_SELECTED_NOT_EXIST = MESSAGES_LANG_PATH + ".not-exist";
    public final static String MESSAGES_LANG_SELECTED_ALREADY = MESSAGES_LANG_PATH + ".already-selected";


    /* --------- VARIABLES PATH (menu) --------- */
    public final static String COMMAND_MENU_LIST_HEADER = MESSAGES_MENU_PATH + ".header";
    public final static String COMMAND_MENU_NOT_FOUND = MESSAGES_MENU_PATH + ".not-found";
    public final static String COMMAND_MENU_LIST_FORMAT = MESSAGES_MENU_PATH + ".format";

    /* --------- VARIABLES PATH (addons) --------- */
    public final static String COMMAND_ADDONS_LIST_HEADER = MESSAGES_ADDONS_PATH + ".header";
    public final static String COMMAND_ADDONS_LIST_FORMAT = MESSAGES_ADDONS_PATH + ".format";


    /* --------- VARIABLES PATH (accept) ------- */
    public final static String MESSAGES_REFERRAL_CODE_NOT_VALID = MESSAGES_REFERRAL_ACCEPT_PATH + ".code-not-valid";
    public final static String MESSAGES_REFERRAL_CODE_NO_ACCOUNT = MESSAGES_REFERRAL_ACCEPT_PATH + ".no-account-match";
    public final static String MESSAGES_REFERRAL_ACCEPTED = MESSAGES_REFERRAL_ACCEPT_PATH + ".msg";
    public final static String MESSAGES_REFERRAL_ALREADY_ACCEPTED = MESSAGES_REFERRAL_ACCEPT_PATH + ".already-msg";
    public final static String MESSAGES_REFERRAL_HOST_MSG = MESSAGES_REFERRAL_ACCEPT_PATH + ".host-msg";


    /* --------- VARIABLES PATH (stats) ------- */
    public final static String MESSAGES_REFERRAL_HOST_INVITE_NUMBER = MESSAGES_REFERRAL_STATS_PATH + ".hosting-invited-number";

    /* --------- VARIABLES PATH (trade) ------- */
    public final static String MESSAGES_TRADE_SEPARATOR_NAME = MESSAGES_MENU_PATH + ".trade.separator-name";

    public final static String MESSAGES_TRADE_CONFIRM_BUTTON_NAME = MESSAGES_MENU_PATH + ".trade.confirm-button-name";
    public final static String MESSAGES_TRADE_CONFIRM_BUTTON_LORE = MESSAGES_MENU_PATH + ".trade.confirm-button-lore";

    public final static String MESSAGES_TRADE_ACCEPT_BUTTON_NAME = MESSAGES_MENU_PATH + ".trade.accept-button-name";
    public final static String MESSAGES_TRADE_ACCEPT_BUTTON_LORE = MESSAGES_MENU_PATH + ".trade.accept-button-lore";

    public final static String MESSAGES_TRADE_CANCEL_BUTTON_NAME = MESSAGES_MENU_PATH + ".trade.cancel-button-name";
    public final static String MESSAGES_TRADE_CANCEL_BUTTON_LORE = MESSAGES_MENU_PATH + ".trade.cancel-button-lore";


    /* --------- VARIABLES PATH (job) ------- */
    public final static String MESSAGES_JOB_STARTED = MESSAGES_JOB_PATH + ".started";
    public final static String MESSAGES_JOB_ALREADY_STARTED = MESSAGES_JOB_PATH + ".already-started";
    public final static String MESSAGES_JOB_NOT_STARTED = MESSAGES_JOB_PATH + ".not-started";
    public final static String MESSAGES_JOB_NOT_ENABLED = MESSAGES_JOB_PATH + ".not-enabled";
    public final static String MESSAGES_JOB_QUIT = MESSAGES_JOB_PATH + ".quit";
    public final static String MESSAGES_JOB_TASK_PROGRESS = MESSAGES_JOB_PATH + ".task-progress";
    public final static String MESSAGES_JOB_TASK_COMPLETED = MESSAGES_JOB_PATH + ".task-completed";


    public final static String MESSAGES_TRADE_TARGET_REQUEST = MESSAGES_TRADE_PATH + ".target.request";
    public final static String MESSAGES_TRADE_TARGET_NOT_FOUND = MESSAGES_TRADE_PATH + ".target.not-found";
    public final static String MESSAGES_TRADE_PLAYER_NOT_FOUND_OR_SELF = MESSAGES_TRADE_PATH + ".player.not-found-or-self";
    public final static String MESSAGES_TRADE_CONFIRMED = MESSAGES_TRADE_PATH + ".player.confirmed";
    public final static String MESSAGES_TRADE_ABORTED = MESSAGES_TRADE_PATH + ".aborted";
    public final static String MESSAGES_TRADE_REQUESTER_COMPLETED = MESSAGES_TRADE_PATH + ".requester.completed";
    public final static String MESSAGES_TRADE_TARGET_COMPLETED = MESSAGES_TRADE_PATH + ".target.completed";
    public final static String MESSAGES_TRADE_PLAYER_ABORTED = MESSAGES_TRADE_PATH + ".player.you-aborted";
    public final static String MESSAGES_TRADE_NO_GLOBAL_PENDING_REQUEST = MESSAGES_TRADE_PATH + ".no-pending-global-request";
    public final static String MESSAGES_TRADE_TARGET_REQUEST_DENIED = MESSAGES_TRADE_PATH + ".target.denied";
    public final static String MESSAGES_TRADE_TARGET_REQUESTER_NOT_ONLINE = MESSAGES_TRADE_PATH + ".target.requester-not-online";
    public final static String MESSAGES_TRADE_REQUESTER_ALREADY_PENDING = MESSAGES_TRADE_PATH + ".requester.target-already-pending";
    public final static String MESSAGES_TRADE_REQUESTER_REQUEST_ACCEPTED = MESSAGES_TRADE_PATH + ".requester.accepted";
    public final static String MESSAGES_TRADE_REQUESTER_REQUEST = MESSAGES_TRADE_PATH + ".requester.request";
    public final static String MESSAGES_TRADE_REQUESTER_REQUEST_DENIED = MESSAGES_TRADE_PATH + ".requester.denied";
    public final static String MESSAGES_TRADE_NO_PENDING_REQUEST = MESSAGES_TRADE_PATH + ".no-pending-request";


}
