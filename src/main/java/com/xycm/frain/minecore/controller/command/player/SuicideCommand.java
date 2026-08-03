package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.SuicideService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore suicide（或 /suicide）—— 自杀。
 */
public class SuicideCommand implements SubCommand {

    private final SuicideService suicideService;

    public SuicideCommand(SuicideService suicideService) {
        this.suicideService = suicideService;
    }

    @Override
    public String getName() {
        return "suicide";
    }

    @Override
    public String getDescription() {
        return "自杀";
    }

    @Override
    public String getPermission() {
        return "minecore.player.suicide";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageManager.sendPlayerOnly(sender);
            return true;
        }
        Result result = suicideService.suicide(player);
        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
