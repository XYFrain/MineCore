package com.xycm.frain.minecore.service;

import lombok.experimental.UtilityClass;

import org.bukkit.entity.Player;

import com.xycm.frain.minecore.config.MessageConfig;

@UtilityClass
public class PlayerSuicideService {

    public void execute(Player player) {
        player.setHealth(0);
        MessageService.send(player, MessageConfig.getInstance().getSuicideSuccess());
    }
}
