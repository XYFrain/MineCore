package com.xycm.frain.minecore.controller.command.subcommand.teleport;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.controller.listener.PlayerListener;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore back —— 返回上一次位置。
 */
public class BackCommand implements SubCommand {

    @Override
    public String getName() { return "back"; }

    @Override
    public String getDescription() { return "返回上一位置"; }

    @Override
    public String getPermission() { return "minecore.teleport.back"; }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.teleport.back")) return true;

        Player player = (Player) sender;
        Location loc = PlayerListener.popLastLocation(player);
        if (loc == null) {
            MessageManager.sendInvalidArgument(sender);
            return true;
        }
        player.teleport(loc);
        return true;
    }
}
