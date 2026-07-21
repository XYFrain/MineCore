package com.xycm.frain.minecore.config;

import com.xycm.frain.minecore.MineCore;

import java.io.File;
import java.io.IOException;

/**
 * 统一管理插件配置文件的初始化、重载和保存。
 */
public final class ConfigManager {

    private static MineCore plugin;
    private static File mainConfigFile;

    private ConfigManager() {}

    public static void init() {
        plugin = MineCore.getInstance();
        load();
        MineCore.getInstance().getLogger().info("配置已加载");
    }

    public static void reload() {
        load();
    }

    /** 保存 config.yml 到磁盘。 */
    public static void saveMainConfig() {
        try {
            MainConfig.save(mainConfigFile);
        } catch (IOException e) {
            plugin.getLogger().warning("保存 config.yml 失败: " + e.getMessage());
        }
    }

    // ---- private ----

    private static void load() {
        loadMainConfig();
        loadMessageConfig();
    }

    private static void loadMainConfig() {
        mainConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!mainConfigFile.exists()) plugin.saveResource("config.yml", false);
        MainConfig.loadConfig(mainConfigFile);
    }

    private static void loadMessageConfig() {
        String lang = MainConfig.getLanguage();
        File file = new File(plugin.getDataFolder(), "messages/" + lang + ".yml");
        if (!file.exists()) plugin.saveResource("messages/" + lang + ".yml", false);
        MessageConfig.loadConfig(file);
    }
}
