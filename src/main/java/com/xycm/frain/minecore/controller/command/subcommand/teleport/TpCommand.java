package com.xycm.frain.minecore.controller.command.subcommand.teleport;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore tp <目标> — 传送到玩家
 * /minecore tp <玩家> <目标> — 将玩家传送到另一个玩家
 * /minecore tp <x> <y> <z> — 传送到坐标
 * /minecore tp <玩家> <x> <y> <z> — 将玩家传送到坐标
 */
public class TpCommand implements SubCommand {

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return "传送";
    }

    @Override
    public String getUsage() {
        return "[玩家] <目标|玩家|x y z>";
    }

    @Override
    public String getPermission() {
        return "minecore.player.tp";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return switch (args.length) {
            case 1 -> tpSelfToPlayer(sender, args[0]);
            case 2 -> tpPlayerToPlayer(sender, args[0], args[1]);
            case 3 -> tpSelfToCoords(sender, args[0], args[1], args[2]);
            case 4 -> tpPlayerToCoords(sender, args[0], args[1], args[2], args[3]);
            default -> { MessageManager.sendInvalidArgument(sender); yield true; }
        };
    }

    private boolean tpSelfToPlayer(CommandSender sender, String targetName) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.tp")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) { MessageManager.sendPlayerNotFound(sender, targetName); return true; }

        ((Player) sender).teleport(target);
        return true;
    }

    private boolean tpPlayerToPlayer(CommandSender sender, String playerName, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.player.tp")) return true;

        Player player = Bukkit.getPlayer(playerName);
        if (player == null) { MessageManager.sendPlayerNotFound(sender, playerName); return true; }

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) { MessageManager.sendPlayerNotFound(sender, targetName); return true; }

        player.teleport(target);
        return true;
    }

    private boolean tpSelfToCoords(CommandSender sender, String x, String y, String z) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.tp")) return true;

        try {
            Location loc = new Location(((Player) sender).getWorld(),
                    Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
            ((Player) sender).teleport(loc);
        } catch (NumberFormatException e) {
            MessageManager.sendInvalidArgument(sender);
        }
        return true;
    }

    private boolean tpPlayerToCoords(CommandSender sender, String playerName, String x, String y, String z) {
        if (!PermissionUtil.check(sender, "minecore.player.tp")) return true;

        Player player = Bukkit.getPlayer(playerName);
        if (player == null) { MessageManager.sendPlayerNotFound(sender, playerName); return true; }

        try {
            Location loc = new Location(player.getWorld(),
                    Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
            player.teleport(loc);
        } catch (NumberFormatException e) {
            MessageManager.sendInvalidArgument(sender);
        }
        return true;
    }
}
