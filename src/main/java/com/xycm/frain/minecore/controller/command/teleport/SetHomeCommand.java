package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.HomeService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /minecore sethome &lt;名字&gt;（或 /sethome）—— 设置家。
 */
public class SetHomeCommand implements SubCommand {

    private final HomeService homeService;

    public SetHomeCommand(HomeService homeService) {
        this.homeService = homeService;
    }

    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getDescription() {
        return "设置家";
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
            MessageManager.sendByKey(sender, "Usage", "{usage}", "/minecore sethome <名字>");
            return true;
        }
        Result result = homeService.setHome(player, args[0]);
        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }
}
