package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.manager.DataManager;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;


@UtilityClass
public class TeleportBackService {

    public void execute(Player player) {
        if (!DataManager.hasBackLocation(player.getUniqueId())) {
            MessageService.send(player, MessageConfig.getInstance().getBackNoLocation());
            return;
        }
        player.teleport(DataManager.getBackLocation(player.getUniqueId()));
    }
}
