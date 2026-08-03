package com.xycm.frain.minecore.controller.command;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.util.CommandUtil;

/**
 * 命令管理器 —— 负责注册主命令和动态子命令。
 */
public final class CommandManager {

    private CommandManager() {}

    /** 注册主命令和所有子命令到 Bukkit，供 {@link MineCore#onEnable()} 调用。 */
    public static void register() {
        MineCore plugin = MineCore.getInstance();
        MainCommand main = new MainCommand();

        plugin.getCommand("MineCore").setExecutor(main);
        plugin.getCommand("MineCore").setTabCompleter(main);

        CommandUtil.register(main.getSubCommands());
    }
}
