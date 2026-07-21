package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore vanish [玩家] —— 切换隐身模式。
 */
public class VanishCommand implements SubCommand {

    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public String getDescription() {
        return "切换隐身模式";
    }

    @Override
    public String getUsage() {
        return "[玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.player.vanish";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            return vanishOthers(sender, args[0]);
        }
        return vanishSelf(sender);
    }

    private boolean vanishSelf(CommandSender sender) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.vanish")) return true;

        Player player = (Player) sender;
        player.setInvisible(!player.isInvisible());
        MessageManager.sendVanishToggle(sender, player.isInvisible());
        return true;
    }

    private boolean vanishOthers(CommandSender sender, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.player.vanish")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            MessageManager.sendPlayerNotFound(sender, targetName);
            return true;
        }
        target.setInvisible(!target.isInvisible());
        MessageManager.sendVanishToggleOthers(sender, target.getName(), target.isInvisible());
        return true;
    }
}
