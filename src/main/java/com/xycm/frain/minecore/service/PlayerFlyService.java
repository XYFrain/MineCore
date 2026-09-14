package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerFlyService {

    public static void toggle(CommandSender sender, String target) {
        if (target == null) {
            if (sender instanceof Player player) {
                toggleSelf(player);
            } else {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerOnly());
            }
        } else {
            Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer == null) {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerNotFound(), "{player}", target);
            } else {
                toggleOther(sender, targetPlayer);
            }
        }
    }

    private static void toggleSelf(Player player) {
        boolean enabled = !player.getAllowFlight();
        player.setAllowFlight(enabled);
        MessageService.send(player, enabled ? MessageConfig.getInstance().getFlyEnabled() : MessageConfig.getInstance().getFlyDisabled());
    }

    private static void toggleOther(CommandSender sender, Player player) {
        boolean enabled = !player.getAllowFlight();
        player.setAllowFlight(enabled);
        MessageService.send(sender, enabled ? MessageConfig.getInstance().getFlyEnabledOthers() : MessageConfig.getInstance().getFlyDisabledOthers(), "{player}", player.getName());
    }
}
