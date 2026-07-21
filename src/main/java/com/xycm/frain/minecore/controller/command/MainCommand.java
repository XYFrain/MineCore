package com.xycm.frain.minecore.controller.command;

import com.xycm.frain.minecore.controller.command.subcommand.player.FeedCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.GamemodeCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.GodCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.FlyCommand;
import com.xycm.frain.minecore.controller.command.subcommand.teleport.BackCommand;
import com.xycm.frain.minecore.controller.command.subcommand.teleport.SpawnCommand;
import com.xycm.frain.minecore.controller.command.subcommand.teleport.TpCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.VanishCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.SuicideCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.HealCommand;
import com.xycm.frain.minecore.controller.command.subcommand.player.HelpCommand;
import com.xycm.frain.minecore.controller.command.subcommand.admin.ReloadCommand;
import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * /minecore 主命令分发，通过 Map 查找子命令。
 */
public final class MainCommand implements TabExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public MainCommand() {
        subCommands.put("back", new BackCommand());
        subCommands.put("spawn", new SpawnCommand());
        subCommands.put("tp", new TpCommand());
        subCommands.put("gamemode", new GamemodeCommand());
        subCommands.put("god", new GodCommand());
        subCommands.put("fly", new FlyCommand());
        subCommands.put("vanish", new VanishCommand());
        subCommands.put("suicide", new SuicideCommand());
        subCommands.put("heal", new HealCommand());
        subCommands.put("feed", new FeedCommand());
        subCommands.put("reload", new ReloadCommand());
        subCommands.put("help", new HelpCommand(subCommands));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return subCommands.get("help").execute(sender, args);
        }

        String sub = args[0].toLowerCase();
        String[] rest = args.length > 1
                ? java.util.Arrays.copyOfRange(args, 1, args.length)
                : new String[0];
        SubCommand handler = subCommands.get(sub);

        if (handler != null) {
            handler.execute(sender, rest);
        } else {
            MessageManager.sendInvalidArgument(sender);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return subCommands.values().stream()
                    .filter(sc -> sender.hasPermission(sc.getPermission()))
                    .map(SubCommand::getName)
                    .collect(Collectors.toList());
        }
        if (args.length >= 2) {
            SubCommand sc = subCommands.get(args[0].toLowerCase());
            if (sc == null) return List.of();

            List<String> custom = sc.tabComplete(sender, args);
            if (!custom.isEmpty()) return custom;

            if (args.length == 2 && sc.supportsPlayerTarget()) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }
}
