package com.xycm.frain.minecore.util;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 通过反射将子命令动态注册为独立 Bukkit 命令，无需修改 plugin.yml。
 */
public final class CommandUtil {

    private CommandUtil() {}

    /** 遍历所有子命令并注册。 */
    public static void register(Map<String, SubCommand> subCommands) {
        try {
            CommandMap map = getCommandMap();
            for (SubCommand sc : subCommands.values()) {
                map.register("minecore", new Wrapper(sc));
            }
        } catch (Exception e) {
            MineCore.getInstance().getLogger().warning("动态注册命令失败: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static CommandMap getCommandMap() throws Exception {
        Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        return (CommandMap) field.get(Bukkit.getServer());
    }

    private static class Wrapper extends Command {
        private final SubCommand sc;

        Wrapper(SubCommand sc) {
            super(sc.getName(), sc.getDescription(), "/" + sc.getName() + " " + sc.getUsage(), List.of());
            this.sc = sc;
            setPermission(sc.getPermission());
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return sc.execute(sender, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
            if (sc.supportsPlayerTarget() && args.length == 1) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(p -> p.getName())
                        .filter(n -> args[0].isEmpty() || n.toLowerCase().startsWith(args[0].toLowerCase()))
                        .toList();
            }
            return super.tabComplete(sender, alias, args);
        }
    }
}
