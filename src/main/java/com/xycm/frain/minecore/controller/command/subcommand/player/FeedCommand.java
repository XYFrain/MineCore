package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore feed [玩家] —— 恢复饱食度。
 */
public class FeedCommand implements SubCommand {

    @Override
    public String getName() {
        return "feed";
    }

    @Override
    public String getDescription() {
        return "恢复饱食度";
    }

    @Override
    public String getUsage() {
        return "[玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.player.feed";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            return feedOthers(sender, args[0]);
        }
        return feedSelf(sender);
    }

    private boolean feedSelf(CommandSender sender) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.feed")) return true;

        Player player = (Player) sender;
        player.setFoodLevel(20);
        player.setSaturation(10);
        MessageManager.sendFeedSuccess(sender);
        return true;
    }

    private boolean feedOthers(CommandSender sender, String targetName) {
        if (!PermissionUtil.check(sender, "minecore.player.feed")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            MessageManager.sendPlayerNotFound(sender, targetName);
            return true;
        }
        target.setFoodLevel(20);
        target.setSaturation(10);
        MessageManager.sendFeedOthers(sender, target.getName());
        return true;
    }
}
