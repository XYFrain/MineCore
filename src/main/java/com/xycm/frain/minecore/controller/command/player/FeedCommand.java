package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.FeedService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore feed [玩家]（或 /feed）—— 恢复饱食度。
 */
public class FeedCommand implements SubCommand {

    private final FeedService feedService;

    public FeedCommand(FeedService feedService) {
        this.feedService = feedService;
    }

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
        Result result;
        if (args.length > 0) {
            result = feedService.feedOther(args[0]);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = feedService.feedSelf(player);
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
