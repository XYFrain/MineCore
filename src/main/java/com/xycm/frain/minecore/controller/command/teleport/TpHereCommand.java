package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.TpHereService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * /minecore tphere &lt;玩家&gt;（或 /tphere）—— 把玩家传送到自己身边。
 */
public class TpHereCommand implements SubCommand {

    private final TpHereService tpHereService;

    public TpHereCommand(TpHereService tpHereService) {
        this.tpHereService = tpHereService;
    }

    @Override
    public String getName() {
        return "tphere";
    }

    @Override
    public String getDescription() {
        return "传送玩家到自己身边";
    }

    @Override
    public String getUsage() {
        return "<玩家>";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.tphere";
    }

    @Override
    public boolean supportsPlayerTarget() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player executor)) {
            MessageManager.sendPlayerOnly(sender);
            return true;
        }
        if (args.length < 1) {
            MessageManager.sendByKey(sender, "Usage", "{usage}", "/minecore tphere <玩家>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MessageManager.sendByKey(sender, "PlayerNotFound", "{player}", args[0]);
            return true;
        }
        Result result = tpHereService.tpHere(executor, target);
        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
