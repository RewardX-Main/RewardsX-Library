package net.rewardsxdev.rewardsx.api.features.jobs;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a configurable Job with levels, action type, and lore.
 */
@Getter
public class Job {

    /**
     * -- GETTER --
     *  Returns the job's unique name.
     *
     * @return job name
     */
    private final String name;                      // Job's unique name, e.g. "minator"
                       // GUI slot or other numeric identifier
    /**
     * -- GETTER --
     *  Returns the action type associated with this job.
     *
     * @return action type enum
     */
    private final ActionType action;                // Associated action type (e.g. BREAK_BLOCK)
    /**
     * -- GETTER --
     *  Returns an unmodifiable map of job levels.
     *
     * @return map of level numbers to JobLevel objects
     */
    private final Map<Integer, JobLevel> levels;   // Map of levels (level -> JobLevel details)

    /**
     * Main constructor.
     *
     * @param name Job's unique identifier
     * @param action Action type linked to the job (like BREAK_BLOCK)
     * @param levels Unmodifiable map of job levels
     */
    public Job(String name, ActionType action, Map<Integer, JobLevel> levels) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.action = Objects.requireNonNull(action, "action cannot be null");
        this.levels = levels == null ? Collections.emptyMap() : Collections.unmodifiableMap(levels);
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", action=" + action +
                ", levels=" + levels.keySet() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;
        return name.equals(job.name) &&
                action == job.action &&
                levels.equals(job.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, action, levels);
    }
}
