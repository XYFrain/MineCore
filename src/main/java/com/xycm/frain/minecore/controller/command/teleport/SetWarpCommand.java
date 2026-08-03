package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.WarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore setwarp &lt;名字&gt;（或 /setwarp）—— 创建公共传送点。
 */
public class SetWarpCommand implements SubCommand {

    private final WarpService warpService;

    public SetWarpCommand(WarpService warpService) {
        this.warpService = warpService;
    }

    @Override
    public String getName() {
        return "setwarp";
    }

    @Override
    public String getDescription() {
        return "创建公共传送点";
    }

    @Override
    public String getUsage() {
        return "<名字>";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.warp";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageManager.sendPlayerOnly(sender);
            return true;
        }
        if (args.length < 1) {
            MessageManager.sendByKey(sender, "Usage", "{usage}", "/minecore setwarp <名字>");
            return true;
        }
        Result result = warpService.setWarp(args[0], player.getLocation());
        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
