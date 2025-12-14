package net.rewardsxdev.rewardsx.api.db;

import net.rewardsxdev.rewardsx.api.db.entity.Module;
import net.rewardsxdev.rewardsx.api.db.entity.*;
import net.rewardsxdev.rewardsx.api.features.jobs.PlayerJobProgress;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface Database {

    default String generateCode() {
        return RandomStringUtils.randomAlphanumeric(8).toUpperCase(Locale.ROOT);
    }

    /**
     * Initialize the database connection and create required tables.
     */
    void init();

    /**
     * Returns the driver class name for database connection.
     */
    String driverClassName();

    /**
     * Retrieve a numeric value from a specific column and table for the given player, supporting custom conditions.
     */
    int getColumn(UUID player, String column, String table, String... condition);

    /* -------------------- Users -------------------- */

    /**
     * Retrieve a user record using its unique player UUID.
     *
     * @param player Unique identifier (UUID) of the player.
     * @return The User object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    User getUser(UUID player) throws SQLException;

    User getUserByName(String playerName) throws SQLException;

    /**
     * Insert or update a user record in the database.
     *
     * @param user The User object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void saveUser(User user) throws SQLException;

    /* ------------------- Modules ------------------- */

    /**
     * Retrieve a module configuration record by the module's name.
     *
     * @param moduleName Case-sensitive module name.
     * @return The Module object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    Module getModule(String moduleName) throws SQLException;

    /**
     * Retrieve all available module configuration records.
     *
     * @return List of all Module objects.
     * @throws SQLException If a database access error occurs.
     */
    List<Module> getAllModules() throws SQLException;

    /**
     * Insert or update the state of a module configuration.
     *
     * @param module The Module object to save.
     * @throws SQLException If a database access error occurs.
     */
    void saveModule(Module module) throws SQLException;

    /* ------------- RewardADSModule ----------------- */

    /**
     * Retrieve a RewardADSModule record by its unique reward identifier.
     *
     * @param idReward Unique ID for the reward.
     * @return The RewardADSModule object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    RewardADSModule getRewardADSModule(int idReward) throws SQLException;

    /**
     * Insert or update a RewardADSModule record in the database.
     *
     * @param r The RewardADSModule object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void saveRewardADSModule(RewardADSModule r) throws SQLException;

    /* ---------------- JobsSystem ------------------- */

    /**
     * Retrieve a job entry for a player with a specific choice type.
     *
     * @param player     UUID of the player.
     * @param choiceType The job type key (e.g. “MINER”).
     * @return The Job object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    Job getJob(UUID player, String choiceType) throws SQLException;

    /**
     * Retrieve the active job entry for a player.
     *
     * @param player UUID of the player.
     * @return The Job object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    Job getJob(UUID player) throws SQLException;

    /**
     * Insert or update a job record for a player.
     *
     * @param job Job object to save or update.
     * @throws SQLException If a database access error occurs.
     */
    void saveJob(Job job) throws SQLException;

    /**
     * Retrieve job progress details for a player and choice type.
     *
     * @param player     UUID of the player.
     * @param choiceType The job type key.
     * @return PlayerJobProgress object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    PlayerJobProgress getJobProgress(UUID player, String choiceType) throws SQLException;

    /**
     * Retrieve a list of all active jobs for all players.
     *
     * @return List of active Job objects.
     * @throws SQLException If a database access error occurs.
     */
    List<Job> getAllActivePlayerJobs() throws SQLException;

    /**
     * Save or update the current job level of a player for a specific job.
     *
     * @param player    Player UUID.
     * @param choiceType Job type key.
     * @param level     Current job level to save.
     * @throws SQLException If a database access error occurs.
     */
    void saveOrUpdateJobLevel(UUID player, String choiceType, int level) throws SQLException;

    /**
     * Save or update the progress for a specific job task and level for the player.
     *
     * @param player    Player UUID.
     * @param choiceType Job type key.
     * @param level     Level to update.
     * @param taskKey   Unique identifier for the task.
     * @param progress  Progress value.
     * @throws SQLException If a database access error occurs.
     */
    void saveOrUpdateJobProgress(UUID player, String choiceType, int level, String taskKey, int progress) throws SQLException;

    /**
     * Clear (delete) job progress for the specified player, job and level.
     *
     * @param player    Player UUID.
     * @param choiceType Job type key.
     * @param level     Job level to clear.
     * @throws SQLException If a database access error occurs.
     */
    void clearJobProgress(UUID player, String choiceType, int level) throws SQLException;

    /**
     * Increment the progress for a specific job and task, then save.
     *
     * @param player    Player UUID.
     * @param jobName   Job key.
     * @param taskKey   Task identifier.
     * @param increment Value to add to the progress.
     * @throws SQLException If a database access error occurs.
     */
    void updateProgressAndSave(UUID player, String jobName, String taskKey, int increment) throws SQLException;

    /**
     * Promote (increment) a player's job level and clear progress for the previous level.
     *
     * @param player  Player UUID.
     * @param jobName Job key.
     * @throws SQLException If a database access error occurs.
     */
    void promoteAndSave(UUID player, String jobName) throws SQLException;

    /* -------------- ReferralSystem ----------------- */

    /**
     * Get referral data between a host and a guest.
     *
     * @param host  UUID of the hosting player.
     * @param guest UUID of the invited player.
     * @return Referral record or null if absent.
     * @throws SQLException If a database access error occurs.
     */
    Referral getReferral(UUID host, UUID guest) throws SQLException;

    /**
     * Retrieves the guest User who was invited by the specified host.
     *
     * @param host UUID of the host player.
     * @return User object of the guest or null.
     * @throws SQLException If a database access error occurs.
     */
    User getInvitedGuestByHost(UUID host) throws SQLException;

    /**
     * Set the language for a player.
     *
     * @param player     Player UUID.
     * @param playerName Name of the player.
     * @param iso        ISO language code.
     */
    void setLanguage(UUID player, String playerName, String iso);

    Referral getPendingReferralByGuestName(String guestName) throws SQLException;

    /**
     * Retrieve the ISO language code of a player.
     *
     * @param player Player UUID.
     * @return ISO language code as String.
     */
    String getLanguage(UUID player);

    /**
     * Get referral data using a referral code.
     *
     * @param code Referral code of the hosting player.
     * @return Referral record or null.
     * @throws SQLException If a database access error occurs.
     */
    Referral getReferralByCode(String code) throws SQLException;

    /**
     * Increment the number of invites for a host player.
     *
     * @param host Host player UUID.
     * @throws SQLException If a database access error occurs.
     */
    void incrementHostInvited(UUID host) throws SQLException;

    /**
     * Save or update a referral entry in the database.
     *
     * @param referral Referral object to store.
     * @throws SQLException If a database access error occurs.
     */
    void saveReferral(Referral referral) throws SQLException;


    /* -------- Daily / Weekly / Monthly / Streak -------- */

    /**
     * Retrieve a daily reward claim for a player and reward key.
     *
     * @param player Player UUID.
     * @param reward Reward key.
     * @return DailyReward object or null.
     * @throws SQLException If a database access error occurs.
     */
    DailyReward getDailyReward(UUID player, String reward) throws SQLException;

    /**
     * Retrieve the last time the player claimed a reward from a table.
     *
     * @param playerId Player UUID.
     * @param table    Name of the reward table.
     * @return Timestamp of last claim or null.
     * @throws SQLException If a database access error occurs.
     */
    Timestamp getLastClaim(UUID playerId, String table) throws SQLException;

    /**
     * Retrieve a weekly reward claim for a player and reward key.
     *
     * @param player Player UUID.
     * @param reward Reward key.
     * @return WeeklyReward object or null.
     * @throws SQLException If a database access error occurs.
     */
    WeeklyReward getWeeklyReward(UUID player, String reward) throws SQLException;

    /**
     * Retrieve a monthly reward claim for a player and reward key.
     *
     * @param player Player UUID.
     * @param reward Reward key.
     * @return MonthlyReward object or null.
     * @throws SQLException If a database access error occurs.
     */
    MonthlyReward getMonthlyReward(UUID player, String reward) throws SQLException;

    /**
     * Retrieve the number of streak days for the player and the given condition.
     *
     * @param player    Player UUID.
     * @param condition Streak condition (LOGIN, EAT, etc).
     * @return Current streak day count.
     * @throws SQLException If a database access error occurs.
     */
    int getStreakDays(UUID player, String condition) throws SQLException;


    /**
     * Store or update the number of streak days for a player and a given condition.
     *
     * @param player    Player UUID.
     * @param condition Streak condition (LOGIN, EAT, etc).
     * @param days      Number of streak days to set.
     * @throws SQLException If a database access error occurs.
     */
    void setStreakDays(UUID player, String condition, int days) throws SQLException;

    LocalDate getLastUpdate(UUID player, String condition) throws SQLException;

    /**
     * Retrieve AFK reward claim data for a player and reward key.
     *
     * @param player Player UUID.
     * @param reward Reward key.
     * @return AfkReward object or null.
     * @throws SQLException If a database access error occurs.
     */
    AfkReward getAfkReward(UUID player, String reward) throws SQLException;

    /**
     * Retrieve Playtime reward claim data for a player and reward key.
     *
     * @param player Player UUID.
     * @param reward Reward key.
     * @return PlaytimeReward object or null.
     * @throws SQLException If a database access error occurs.
     */
    PlaytimeReward getPlaytimeReward(UUID player, String reward) throws SQLException;

    /**
     * Save or update a daily reward for a player.
     *
     * @param dr DailyReward object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void saveDailyReward(DailyReward dr) throws SQLException;

    /**
     * Save or update a weekly reward for a player.
     *
     * @param wr WeeklyReward object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void saveWeeklyReward(WeeklyReward wr) throws SQLException;

    /**
     * Save or update a monthly reward for a player.
     *
     * @param mr MonthlyReward object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void saveMonthlyReward(MonthlyReward mr) throws SQLException;

    /**
     * Returns whether the player has claimed a streak reward for a specific condition and streak day.
     *
     * @param player    The player UUID.
     * @param condition Streak condition (LOGIN, EAT, etc).
     * @param streakDay The streak day number (e.g. 1, 2, 5).
     * @return True if claimed, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    boolean hasClaimedStreakReward(UUID player, String condition, int streakDay) throws SQLException;

    /**
     * Save or update a claim for a streak reward for a specific player, condition and streak day.
     *
     * @param player    The player UUID.
     * @param condition Streak condition (LOGIN, EAT, etc).
     * @param streakDay The streak day number.
     * @param reward    The reward key.
     * @throws SQLException If a database access error occurs.
     */
    void saveStreakRewardClaim(UUID player, String condition, int streakDay, String reward) throws SQLException;

    /**
     * Reset (set to 0) the number of streak days for a player and a given condition.
     *
     * @param player    Player UUID.
     * @param condition Streak condition (LOGIN, EAT, etc).
     * @throws SQLException If a database access error occurs.
     */
    void resetStreak(UUID player, String condition) throws SQLException;

    /**
     * Save or update an AFK reward for a player.
     *
     * @param s AfkReward object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void saveAfkReward(AfkReward s) throws SQLException;

    /**
     * Get Database Connection.
     *
     * @throws SQLException If a database access error occurs.
     */
    Connection getConnection() throws SQLException;

    /**
     * Save or update a Playtime reward for a player.
     *
     * @param s PlaytimeReward object to persist.
     * @throws SQLException If a database access error occurs.
     */
    void savePlaytimeReward(PlaytimeReward s)  throws SQLException;

}

