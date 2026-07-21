package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore heal [玩家] —— 恢复生命值。
 */
public class HealCommand implements SubCommand {

    @Override
    public String getName() {
        return "heal";
    }

    @Override
    public String getDescription() {
        return "恢复生命值";
    }

    @Override
    public String getUsage() {
        return "[玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.player.heal";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            return healOthers(sender, args[0]);
        }
        return healSelf(sender);
    }

    private boolean healSelf(CommandSender sender) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.heal")) return true;

        Player player = (Player) sender;
        double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(max);
        MessageManager.sendHealSuccess(sender);
        return true;
    }

    private boolean healOthers(CommandSender sender, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.player.heal")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            MessageManager.sendPlayerNotFound(sender, targetName);
            return true;
        }
        double max = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        target.setHealth(max);
        MessageManager.sendHealOthers(sender, target.getName());
        return true;
    }
}
