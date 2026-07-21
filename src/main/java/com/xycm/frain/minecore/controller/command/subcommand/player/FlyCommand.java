package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore fly [玩家] —— 切换飞行模式。
 */
public class FlyCommand implements SubCommand {

    @Override
    public String getName() {
        return "fly";
    }

    @Override
    public String getDescription() {
        return "切换飞行模式";
    }

    @Override
    public String getUsage() {
        return "[玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.player.fly";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            return flyOthers(sender, args[0]);
        }
        return flySelf(sender);
    }

    private boolean flySelf(CommandSender sender) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.fly")) return true;

        Player player = (Player) sender;
        boolean enabled = !player.getAllowFlight();
        player.setAllowFlight(enabled);
        MessageManager.sendFlyToggle(sender, enabled);
        return true;
    }

    private boolean flyOthers(CommandSender sender, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.player.fly")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            MessageManager.sendPlayerNotFound(sender, targetName);
            return true;
        }
        boolean enabled = !target.getAllowFlight();
        target.setAllowFlight(enabled);
        MessageManager.sendFlyToggleOthers(sender, target.getName(), enabled);
        return true;
    }
}
