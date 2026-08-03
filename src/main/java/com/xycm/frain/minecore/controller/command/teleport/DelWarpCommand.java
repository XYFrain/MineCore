package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.WarpService;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * /minecore delwarp &lt;名字&gt;（或 /delwarp）—— 删除公共传送点。
 */
public class DelWarpCommand implements SubCommand {

    private final WarpService warpService;
    private final DataManager dataManager;

    public DelWarpCommand(WarpService warpService, DataManager dataManager) {
        this.warpService = warpService;
        this.dataManager = dataManager;
    }

    @Override
    public String getName() {
        return "delwarp";
    }

    @Override
    public String getDescription() {
        return "删除公共传送点";
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
        if (args.length < 1) {
            MessageManager.sendByKey(sender, "Usage", "{usage}", "/minecore delwarp <名字>");
            return true;
        }
        Result result = warpService.delWarp(args[0]);
        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            List<String> result = new ArrayList<>();
            for (String name : dataManager.getAllWarps().keySet()) {
                if (name.toLowerCase(Locale.ROOT).startsWith(prefix)) {
                    result.add(name);
                }
            }
            return result;
        }
        return List.of();
    }
}
