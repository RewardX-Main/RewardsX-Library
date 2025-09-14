package net.rewardsxdev.rewardsx.api.utils.menu;

import lombok.Getter;

import java.io.File;

public class Create {
    private File folder;
    private String child;
    @Getter
    private File menusFolder;
    public Create(File folder, String child){
        this.folder = folder;
        this.child = child;
    }

    public void init() {
        menusFolder = new File(folder, child);
        if (!menusFolder.exists()) {
            menusFolder.mkdirs();
        }
    }

}
