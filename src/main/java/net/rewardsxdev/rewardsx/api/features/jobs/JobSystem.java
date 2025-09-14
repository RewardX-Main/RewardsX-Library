package net.rewardsxdev.rewardsx.api.features.jobs;

import java.sql.SQLException;
import java.util.*;

public interface JobSystem {

    void init();

    String getName();

    /**
     * Ritorna tutti i job disponibili.
     */
    List<Job> getAvailableJobs();

    /**
     * Return player job from database.
     */
    net.rewardsxdev.rewardsx.api.db.entity.Job getPlayerJob(UUID playerId) throws SQLException;

    int getJobTotalGoal(String jobName, int level);

    int getTaskGoal(String jobName, int level, String taskKey);

    /**
     * Return player job from JobManager.
     */


    Set<String> getActiveJobs(UUID playerId);

    void addActiveJob(UUID playerId, String jobName);

    void removeActiveJob(UUID playerId, String jobName);

    net.rewardsxdev.rewardsx.api.db.entity.Job getPlayerJob(UUID playerId, String jobName) throws  SQLException;

    Optional<PlayerJobProgress> getPlayerJobProgress(UUID playerId) throws SQLException;
    Optional<PlayerJobProgress> getPlayerJobProgress(UUID playerId, String jobName) throws SQLException;

    /**
     * Aggiorna il progresso di un giocatore per una task (es: break block).
     */
    void progressJob(UUID playerId, String jobName, String taskKey, int amount);

    /**
     * Prova a consegnare la ricompensa se la task corrente è completa.
     */
    boolean tryGiveReward(UUID playerId, String jobName);

    /**
     * Gestione livelli.
     */
    void promoteToNextLevel(UUID playerId, String jobName);

    void loadJobs();
}
