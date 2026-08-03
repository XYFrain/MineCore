package com.xycm.frain.minecore.util;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.controller.command.MainCommand;
import com.xycm.frain.minecore.controller.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 通过反射把子命令注册为独立命令（如 /fly），与 /minecore fly 共用同一份逻辑。
 * <p>
 * 设计说明：
 * <ul>
 *   <li>不设置 Bukkit 原生 permission（否则无权限时显示英文默认提示），
 *       改由包装器内统一走 PermissionUtil，提示为插件中文消息（修问题 #9）。</li>
 *   <li>Tab 补全复用 MainCommand.completeArgs，与子命令路径一致（修问题 #1）。</li>
 *   <li>isStandalone() 为 false 的子命令（help）不注册，保留原版命令（修问题 #2）。</li>
 * </ul>
 */
public final class CommandUtil {

    private CommandUtil() {}

    public static void register(List<SubCommand> subCommands) {
        try {
            CommandMap map = getCommandMap();
            for (SubCommand sc : subCommands) {
                if (!sc.isStandalone()) continue;
                map.register("minecore", new StandaloneCommand(sc));
            }
        } catch (Exception e) {
            MineCore.getInstance().getLogger().warning("动态注册独立命令失败: " + e.getMessage());
        }
    }

    private static CommandMap getCommandMap() throws Exception {
        Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        return (CommandMap) field.get(Bukkit.getServer());
    }

    /** 把单个 SubCommand 包装成独立 Bukkit 命令。 */
    private static class StandaloneCommand extends Command {
        private final SubCommand sc;

        StandaloneCommand(SubCommand sc) {
            super(sc.getName(), sc.getDescription(), "/" + sc.getName() + " " + sc.getUsage(), List.of());
            this.sc = sc;
            // 注意：不 setPermission，权限检查在 execute 里用中文提示统一处理
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (!PermissionUtil.check(sender, sc.getPermission())) return true;
            return sc.execute(sender, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
            // 独立命令的 args 不含子命令名，补上占位后复用 MainCommand 的补全逻辑
            String[] full = new String[args.length + 1];
            full[0] = sc.getName();
            System.arraycopy(args, 0, full, 1, args.length);
            return MainCommand.completeArgs(sender, sc, full);
        }
    }
}
