package com.xycm.frain.minecore.controller.command;

import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * /minecore 主命令分发器。
 * <p>
 * 职责：查子命令、统一查权限（修问题 #9 的提示一致性）、调 execute、做 Tab 补全。
 * 子命令自身不再检查权限，保证 /minecore &lt;子命令&gt; 与独立命令两条路径行为一致。
 */
public final class MainCommand implements TabExecutor {

    private final Map<String, SubCommand> subCommands;

    public MainCommand(Map<String, SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // 无参数时等价于 help
            SubCommand help = subCommands.get("help");
            if (help == null) return true;
            if (!PermissionUtil.check(sender, help.getPermission())) return true;
            return help.execute(sender, args);
        }

        SubCommand handler = subCommands.get(args[0].toLowerCase(Locale.ROOT));
        if (handler == null) {
            MessageManager.sendInvalidArgument(sender);
            return true;
        }
        if (!PermissionUtil.check(sender, handler.getPermission())) return true;

        String[] rest = Arrays.copyOfRange(args, 1, args.length);
        return handler.execute(sender, rest);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            // 补全子命令名：有权限的才列出，并按已输入前缀过滤（修问题 #6）
            String prefix = args[0].toLowerCase(Locale.ROOT);
            return subCommands.values().stream()
                    .filter(sc -> sender.hasPermission(sc.getPermission()))
                    .map(SubCommand::getName)
                    .filter(name -> name.startsWith(prefix))
                    .sorted()
                    .collect(Collectors.toList());
        }

        SubCommand handler = subCommands.get(args[0].toLowerCase(Locale.ROOT));
        if (handler == null) return List.of();
        return completeArgs(sender, handler, args);
    }

    /**
     * 补全子命令参数：先问子命令的自定义补全，为空则默认补在线玩家名。
     * 独立命令包装器也复用此方法，保证两条路径补全一致（修问题 #1）。
     *
     * @param args 完整参数数组（含子命令名本身，args[0] 为子命令名）
     */
    public static List<String> completeArgs(CommandSender sender, SubCommand handler, String[] args) {
        if (!sender.hasPermission(handler.getPermission())) return List.of();

        List<String> custom = handler.tabComplete(sender, args);
        if (!custom.isEmpty()) return custom;

        // 第一个参数位：支持玩家目标时补全在线玩家名，按前缀过滤
        if (args.length == 2 && handler.supportsPlayerTarget()) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public Map<String, SubCommand> getSubCommands() {
        return subCommands;
    }
}
