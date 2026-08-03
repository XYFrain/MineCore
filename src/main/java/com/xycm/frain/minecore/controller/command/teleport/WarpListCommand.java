package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.WarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * /minecore warplist（或 /warplist）—— 显示公共传送点列表。
 */
public class WarpListCommand implements SubCommand {

    private final WarpService warpService;

    public WarpListCommand(WarpService warpService) {
        this.warpService = warpService;
    }

    @Override
    public String getName() {
        return "warplist";
    }

    @Override
    public String getDescription() {
        return "显示公共传送点列表";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.warp";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Result result = warpService.listWarps();
        if ("WarpList".equals(result.getMessageKey())) {
            @SuppressWarnings("unchecked")
            Set<String> names = result.getData(Set.class);
            MessageManager.sendWarpList(sender, names);
        } else {
            MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        }
        return true;
    }
}
