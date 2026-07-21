package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * /minecore gamemode <模式> [玩家] —— 切换游戏模式。
 * <p>
 * 模式支持名称（survival / creative / adventure / spectator）
 * 和数字（0 / 1 / 2 / 3）。
 */
public class GamemodeCommand implements SubCommand {

    private static final List<String> MODE_NAMES = List.of("survival", "creative", "adventure", "spectator");
    private static final Map<GameMode, String> MODE_DISPLAY = Map.of(
            GameMode.SURVIVAL, "生存模式",
            GameMode.CREATIVE, "创造模式",
            GameMode.ADVENTURE, "冒险模式",
            GameMode.SPECTATOR, "旁观模式"
    );

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

        if (args.length > 1) {
            return applyToOthers(sender, args[1], mode);
        }
        return applyToSelf(sender, mode);
    }

    private boolean applyToSelf(CommandSender sender, GameMode mode) {
        if (!PermissionUtil.playerOnly(sender)) return true;
        if (!PermissionUtil.check(sender, "minecore.player.gamemode")) return true;

        Player player = (Player) sender;
        player.setGameMode(mode);
        MessageManager.sendGamemodeChanged(sender, MODE_DISPLAY.get(mode));
        return true;
    }

    private boolean applyToOthers(CommandSender sender, String targetName, GameMode mode) {
        if (!PermissionUtil.check(sender, "minecore.player.gamemode")) return true;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            MessageManager.sendPlayerNotFound(sender, targetName);
            return true;
        }
        target.setGameMode(mode);
        MessageManager.sendGamemodeOthers(sender, target.getName(), MODE_DISPLAY.get(mode));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String partial = args[1].toLowerCase();
            return MODE_NAMES.stream()
                    .filter(n -> n.startsWith(partial))
                    .collect(Collectors.toList());
        }
        if (args.length == 3) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private GameMode resolve(String input) {
        return switch (input.toLowerCase()) {
            case "0", "survival" -> GameMode.SURVIVAL;
            case "1", "creative" -> GameMode.CREATIVE;
            case "2", "adventure" -> GameMode.ADVENTURE;
            case "3", "spectator" -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}
