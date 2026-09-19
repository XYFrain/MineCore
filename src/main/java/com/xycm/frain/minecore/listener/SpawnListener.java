package com.xycm.frain.minecore.listener;

import com.xycm.frain.minecore.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Location spawn = MainConfig.getInstance().getSpawn().getLocation();
        event.getPlayer().teleport(spawn);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location spawn = MainConfig.getInstance().getSpawn().getLocation();
        event.setRespawnLocation(spawn);
    }
}
