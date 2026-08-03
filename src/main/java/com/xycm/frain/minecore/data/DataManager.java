package com.xycm.frain.minecore.data;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.model.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

/**
 * 数据层 —— 负责所有 yml 读写。
 * <p>
 * 文件布局：
 * <pre>
 * plugins/MineCore/
 * ├── userdata/
 * │   └── &lt;uuid&gt;.yml      # 玩家数据：home 列表
 * └── warps.yml            # 公共传送点
 * </pre>
 */
public class DataManager {

    private final File userdataDir;
    private final File warpsFile;

    /** 内存中的 warp 缓存（启动时加载，修改时立即写盘并同步）。 */
    private final Map<String, Location> warps = new LinkedHashMap<>();

    public DataManager() {
        File dataFolder = MineCore.getInstance().getDataFolder();
        this.userdataDir = new File(dataFolder, "userdata");
        if (!userdataDir.exists()) {
            userdataDir.mkdirs();
        }
        this.warpsFile = new File(dataFolder, "warps.yml");
        loadWarps();
    }

    // ========== 玩家数据 ==========

    /** 检查玩家数据文件是否存在。 */
    public boolean exists(UUID uuid) {
        return userdataFileOf(uuid).exists();
    }

    /** 创建默认（空）玩家数据文件。 */
    public void createDefault(UUID uuid) {
        savePlayerData(new PlayerData(uuid));
    }

    /** 加载玩家数据；文件不存在或解析失败时返回空数据（不报错）。 */
    public PlayerData loadPlayerData(UUID uuid) {
        PlayerData data = new PlayerData(uuid);
        File file = userdataFileOf(uuid);
        if (!file.exists()) {
            return data;
        }
        try {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection homesSection = yml.getConfigurationSection("homes");
            if (homesSection != null) {
                for (String name : homesSection.getKeys(false)) {
                    Location loc = loadLocation(homesSection, name);
                    if (loc != null) {
                        data.setHome(name, loc);
                    }
                }
            }
        } catch (Exception e) {
            MineCore.getInstance().getLogger().log(Level.WARNING,
                    "加载玩家数据失败: " + uuid, e);
        }
        return data;
    }

    /** 保存玩家数据；覆盖式写入。 */
    public void savePlayerData(PlayerData data) {
        File file = userdataFileOf(data.getUuid());
        YamlConfiguration yml = new YamlConfiguration();
        for (Map.Entry<String, Location> entry : data.getHomes().entrySet()) {
            saveLocation(yml, "homes." + entry.getKey(), entry.getValue());
        }
        try {
            yml.save(file);
        } catch (IOException e) {
            MineCore.getInstance().getLogger().log(Level.SEVERE,
                    "保存玩家数据失败: " + data.getUuid(), e);
        }
    }

    // ========== warp 数据 ==========

    /** 获取所有 warp（只读视图）。 */
    public Map<String, Location> getAllWarps() {
        return Collections.unmodifiableMap(warps);
    }

    public Location getWarp(String name) {
        return warps.get(name);
    }

    public boolean hasWarp(String name) {
        return warps.containsKey(name);
    }

    /** 设置 warp（重名覆盖），立即写盘。 */
    public void setWarp(String name, Location location) {
        warps.put(name, location);
        saveWarps();
    }

    /** 删除 warp，返回是否成功删除；立即写盘。 */
    public boolean delWarp(String name) {
        boolean removed = warps.remove(name) != null;
        if (removed) {
            saveWarps();
        }
        return removed;
    }

    private void loadWarps() {
        if (!warpsFile.exists()) return;
        try {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(warpsFile);
            ConfigurationSection section = yml.getConfigurationSection("warps");
            if (section == null) return;
            for (String name : section.getKeys(false)) {
                Location loc = loadLocation(section, name);
                if (loc != null) {
                    warps.put(name, loc);
                }
            }
        } catch (Exception e) {
            MineCore.getInstance().getLogger().log(Level.WARNING,
                    "加载 warps.yml 失败", e);
        }
    }

    private void saveWarps() {
        YamlConfiguration yml = new YamlConfiguration();
        for (Map.Entry<String, Location> entry : warps.entrySet()) {
            saveLocation(yml, "warps." + entry.getKey(), entry.getValue());
        }
        try {
            yml.save(warpsFile);
        } catch (IOException e) {
            MineCore.getInstance().getLogger().log(Level.SEVERE,
                    "保存 warps.yml 失败", e);
        }
    }

    // ========== 工具方法 ==========

    private File userdataFileOf(UUID uuid) {
        return new File(userdataDir, uuid + ".yml");
    }

    private Location loadLocation(ConfigurationSection section, String path) {
        if (!section.contains(path + ".world")) return null;
        String worldName = section.getString(path + ".world");
        if (worldName == null || Bukkit.getWorld(worldName) == null) return null;
        return new Location(
                Bukkit.getWorld(worldName),
                section.getDouble(path + ".x"),
                section.getDouble(path + ".y"),
                section.getDouble(path + ".z"),
                (float) section.getDouble(path + ".yaw"),
                (float) section.getDouble(path + ".pitch"));
    }

    private void saveLocation(YamlConfiguration yml, String path, Location loc) {
        yml.set(path + ".world", loc.getWorld().getName());
        yml.set(path + ".x", loc.getX());
        yml.set(path + ".y", loc.getY());
        yml.set(path + ".z", loc.getZ());
        yml.set(path + ".yaw", (double) loc.getYaw());
        yml.set(path + ".pitch", (double) loc.getPitch());
    }
}
