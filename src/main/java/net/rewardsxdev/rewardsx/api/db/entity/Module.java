package net.rewardsxdev.rewardsx.api.db.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Module {
    private String moduleName;
    private boolean actived;
    /* costruttore + getter/setter */
    public Module(String moduleName, boolean actived) {
        this.moduleName = moduleName;
        this.actived = actived;
    }
}
