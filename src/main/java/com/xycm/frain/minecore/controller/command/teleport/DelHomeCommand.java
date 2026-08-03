package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.PlayerData;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.HomeService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * /minecore delhome &lt;名字&gt;（或 /delhome）—— 删除家。
 */
public class DelHomeCommand implements SubCommand {

    private final HomeService homeService;
    private final DataManager dataManager;

    public DelHomeCommand(HomeService homeService, DataManager dataManager) {
        this.homeService = homeService;
        this.dataManager = dataManager;
    }

    @Override
    public String getName() {
        return "delhome";
    }

    @Override
    public String getDescription() {
        return "删除家";
    }

    @Override
    public String getUsage() {
        return "<名字>";
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
        if (args.length < 1) {
            MessageManager.sendByKey(sender, "Usage", "{usage}", "/minecore delhome <名字>");
            return true;
        }
        Result result = homeService.delHome(player, args[0]);
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
