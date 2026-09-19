package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TeleportTpHereService {
    public void teleport(Player player, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            MessageService.send(player, MessageConfig.getInstance().getPlayerNotFound(), "{player}", target);
        }else {
            targetPlayer.teleport(player.getLocation());
            MessageService.send(player, MessageConfig.getInstance().getTpHereSuccess(), "{player}", targetPlayer.getName());
        }
    }
}