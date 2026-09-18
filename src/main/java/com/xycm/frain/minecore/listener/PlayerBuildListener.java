package com.xycm.frain.minecore.listener;

import com.xycm.frain.minecore.service.PlayerBuildService;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

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

    @EventHandler // 盔甲架交互
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (!PlayerBuildService.isEnabled(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler // 拆展示框/画/栓绳
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player player) {
            if (!PlayerBuildService.isEnabled(player)) event.setCancelled(true);
        } else if (event.getRemover() instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            if (!PlayerBuildService.isEnabled(player)) event.setCancelled(true);
        }
    }

    @EventHandler // 拆船/矿车
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (!(event.getAttacker() instanceof Player player)) return;
        if (!PlayerBuildService.isEnabled(player)) event.setCancelled(true);
    }

    @EventHandler // 拆盔甲架
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof ArmorStand)) return;
        if (event.getDamager() instanceof Player player) {
            if (!PlayerBuildService.isEnabled(player)) event.setCancelled(true);
        } else if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            if (!PlayerBuildService.isEnabled(player)) event.setCancelled(true);
        }
    }
}
