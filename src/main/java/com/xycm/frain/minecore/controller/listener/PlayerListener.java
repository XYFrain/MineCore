package com.xycm.frain.minecore.controller.listener;

import com.xycm.frain.minecore.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家事件监听 —— 出生点控制 + 位置回溯。
 */
public class PlayerListener implements Listener {

    private static final Map<UUID, Location> lastLocation = new HashMap<>();

    /** 获取并清除玩家的上一次位置（供 back 命令使用）。 */
    public static Location popLastLocation(Player player) {
        return lastLocation.remove(player.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Location spawn = MainConfig.getSpawnLocation();
        if (spawn != null) event.getPlayer().teleport(spawn);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location spawn = MainConfig.getSpawnLocation();
        if (spawn != null) event.setRespawnLocation(spawn);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        lastLocation.put(event.getPlayer().getUniqueId(), event.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        lastLocation.put(event.getEntity().getUniqueId(), event.getEntity().getLocation());
    }
}
