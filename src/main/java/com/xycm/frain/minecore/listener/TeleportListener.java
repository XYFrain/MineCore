package com.xycm.frain.minecore.listener;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.service.MessageService;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.COMMAND) return;
        Location to = event.getTo();
        MessageService.send(event.getPlayer(), MessageConfig.getInstance().getTeleportNotice(),
                "{x}", String.valueOf(to.getBlockX()),
                "{y}", String.valueOf(to.getBlockY()),
                "{z}", String.valueOf(to.getBlockZ()));
    }
}
