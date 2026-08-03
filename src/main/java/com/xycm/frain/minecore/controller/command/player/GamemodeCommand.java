package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.player.GamemodeService;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * /minecore gamemode <模式> [玩家]（或 /gamemode）—— 切换游戏模式。
 * <p>
 * 模式支持名称（survival / creative / adventure / spectator）和数字（0 / 1 / 2 / 3）。
 */
public class GamemodeCommand implements SubCommand {

    private static final List<String> MODE_NAMES = List.of("survival", "creative", "adventure", "spectator");

    private final GamemodeService gamemodeService;

    public GamemodeCommand(GamemodeService gamemodeService) {
        this.gamemodeService = gamemodeService;
    }

    @Override
    public String getName() {
        return "gamemode";
    }

    @Override
    public String getDescription() {
        return "切换游戏模式";
    }

    @Override
    public String getUsage() {
        return "<模式> [玩家]";
    }

    @Override
    public String getPermission() {
        return "minecore.player.gamemode";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            MessageManager.sendInvalidArgument(sender);
            return true;
        }

        GameMode mode = resolve(args[0]);
        if (mode == null) {
            MessageManager.sendInvalidArgument(sender);
            return true;
        }

        Result result;
        if (args.length > 1) {
            result = gamemodeService.applyOther(args[1], mode);
        } else {
            if (!(sender instanceof Player player)) {
                MessageManager.sendPlayerOnly(sender);
                return true;
            }
            result = gamemodeService.applySelf(player, mode);
        }

        MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        // args[0] 是子命令名，args[1] 是第一个真实参数
        if (args.length == 2) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            return MODE_NAMES.stream()
                    .filter(name -> name.startsWith(prefix))
                    .collect(Collectors.toList());
        }
        if (args.length == 3) {
            String prefix = args[2].toLowerCase(Locale.ROOT);
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private GameMode resolve(String input) {
        return switch (input.toLowerCase(Locale.ROOT)) {
            case "0", "survival" -> GameMode.SURVIVAL;
            case "1", "creative" -> GameMode.CREATIVE;
            case "2", "adventure" -> GameMode.ADVENTURE;
            case "3", "spectator" -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}
