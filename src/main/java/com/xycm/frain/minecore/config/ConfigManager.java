package com.xycm.frain.minecore.config;

import com.xycm.frain.minecore.MineCore;

import java.io.File;

/**
 * 统一管理插件配置文件的初始化和重载。
 * <p>
 * 所有配置文件（config.yml、messages/ 等）的保存和加载在此集中，
 * 具体配置项由各自的配置类提供。
 */
public final class ConfigManager {

    private static MineCore plugin;
    /**
     * 初始化所有配置文件，插件启动时调用一次。
     */
    public static void init() {
        plugin = MineCore.getInstance();
        load();
        MineCore.getInstance().getLogger().info("配置已加载");
    }

    /**
     * 将 config.yml 写入文件（如不存在），并加载到内存。
     */
    private static void loadMainConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) plugin.saveResource("config.yml", false);
        MainConfig.loadConfig(file);
    }

    /**
     * 将 messages/<语言>.yml 写入文件（如不存在），并加载到内存。
     */
    private static void loadMessageConfig() {
        String lang = MainConfig.getLanguage();
        File file = new File(plugin.getDataFolder(), "messages/" + lang + ".yml");
        if (!file.exists()) plugin.saveResource("messages/" + lang + ".yml", false);
        MessageConfig.loadConfig(file);
    }

    /**
     * 加载所有配置。
     */
    private static void load() {
        loadMainConfig();
        loadMessageConfig();
    }

    /**
     * 重新加载所有配置（供 reload 命令使用）。
     */
    public static void reload() {
        load();
    }
}
