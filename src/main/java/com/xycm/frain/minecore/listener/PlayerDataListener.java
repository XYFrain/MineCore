package com.xycm.frain.minecore.listener;

import com.xycm.frain.minecore.manager.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDataListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        DataManager.loadPlayerData(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        DataManager.unloadPlayerData(event.getPlayer().getUniqueId());
    }
}
