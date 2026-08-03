package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.WarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * /minecore warp [名字]（或 /warp）—— 传送到公共传送点。
 */
public class WarpCommand implements SubCommand {

    private final WarpService warpService;
    private final DataManager dataManager;

    public WarpCommand(WarpService warpService, DataManager dataManager) {
        this.warpService = warpService;
        this.dataManager = dataManager;
    }

    @Override
    public String getName() {
        return "warp";
    }

    @Override
    public String getDescription() {
        return "传送到公共传送点";
    }

    @Override
    public String getUsage() {
        return "[名字]";
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
        if (args.length == 0) {
            // 无参数：显示列表
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
        Result result = warpService.goWarp(player, args[0]);
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
