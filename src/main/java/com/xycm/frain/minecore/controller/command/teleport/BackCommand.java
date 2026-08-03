package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.BackService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore back（或 /back）—— 返回上一次位置。
 */
public class BackCommand implements SubCommand {

    private final BackService backService;

    public BackCommand(BackService backService) {
        this.backService = backService;
    }

    @Override
    public String getName() {
        return "back";
    }

    @Override
    public String getDescription() {
        return "返回上一位置";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.back";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageManager.sendPlayerOnly(sender);
            return true;
        }
        Result result = backService.back(player);
        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
