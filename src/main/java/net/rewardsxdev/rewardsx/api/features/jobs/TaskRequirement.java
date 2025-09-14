package net.rewardsxdev.rewardsx.api.features.jobs;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a requirement task for a job level.
 * Defines the item to collect or action target, the required amount, and the possible rewards.
 */
@Getter
public class TaskRequirement {

    /**
     * The identifier of the item or action to track (es: "STONE_BLOCK", "DIAMOND").
     */
    private final String item;

    /**
     * The amount required to complete the task.
     */
    private final int amount;

    /**
     * The list of rewards to give when the task is completed.
     * Can be empty but never null.
     */
    private final List<JobReward> rewards;

    /**
     * Constructs a TaskRequirement with its associated item, amount and rewards.
     *
     * @param item    the item or action key required (not null/empty)
     * @param amount  how many times the task must be completed (must be > 0)
     * @param rewards the rewards list to give; can be empty but not null
     */
    public TaskRequirement(String item, int amount, List<JobReward> rewards) {
        if (item == null || item.isEmpty()) {
            throw new IllegalArgumentException("item cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        // Non accettare null come lista rewards
        this.item = item;
        this.amount = amount;
        this.rewards = (rewards != null) ? rewards : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "TaskRequirement{" +
                "item='" + item + '\'' +
                ", amount=" + amount +
                ", rewards=" + (rewards != null ? rewards.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskRequirement)) return false;
        TaskRequirement that = (TaskRequirement) o;
        // Se i rewards non sono parte dell'identità, NON includerli
        return amount == that.amount && item.equals(that.item);
    }

    @Override
    public int hashCode() {
        // Idem: solo item e amount. Se vuoi rewards, aggiungili qui
        return Objects.hash(item, amount);
    }
}
