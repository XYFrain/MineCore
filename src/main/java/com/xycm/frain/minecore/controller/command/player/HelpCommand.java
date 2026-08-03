package com.xycm.frain.minecore.controller.command.player;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.util.ColorUtil;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * /minecore help —— 显示有权限的子命令列表。
 * <p>
 * 按 PLAN 例外不建 service；不注册独立命令（保留原版 /help）。
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

    /** 不注册独立命令，保留原版 /help（修问题 #2）。 */
    @Override
    public boolean isStandalone() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(ColorUtil.colorize("&8&m---&r &bMineCore &8&m---"));

        for (SubCommand sub : subCommands.values()) {
            if (!sender.hasPermission(sub.getPermission())) {
                continue;
            }
            String usage = sub.getUsage().isEmpty() ? "" : " " + sub.getUsage();
            sender.sendMessage(ColorUtil.colorize(
                    " &7/minecore " + sub.getName() + usage + " &f- &r&f" + sub.getDescription()));
        }
        return true;
    }
}
