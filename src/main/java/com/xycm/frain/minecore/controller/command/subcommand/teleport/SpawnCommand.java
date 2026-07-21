package com.xycm.frain.minecore.controller.command.subcommand.teleport;

import com.xycm.frain.minecore.config.MainConfig;
import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore spawn [玩家] —— 传送到配置的出生点。
 */
public class SpawnCommand implements SubCommand {

    @Override
    public String getName() { return "spawn"; }

    @Override
    public String getDescription() { return "传送到出生点"; }

    @Override
    public String getUsage() { return "[玩家]"; }

    @Override
    public String getPermission() { return "minecore.teleport.spawn"; }

    @Override
    public boolean supportsPlayerTarget() { return true; }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) return spawnOthers(sender, args[0]);
        return spawnSelf(sender);
    }

    private boolean spawnSelf(CommandSender sender) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.teleport.spawn")) return true;
        ((Player) sender).teleport(getSpawn((Player) sender));
        return true;
    }

    private boolean spawnOthers(CommandSender sender, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.teleport.spawn")) return true;
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) { MessageManager.sendPlayerNotFound(sender, targetName); return true; }
        target.teleport(getSpawn(target));
        return true;
    }

    private Location getSpawn(Player player) {
        Location spawn = MainConfig.getSpawnLocation();
        return spawn != null ? spawn : player.getWorld().getSpawnLocation();
    }
}
