package com.xycm.frain.minecore.manager;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.model.PlayerData;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

@UtilityClass
public class DataManager {

    private final File playerDataDir = new File(MineCore.getInstance().getDataFolder(), "userdata");
    private final File warpDataFile = new File(MineCore.getInstance().getDataFolder(), "warps.yml");

    private final Map<UUID, PlayerData> playerDataRegistry = new HashMap<>();
    private final Map<String, Location> warpDataRegistry = new LinkedHashMap<>();

    //------------------------------------------------------------------------------------------------------------------
    // 路径点数据
    //------------------------------------------------------------------------------------------------------------------
    public void loadWarpData() {
        warpDataRegistry.clear();
        if (!warpDataFile.exists()) return;
        YamlConfiguration warpDataConfig = YamlConfiguration.loadConfiguration(warpDataFile);
        for (String name : warpDataConfig.getKeys(false)) {
            Location location = loadLocation(warpDataConfig.getConfigurationSection(name));
            if (location == null) continue;
            warpDataRegistry.put(name, location);
        }
    }

    private void saveWarpData() {
        YamlConfiguration warpDataConfig = new YamlConfiguration();
        for (Map.Entry<String, Location> entry : warpDataRegistry.entrySet()) {
            warpDataConfig.set(entry.getKey(), saveLocation(entry.getValue()));
        }
        try {
            warpDataConfig.save(warpDataFile);
        } catch (IOException e) {
            throw new RuntimeException("保存 warps.yml 失败", e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // 玩家数据
    //------------------------------------------------------------------------------------------------------------------
    public void loadPlayerData(UUID uuid) {
        File playerDataFile = new File(playerDataDir, uuid + ".yml");
        PlayerData data = new PlayerData(uuid);
        if (!playerDataFile.exists()) {
            savePlayerData(data);
        } else {
            YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
            // Home
            ConfigurationSection homesSection = playerDataConfig.getConfigurationSection("homes");
            if (homesSection != null) {
                for (String name : homesSection.getKeys(false)) {
                    Location location = loadLocation(homesSection.getConfigurationSection(name));
                    if (location != null) {
                        data.getHomes().put(name, location);
                    }
                }
            }
            // Back
            ConfigurationSection backSection = playerDataConfig.getConfigurationSection("back-location");
            if (backSection != null) {
                data.setBackLocation(loadLocation(backSection));
            }
        }
        playerDataRegistry.put(uuid, data);
    }

    public void unloadPlayerData(UUID uuid) {
        playerDataRegistry.remove(uuid);
    }

    private void savePlayerData(PlayerData data) {
        File playerDataFile = new File(playerDataDir, data.getUuid() + ".yml");
        playerDataFile.getParentFile().mkdirs();
        YamlConfiguration playerDataConfig = new YamlConfiguration();
        for (Map.Entry<String, Location> entry : data.getHomes().entrySet()) {
            playerDataConfig.set("homes." + entry.getKey(), saveLocation(entry.getValue()));
        }
        if (data.getBackLocation() != null) {
            playerDataConfig.set("back-location", saveLocation(data.getBackLocation()));
        }
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            throw new RuntimeException("保存 " + playerDataFile.getName() + " 失败", e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // 路径点数据
    //------------------------------------------------------------------------------------------------------------------
    public List<String> getAllWarps() {
        return new ArrayList<>(warpDataRegistry.keySet());
    }

    public boolean hasWarp(String name) {
        return warpDataRegistry.containsKey(name);
    }

    public Location getWarp(String name) {
        return warpDataRegistry.get(name);
    }

    public void setWarp(String name, Location location) {
        warpDataRegistry.put(name, location);
        saveWarpData();
    }

    public void delWarp(String name) {
        warpDataRegistry.remove(name);
        saveWarpData();
    }

    //------------------------------------------------------------------------------------------------------------------
    // 玩家数据
    //------------------------------------------------------------------------------------------------------------------
    public List<String> getAllHomes(UUID uuid) {
        return new ArrayList<>(playerDataRegistry.get(uuid).getHomes().keySet());
    }

    public boolean hasHome(UUID uuid, String name) {
        return playerDataRegistry.get(uuid).getHomes().containsKey(name);
    }

    public Location getHome(UUID uuid, String name) {
        return playerDataRegistry.get(uuid).getHomes().get(name);
    }

    public void setHome(UUID uuid, String name, Location location) {
        PlayerData data = playerDataRegistry.get(uuid);
        data.getHomes().put(name, location);
        savePlayerData(data);
    }

    public void delHome(UUID uuid, String name) {
        PlayerData data = playerDataRegistry.get(uuid);
        data.getHomes().remove(name);
        savePlayerData(data);
    }

    public boolean hasBackLocation(UUID uuid) {
        return playerDataRegistry.get(uuid).getBackLocation() != null;
    }

    public Location getBackLocation(UUID uuid) {
        return playerDataRegistry.get(uuid).getBackLocation();
    }

    public void setBackLocation(UUID uuid, Location location) {
        PlayerData data = playerDataRegistry.get(uuid);
        data.setBackLocation(location);
        savePlayerData(data);
    }


    //------------------------------------------------------------------------------------------------------------------
    // 私有方法
    //------------------------------------------------------------------------------------------------------------------
    private Location loadLocation(ConfigurationSection section) {
        try {
            return new Location(
                    Bukkit.getWorld(section.getString("world")),
                    section.getDouble("x"),
                    section.getDouble("y"),
                    section.getDouble("z"),
                    (float) section.getDouble("yaw"),
                    (float) section.getDouble("pitch"));
        } catch (Exception e) {
            return null;
        }
    }

    private ConfigurationSection saveLocation(Location location) {
        YamlConfiguration section = new YamlConfiguration();
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("yaw", (double) location.getYaw());
        section.set("pitch", (double) location.getPitch());
        return section;
    }
}