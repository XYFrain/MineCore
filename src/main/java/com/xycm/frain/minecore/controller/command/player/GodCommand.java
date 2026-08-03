package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.GodService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore god [玩家]（或 /god）—— 切换无敌模式。
 */
public class GodCommand implements SubCommand {

    private final GodService godService;

    public GodCommand(GodService godService) {
        this.godService = godService;
    }

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
        Result result;
        if (args.length > 0) {
            result = godService.toggleOther(args[0]);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = godService.toggleSelf(player);
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
