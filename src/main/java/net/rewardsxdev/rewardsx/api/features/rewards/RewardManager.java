package net.rewardsxdev.rewardsx.api.features.rewards;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RewardManager {
    private static final Map<String, Supplier<Reward>> registry = new HashMap<>();
    public static void registerFactory(String prefix, Supplier<Reward> factory) {
        registry.put(prefix, factory);
    }
    public static Reward getReward(String prefix) {
        Supplier<Reward> sup = registry.get(prefix);
        return (sup != null) ? sup.get() : null;
    }
    public static Iterable<String> getRegisteredPrefixes() {
        return registry.keySet();
    }

}

