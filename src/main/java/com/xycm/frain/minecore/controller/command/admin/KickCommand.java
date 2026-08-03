package com.xycm.frain.minecore.controller.command.admin;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.admin.KickService;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * /minecore kick &lt;玩家&gt; [理由] —— 踢出玩家。
 * <p>
 * 理由显示给被踢者；每次执行记录控制台日志；不全服广播。
 */
public class KickCommand implements SubCommand {

    private final KickService kickService;

    public KickCommand(KickService kickService) {
        this.kickService = kickService;
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "踢出玩家";
    }

    @Override
    public String getUsage() {
        return "<玩家> [理由]";
    }

    @Override
    public String getPermission() {
        return "minecore.admin.kick";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            MessageManager.sendByKey(sender, "Usage", "{usage}", "/minecore kick <玩家> [理由]");
            return true;
        }

        String targetName = args[0];
        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) reasonBuilder.append(" ");
            reasonBuilder.append(args[i]);
        }
        String reason = reasonBuilder.toString();

        Result result = kickService.kick(sender.getName(), targetName, reason);
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
