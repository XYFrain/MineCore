package com.xycm.frain.minecore.model;

import org.bukkit.Location;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 玩家数据模型 —— 持有玩家持久化状态。
 * <p>
 * home 支持多个（按名字索引），数量不设上限。
 */
public class PlayerData {

    private final UUID uuid;
    private final Map<String, Location> homes = new LinkedHashMap<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    // ---- home 操作 ----

    public void setHome(String name, Location location) {
        homes.put(name, location);
    }

    public boolean delHome(String name) {
        return homes.remove(name) != null;
    }

    public Location getHome(String name) {
        return homes.get(name);
    }

    public boolean hasHome(String name) {
        return homes.containsKey(name);
    }

    public boolean hasAnyHome() {
        return !homes.isEmpty();
    }

    public Set<String> getHomeNames() {
        return Collections.unmodifiableSet(homes.keySet());
    }

    /** 返回所有 home 的只读视图（供 DataManager 序列化用）。 */
    public Map<String, Location> getHomes() {
        return Collections.unmodifiableMap(homes);
    }

    /** 仅当恰好有 1 个 home 时返回其名字；否则返回 null。 */
    public String getSingleHomeName() {
        if (homes.size() != 1) return null;
        return homes.keySet().iterator().next();
    }
}
