package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

@UtilityClass
public class PlayerGamemodeService {
    public void change(CommandSender sender, String gamemode, String target) {
        GameMode resolved = switch (gamemode.toLowerCase(Locale.ROOT)) {
            case "survival", "0" -> GameMode.SURVIVAL;
            case "creative", "1" -> GameMode.CREATIVE;
            case "adventure", "2" -> GameMode.ADVENTURE;
            case "spectator", "3" -> GameMode.SPECTATOR;
            default -> null;
        };
        if (resolved == null) {
            MessageService.send(sender, MessageConfig.getInstance().getInvalidArgument());
            return;
        }
        String gamemodeName = switch (resolved) {
            case SURVIVAL -> MessageConfig.getInstance().getModeSurvival();
            case CREATIVE -> MessageConfig.getInstance().getModeCreative();
            case ADVENTURE -> MessageConfig.getInstance().getModeAdventure();
            case SPECTATOR -> MessageConfig.getInstance().getModeSpectator();
        };
        if (target == null) {
            if (sender instanceof Player player) {
                changeSelf(player, resolved, gamemodeName);
            } else {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerOnly());
            }
        } else {
            Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer == null) {
                MessageService.send(sender, MessageConfig.getInstance().getPlayerNotFound(), "{player}", target);
            } else {
                changeOther(sender, targetPlayer, resolved, gamemodeName);
            }
        }
    }

    private void changeSelf(Player player, GameMode gamemode, String gamemodeName) {
        player.setGameMode(gamemode);
        MessageService.send(player, MessageConfig.getInstance().getGamemodeChanged(), "{mode}", gamemodeName);
    }

    private void changeOther(CommandSender sender, Player player, GameMode gamemode, String gamemodeName) {
        player.setGameMode(gamemode);
        MessageService.send(sender, MessageConfig.getInstance().getGamemodeOthers(), "{player}", player.getName(), "{mode}", gamemodeName);
    }
}
