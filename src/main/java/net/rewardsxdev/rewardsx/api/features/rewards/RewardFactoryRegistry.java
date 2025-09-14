package net.rewardsxdev.rewardsx.api.features.rewards;

import java.util.function.Supplier;

public interface RewardFactoryRegistry {
    void register(String prefix, Supplier<Reward> factory);
}

