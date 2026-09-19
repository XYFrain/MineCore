package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class PlayerBuildService {
    private final Map<UUID, Long> playerBuildModeRegistry = new HashMap<>();

    public void execute(Player player, int time) {
        if (time <= 0) {
            MessageService.send(player, MessageConfig.getInstance().getInvalidArgument());
            return;
        }
        playerBuildModeRegistry.put(player.getUniqueId(), System.currentTimeMillis() + time * 60_000L);
        MessageService.send(player, MessageConfig.getInstance().getBuildModeEnabled(), "{time}", String.valueOf(time));
    }

    public boolean isEnabled(Player player) {
        Long expiryTime = playerBuildModeRegistry.get(player.getUniqueId());
        return expiryTime != null && System.currentTimeMillis() < expiryTime;
    }
}
