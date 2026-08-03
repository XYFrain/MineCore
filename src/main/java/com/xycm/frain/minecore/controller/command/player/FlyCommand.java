package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.FlyService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore fly [玩家]（或 /fly）—— 切换飞行模式。
 * <p>
 * controller 只做三件事：解析参数、调 FlyService、把 Result 翻译成消息。
 * 权限检查由分发层统一处理，这里不再检查。
 */
public class FlyCommand implements SubCommand {

    private final FlyService flyService;

    public FlyCommand(FlyService flyService) {
        this.flyService = flyService;
    }

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
        Result result;
        if (args.length > 0) {
            result = flyService.toggleOther(args[0]);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = flyService.toggleSelf(player);
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
