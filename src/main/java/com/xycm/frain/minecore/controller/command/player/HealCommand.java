package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.HealService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore heal [玩家]（或 /heal）—— 恢复生命值。
 */
public class HealCommand implements SubCommand {

    private final HealService healService;

    public HealCommand(HealService healService) {
        this.healService = healService;
    }

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
        Result result;
        if (args.length > 0) {
            result = healService.healOther(args[0]);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = healService.healSelf(player);
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
