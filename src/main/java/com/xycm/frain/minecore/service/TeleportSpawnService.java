package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MainConfig;
import com.xycm.frain.minecore.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TeleportSpawnService {

    public static void teleport(CommandSender sender, String target) {
        if (target == null) {
            if (sender instanceof Player player) {
                teleportSelf(player);
            } else {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerOnly());
            }
        } else {
            Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer == null) {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerNotFound(), "{player}", target);
            } else {
                teleportOther(sender, targetPlayer);
            }
        }
    }

    private static void teleportSelf(Player player) {
        player.teleport(MainConfig.getInstance().getSpawn().getLocation());
        MessageService.send(player, MessageConfig.getInstance().getSpawnSuccess());
    }

    private static void teleportOther(CommandSender sender, Player player) {
        player.teleport(MainConfig.getInstance().getSpawn().getLocation());
        MessageService.send(sender, MessageConfig.getInstance().getSpawnOthers(), "{player}", player.getName());
    }
}
