package com.xycm.frain.minecore.manager;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.config.MainConfig;
import com.xycm.frain.minecore.config.MessageConfig;

import lombok.experimental.UtilityClass;
import java.io.File;


@UtilityClass
public class ConfigManager {

    //------------------------------------------------------------------------------------------------------------------
    public void init() {
        loadMainConfig();
        loadMessageConfig();
        DataManager.loadWarpData();
    }

    public void reload() {
        loadMainConfig();
        loadMessageConfig();
        DataManager.loadWarpData();
    }

    //------------------------------------------------------------------------------------------------------------------
    private void loadMainConfig() {
        File file = new File(MineCore.getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) MineCore.getInstance().saveResource("config.yml", false);
        MainConfig.load(file);
    }

    private void loadMessageConfig() {
        String lang = MainConfig.getInstance().getLanguage();
        File file = new File(MineCore.getInstance().getDataFolder(), "messages/" + lang + ".yml");
        if (!file.exists()) MineCore.getInstance().saveResource("messages/" + lang + ".yml", false);
        MessageConfig.load(file);
    }
}
