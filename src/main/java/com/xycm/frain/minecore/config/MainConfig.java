package com.xycm.frain.minecore.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * config.yml 对应的配置类。
 * <p>
 * 所有配置路径集中在这里，其他模块只通过有明确含义的方法读写配置。
 */
public final class MainConfig {

    private static final List<String> SUPPORTED_LANGUAGES = List.of("zh_CN");

    private static FileConfiguration mainConfig;

    private MainConfig() {}

    static void loadConfig(File file) {
        mainConfig = YamlConfiguration.loadConfiguration(file);
    }

    /** 获取插件语言；值为空或不支持时返回第一个支持的语言。 */
    public static String getLanguage() {
        String lang = mainConfig.getString("Language");
        if (lang == null || !SUPPORTED_LANGUAGES.contains(lang)) {
            return SUPPORTED_LANGUAGES.get(0);
        }
        return lang;
    }

    /** 从配置读取出生点，世界不存在时返回 null。 */
    public static Location getSpawnLocation() {
        String worldName = mainConfig.getString("spawn.world", "world");
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;
        return new Location(
                world,
                mainConfig.getDouble("spawn.x", 0.5),
                mainConfig.getDouble("spawn.y", 64.0),
                mainConfig.getDouble("spawn.z", 0.5),
                (float) mainConfig.getDouble("spawn.yaw", 0.0),
                (float) mainConfig.getDouble("spawn.pitch", 0.0)
        );
    }

    /** 将玩家当前位置写入配置并保存。 */
    public static void setSpawnLocation(Location loc) {
        mainConfig.set("spawn.world", loc.getWorld().getName());
        mainConfig.set("spawn.x", loc.getX());
        mainConfig.set("spawn.y", loc.getY());
        mainConfig.set("spawn.z", loc.getZ());
        mainConfig.set("spawn.yaw", (double) loc.getYaw());
        mainConfig.set("spawn.pitch", (double) loc.getPitch());
    }

    /** 将配置写入文件。 */
    static void save(File file) throws IOException {
        mainConfig.save(file);
    }
}
