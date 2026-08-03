package com.xycm.frain.minecore.controller.listener;

import com.xycm.frain.minecore.config.MainConfig;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.service.teleport.BackService;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * 玩家事件监听 —— 出生点控制 + back 位置记录 + 玩家数据初始化。
 * <p>
 * 玩家 join 时若不存在数据文件，则生成默认空文件；
 * 退出时清理内存中的位置记忆，避免泄漏。
 */
public class PlayerListener implements Listener {

    private final BackService backService;
    private final DataManager dataManager;

    public PlayerListener(BackService backService, DataManager dataManager) {
        this.backService = backService;
        this.dataManager = dataManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Location spawn = MainConfig.getSpawnLocation();
        if (spawn != null) event.getPlayer().teleport(spawn);

        // 首次加入玩家：生成空数据文件
        if (!dataManager.exists(event.getPlayer().getUniqueId())) {
            dataManager.createDefault(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location spawn = MainConfig.getSpawnLocation();
        if (spawn != null) event.setRespawnLocation(spawn);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        backService.recordLocation(event.getPlayer(), event.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        backService.recordLocation(event.getEntity(), event.getEntity().getLocation());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        backService.onQuit(event.getPlayer());
    }
}
