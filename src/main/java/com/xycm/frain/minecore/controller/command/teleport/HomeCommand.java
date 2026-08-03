package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.model.PlayerData;
import com.xycm.frain.minecore.service.teleport.HomeService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * /minecore home [名字]（或 /home）—— 回家。
 */
public class HomeCommand implements SubCommand {

    private final HomeService homeService;
    private final DataManager dataManager;

    public HomeCommand(HomeService homeService, DataManager dataManager) {
        this.homeService = homeService;
        this.dataManager = dataManager;
    }

    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getDescription() {
        return "回家";
    }

    @Override
    public String getUsage() {
        return "[名字]";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.home";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageManager.sendPlayerOnly(sender);
            return true;
        }
        String name = args.length > 0 ? args[0] : null;
        Result result = homeService.goHome(player, name);

        // 多家时需要显示列表（特殊处理）
        if ("HomeList".equals(result.getMessageKey())) {
            @SuppressWarnings("unchecked")
            Set<String> names = result.getData(Set.class);
            MessageManager.sendHomeList(sender, names);
            return true;
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2 && sender instanceof Player player) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            PlayerData data = dataManager.loadPlayerData(player.getUniqueId());
            List<String> result = new ArrayList<>();
            for (String name : data.getHomeNames()) {
                if (name.toLowerCase(Locale.ROOT).startsWith(prefix)) {
                    result.add(name);
                }
            }
            return result;
        }
        return List.of();
    }
}
