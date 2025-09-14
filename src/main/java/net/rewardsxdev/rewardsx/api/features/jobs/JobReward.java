package net.rewardsxdev.rewardsx.api.features.jobs;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a reward given for completing a job level.
 * Contains a list of commands to be executed as part of the reward.
 */
@Getter
public class JobReward {

    /**
     * -- GETTER --
     *  Returns the unmodifiable list of commands defined for this reward.
     *
     * @return list of commands
     */
    private final List<String> commands;  // List of commands to execute (e.g. [console] or [message])

    /**
     * Creates a JobReward with the specified list of commands.
     *
     * @param commands List of commands to be executed as reward; if null, initialized to empty list.
     */
    public JobReward(List<String> commands) {
        this.commands = commands == null ? Collections.emptyList() : Collections.unmodifiableList(commands);
    }

    @Override
    public String toString() {
        return "JobReward{" +
                "commands=" + commands +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobReward)) return false;
        JobReward jobReward = (JobReward) o;
        return commands.equals(jobReward.commands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commands);
    }
}
