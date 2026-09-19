package com.xycm.frain.minecore.listener;

import com.xycm.frain.minecore.manager.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackListener implements Listener {
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        DataManager.setBackLocation(event.getPlayer().getUniqueId(), event.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        DataManager.setBackLocation(event.getEntity().getUniqueId(), event.getEntity().getLocation());
    }
}
