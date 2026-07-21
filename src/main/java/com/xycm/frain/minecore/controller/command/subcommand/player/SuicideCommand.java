package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore suicide —— 自杀。
 */
public class SuicideCommand implements SubCommand {

    @Override
    public String getName() {
        return "suicide";
    }

    @Override
    public String getDescription() {
        return "自杀";
    }

    @Override
    public String getPermission() {
        return "minecore.player.suicide";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.suicide")) return true;

        Player player = (Player) sender;
        MessageManager.sendSuicideSuccess(sender);
        player.setHealth(0);
        return true;
    }
}
