package net.rewardsxdev.rewardsx.api.features.jobs;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the progress of a player in a specific job.
 * Tracks current level and progress for individual task keys/items.
 */
public class PlayerJobProgress {

    /**
     * -- GETTER --
     *  Returns the player's UUID.
     *
     * @return UUID of the player
     */
    @Getter
    private final UUID playerId;                    // Unique ID of the player
    /**
     * -- GETTER --
     *  Returns the name of the job.
     *
     * @return job name
     */
    @Getter
    private final String jobName;                    // Name of the job being progressed
    /**
     * -- GETTER --
     *  Returns the current level/stage of the player in this job.
     *
     *
     * -- SETTER --
     *  Sets the current level of the player in this job.
     *
     @return current level
      * @param currentLevel new current level
     */
    @Setter
    @Getter
    private int currentLevel;                        // Current level/stage in the job
    private final Map<String, Integer> taskProgress; // Map of task key -> progress count (e.g., "stone" -> 7)

    /**
     * Constructs a PlayerJobProgress with given data.
     *
     * @param playerId     UUID of the player
     * @param jobName      Name of the job
     * @param currentLevel Current level of the player in the job
     * @param taskProgress Map of task progress; copied internally to modifiable map
     */
    public PlayerJobProgress(UUID playerId, String jobName, int currentLevel, Map<String, Integer> taskProgress) {
        this.playerId = Objects.requireNonNull(playerId, "playerId cannot be null");
        this.jobName = Objects.requireNonNull(jobName, "jobName cannot be null");
        this.currentLevel = currentLevel;
        this.taskProgress = taskProgress == null ? new HashMap<>() : new HashMap<>(taskProgress);
    }

    /**
     * Returns an unmodifiable view of the task progress map.
     *
     * @return unmodifiable map of task progress
     */
    public Map<String, Integer> getTaskProgress() {
        return Collections.unmodifiableMap(taskProgress);
    }

    /**
     * Adds progress to a specific task key/item.
     * If no prior progress, starts from zero.
     *
     * @param taskKey the task/item identifier (e.g., "stone")
     * @param amount  amount to add to the current progress
     * @return the new total progress for this task
     */
    public int addProgress(String taskKey, int amount) {
        if (taskKey == null || amount <= 0) {
            throw new IllegalArgumentException("taskKey cannot be null and amount must be positive");
        }
        int current = taskProgress.getOrDefault(taskKey, 0);
        int updated = current + amount;
        taskProgress.put(taskKey, updated);
        return updated;
    }

    // Mappa: nome task -> true se reward già dato (resetta ogni livello!)
    private final Map<String, Boolean> taskRewardGiven = new HashMap<>();

    /**
     * -- GETTER --
     * Ritorna true se il reward di livello è già stato dato durante questo livello.
     * -- SETTER --
     * Imposta il flag: reward di livello data.

     */
    // Flag: reward di livello dato (resetta ogni promozione di livello)
    @Setter
    @Getter
    private boolean levelRewardGiven = false;

    /** Ritorna true se la ricompensa per la task è già stata data durante questo livello. */
    public boolean isTaskRewardGiven(String taskKey) {
        return taskRewardGiven.getOrDefault(taskKey, false);
    }

    /** Imposta il flag: reward data per la task corrente. */
    public void setTaskRewardGiven(String taskKey, boolean given) {
        if (taskKey != null) {
            taskRewardGiven.put(taskKey, given);
        }
    }

    /** Reset dei flag reward (da chiamare quando si avanza livello!) */
    public void resetTaskRewards() {
        taskRewardGiven.clear();
        levelRewardGiven = false;
    }


    /**
     * Resets the progress for a specific task key.
     *
     * @param taskKey the task/item identifier to reset
     */
    public void resetProgress(String taskKey) {
        if (taskKey != null) {
            taskProgress.remove(taskKey);
        }
    }

    /**
     * Resets all task progress to empty.
     */
    public void resetAllProgress() {
        taskProgress.clear();
    }

    @Override
    public String toString() {
        return "PlayerJobProgress{" +
                "playerId=" + playerId +
                ", jobName='" + jobName + '\'' +
                ", currentLevel=" + currentLevel +
                ", taskProgress=" + taskProgress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerJobProgress)) return false;
        PlayerJobProgress that = (PlayerJobProgress) o;
        return currentLevel == that.currentLevel &&
                playerId.equals(that.playerId) &&
                jobName.equals(that.jobName) &&
                taskProgress.equals(that.taskProgress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, jobName, currentLevel, taskProgress);
    }
}
