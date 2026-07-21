package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore god [玩家] —— 切换无敌模式。
 */
public class GodCommand implements SubCommand {

    @Override
    public String getName() {
        return "god";
    }

    @Override
    public String getDescription() {
        return "切换无敌模式";
    }

    @Override
    public String getUsage() {
        return "[玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.player.god";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            return godOthers(sender, args[0]);
        }
        return godSelf(sender);
    }

    private boolean godSelf(CommandSender sender) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.god")) return true;

        Player player = (Player) sender;
        player.setInvulnerable(!player.isInvulnerable());
        MessageManager.sendGodToggle(sender, player.isInvulnerable());
        return true;
    }

    private boolean godOthers(CommandSender sender, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.player.god")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            MessageManager.sendPlayerNotFound(sender, targetName);
            return true;
        }
        target.setInvulnerable(!target.isInvulnerable());
        MessageManager.sendGodToggleOthers(sender, target.getName(), target.isInvulnerable());
        return true;
    }
}
