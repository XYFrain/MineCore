package com.xycm.frain.minecore.manager;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.config.MainConfig;
import com.xycm.frain.minecore.config.MessageConfig;

import lombok.experimental.UtilityClass;
import java.io.File;


@UtilityClass
public class ConfigManager {

    //------------------------------------------------------------------------------------------------------------------

    public static void init() {
        loadMainConfig();
        loadMessageConfig();
        DataManager.loadWarpData();
        MineCore.getInstance().getLogger().info("配置已加载");
    }

    public static void reload() {
        loadMainConfig();
        loadMessageConfig();
        DataManager.loadWarpData();
        MineCore.getInstance().getLogger().info("配置已重载");
    }

    //------------------------------------------------------------------------------------------------------------------

    private static void loadMainConfig() {
        File file = new File(MineCore.getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) MineCore.getInstance().saveResource("config.yml", false);
        MainConfig.load(file);
    }

    private static void loadMessageConfig() {
        String lang = MainConfig.getInstance().getLanguage();
        File file = new File(MineCore.getInstance().getDataFolder(), "messages/" + lang + ".yml");
        if (!file.exists()) MineCore.getInstance().saveResource("messages/" + lang + ".yml", false);
        MessageConfig.load(file);
    }
}
