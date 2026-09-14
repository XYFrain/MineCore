package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerHealService {

    public static void execute(CommandSender sender, String target) {
        if (target == null) {
            if (sender instanceof Player player) {
                executeSelf(player);
            } else {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerOnly());
            }
        } else {
            Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer == null) {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerNotFound(), "{player}", target);
            } else {
                executeOther(sender, targetPlayer);
            }
        }
    }

    private static void executeSelf(Player player) {
        double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(max);
        MessageService.send(player, MessageConfig.getInstance().getHealSuccess());
    }

    private static void executeOther(CommandSender sender, Player player) {
        double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(max);
        MessageService.send(sender, MessageConfig.getInstance().getHealOthers(), "{player}", player.getName());
    }
}
