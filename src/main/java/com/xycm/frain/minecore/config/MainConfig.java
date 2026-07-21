package com.xycm.frain.minecore.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * config.yml 对应的配置类。
 * <p>
 * 所有配置路径集中在这里，其他模块只通过有明确含义的方法读取配置。
 */
public final class MainConfig {

    private static final List<String> SUPPORTED_LANGUAGES = List.of("zh_CN");

    private static FileConfiguration mainConfig;

    private MainConfig() {}

    static void loadConfig(File file) {
        mainConfig = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 获取插件语言；值为空或不支持时返回 {@link SUPPORTED_LANGUAGES} 的第一个值。
     */
    public static String getLanguage(){
        String lang = mainConfig.getString("Language");
        if (lang == null || !SUPPORTED_LANGUAGES.contains(lang)) {
            return SUPPORTED_LANGUAGES.get(0);
        }
        return lang;
    }
}
