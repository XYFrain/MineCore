package com.xycm.frain.minecore.listener;

import com.xycm.frain.minecore.service.PlayerBuildService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerBuildListener implements Listener {

    @EventHandler // 放置
    public void onPlace(BlockPlaceEvent event) {
        if (!PlayerBuildService.isEnabled(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler // 破坏
    public void onBreak(BlockBreakEvent event) {
        if (!PlayerBuildService.isEnabled(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler // 交互
    public void onInteract(PlayerInteractEvent event) {
        if (!PlayerBuildService.isEnabled(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler // 实体交互
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (!PlayerBuildService.isEnabled(event.getPlayer())) event.setCancelled(true);
    }
}
