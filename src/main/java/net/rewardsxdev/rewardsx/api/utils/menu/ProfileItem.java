package net.rewardsxdev.rewardsx.api.utils.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProfileItem {
    // Left/right click commands: key = "left_click", "right_click", etc.
    private Map<String, List<String>> clickCommands;

    private final String name;
    private final String material;
    private final String materialCompleted;
    private final String streakCondition; // es: "LOGIN", "EAT", ecc.
    private Integer streakDay;      // es: 1, 2, 5, 10
    private final int data;
    private final List<String> lore;
    private List<Integer> slot;
    private List<String> enchantments;
    private boolean hide_enchantments;
    private final int size;
    private final String title;
    private List<String> commands;

    public ProfileItem(String name, List<String> lore, List<Integer> slot, String material, String materialCompleted, int data, List<String> commands,
                       List<String> enchantments, boolean hide_enchantments,
                       String title, int size, String streakCondition) {
        this.name = name;
        this.material = material;
        this.data = data;
        this.lore = lore;
        this.slot = slot;
        this.title = title;
        this.size = size;
        this.commands = commands;
        this.enchantments = enchantments;
        this.hide_enchantments = hide_enchantments;
        this.clickCommands = null;
        // Default streak fields to null
        this.streakCondition = streakCondition;
        this.streakDay = null;
        this.materialCompleted = materialCompleted;
    }

    @Override
    public String toString() {
        return "ProfileItem{" +
                "name='" + name + '\'' +
                ", lore=" + lore +
                ", slot=" + slot +
                ", material='" + material + '\'' +
                ", materialCompleted='" + materialCompleted + '\'' +
                ", data=" + data +
                ", commands=" + commands +
                ", clickCommands=" + clickCommands +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
