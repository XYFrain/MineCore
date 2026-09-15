package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.manager.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TeleportWarpService {

    public void teleport(Player player, String name) {
        if (name == null) {
            list(player);
            return;
        }
        if (!DataManager.hasWarp(name)) {
            MessageService.send(player, MessageConfig.getInstance().getWarpNotFound(), "{warp}", name);
            return;
        }
        player.teleport(DataManager.getWarp(name));
        MessageService.send(player, MessageConfig.getInstance().getTeleportedToWarp(), "{warp}", name);
    }

    public void create(Player player, String name) {
        DataManager.setWarp(name, player.getLocation());
        MessageService.send(player, MessageConfig.getInstance().getWarpSet(), "{warp}", name);
    }

    public void delete(CommandSender sender, String name) {
        if (!DataManager.hasWarp(name)) {
            MessageService.send(sender, MessageConfig.getInstance().getWarpNotFound(), "{warp}", name);
            return;
        }
        DataManager.delWarp(name);
        MessageService.send(sender, MessageConfig.getInstance().getWarpDeleted(), "{warp}", name);
    }

    public void list(CommandSender sender) {
        List<String> names = DataManager.getAllWarps();
        if (names.isEmpty()) {
            MessageService.send(sender, MessageConfig.getInstance().getNoWarps());
            return;
        }
        MessageService.send(sender, "<green>公共传送点：");
        for (String warpName : names) {
            MessageService.send(sender, "<gray>- <white>" + warpName);
        }
    }
}