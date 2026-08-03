package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.VanishService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore vanish [玩家]（或 /vanish）—— 切换隐身模式。
 */
public class VanishCommand implements SubCommand {

    private final VanishService vanishService;

    public VanishCommand(VanishService vanishService) {
        this.vanishService = vanishService;
    }

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
        Result result;
        if (args.length > 0) {
            result = vanishService.toggleOther(args[0]);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = vanishService.toggleSelf(player);
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
