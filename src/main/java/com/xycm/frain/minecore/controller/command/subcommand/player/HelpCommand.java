package com.xycm.frain.minecore.controller.command.subcommand.player;

import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.util.ColorUtil;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * /minecore help —— 显示所有子命令的帮助信息。
 */
public class HelpCommand implements SubCommand {

    private final Map<String, SubCommand> subCommands;

    public HelpCommand(Map<String, SubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "显示帮助信息";
    }

    @Override
    public String getPermission() {
        return "minecore.player.help";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!PermissionUtil.check(sender, "minecore.player.help")) return true;

        sender.sendMessage(ColorUtil.colorize("&8&m---&r &bMineCore &8&m---"));

        for (SubCommand sub : subCommands.values()) {
            if (!sender.hasPermission(sub.getPermission())) {
                continue;
            }

            String usage = sub.getUsage().isEmpty() ? "" : " " + sub.getUsage();
            String desc = sub.getDescription() != null ? sub.getDescription() : "";
            sender.sendMessage(ColorUtil.colorize(
                    " &7/minecore " + sub.getName() + usage + " &f- &r&f" + desc));
        }
        return true;
    }
}
