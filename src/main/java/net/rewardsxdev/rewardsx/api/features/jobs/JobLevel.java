package net.rewardsxdev.rewardsx.api.features.jobs;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a job level with task requirements and rewards.
 */
@Getter
public class JobLevel {

    /**
     * -- GETTER --
     *  Returns the numeric level for this job stage.
     *
     * @return level number
     */
    private final int level;  // Numeric level identifier (e.g., 1, 2, ...)
    /**
     * -- GETTER --
     *  Returns an unmodifiable map of task requirements for this level.
     *  The key represents a task (e.g., "stone", "iron_ore").
     *
     * @return map of task keys to TaskRequirement objects
     */
    private final Map<String, TaskRequirement> taskRequirements; // Task key (such as "stone") to requirement
    /**
     * -- GETTER --
     *  Returns an unmodifiable list of rewards for this level.
     *
     * @return list of JobReward objects
     */
    private final List<JobReward> rewards; // Rewards for completing this level

    /**
     * Constructs a JobLevel with all required details.
     *
     * @param level The numeric level of this stage within the job.
     * @param taskRequirements Map of task keys to their requirements (unmodifiable).
     * @param rewards List of rewards associated with this level (unmodifiable).
     */
    public JobLevel(int level, Map<String, TaskRequirement> taskRequirements, List<JobReward> rewards) {
        this.level = level;
        this.taskRequirements = taskRequirements == null ? Collections.emptyMap() : Collections.unmodifiableMap(taskRequirements);
        this.rewards = rewards == null ? Collections.emptyList() : Collections.unmodifiableList(rewards);
    }

    @Override
    public String toString() {
        return "JobLevel{" +
                "level=" + level +
                ", taskRequirements=" + taskRequirements.keySet() +
                ", rewards=" + rewards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobLevel)) return false;
        JobLevel jobLevel = (JobLevel) o;
        return level == jobLevel.level &&
                taskRequirements.equals(jobLevel.taskRequirements) &&
                rewards.equals(jobLevel.rewards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, taskRequirements, rewards);
    }
}
