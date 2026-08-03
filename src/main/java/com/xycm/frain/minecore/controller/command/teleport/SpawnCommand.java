package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.SpawnService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore spawn [玩家]（或 /spawn）—— 传送到配置的出生点。
 */
public class SpawnCommand implements SubCommand {

    private final SpawnService spawnService;

    public SpawnCommand(SpawnService spawnService) {
        this.spawnService = spawnService;
    }

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "传送到出生点";
    }

    @Override
    public String getUsage() {
        return "[玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.spawn";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Result result;
        if (args.length > 0) {
            result = spawnService.other(args[0]);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = spawnService.self(player);
        }

        if (result.getMessageKey() != null) {
            MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        }
        return true;
    }
}
