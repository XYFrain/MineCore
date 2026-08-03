package com.xycm.frain.minecore.controller.command;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.controller.command.admin.KickCommand;
import com.xycm.frain.minecore.controller.command.admin.ReloadCommand;
import com.xycm.frain.minecore.controller.command.player.FeedCommand;
import com.xycm.frain.minecore.controller.command.player.FlyCommand;
import com.xycm.frain.minecore.controller.command.player.GamemodeCommand;
import com.xycm.frain.minecore.controller.command.player.GodCommand;
import com.xycm.frain.minecore.controller.command.player.HealCommand;
import com.xycm.frain.minecore.controller.command.player.HelpCommand;
import com.xycm.frain.minecore.controller.command.player.SuicideCommand;
import com.xycm.frain.minecore.controller.command.player.VanishCommand;
import com.xycm.frain.minecore.controller.command.teleport.BackCommand;
import com.xycm.frain.minecore.controller.command.teleport.DelHomeCommand;
import com.xycm.frain.minecore.controller.command.teleport.DelWarpCommand;
import com.xycm.frain.minecore.controller.command.teleport.HomeCommand;
import com.xycm.frain.minecore.controller.command.teleport.SetHomeCommand;
import com.xycm.frain.minecore.controller.command.teleport.SetWarpCommand;
import com.xycm.frain.minecore.controller.command.teleport.SpawnCommand;
import com.xycm.frain.minecore.controller.command.teleport.TpCommand;
import com.xycm.frain.minecore.controller.command.teleport.TpHereCommand;
import com.xycm.frain.minecore.controller.command.teleport.WarpCommand;
import com.xycm.frain.minecore.controller.command.teleport.WarpListCommand;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.service.admin.KickService;
import com.xycm.frain.minecore.service.player.FeedService;
import com.xycm.frain.minecore.service.player.FlyService;
import com.xycm.frain.minecore.service.player.GamemodeService;
import com.xycm.frain.minecore.service.player.GodService;
import com.xycm.frain.minecore.service.player.HealService;
import com.xycm.frain.minecore.service.player.SuicideService;
import com.xycm.frain.minecore.service.player.VanishService;
import com.xycm.frain.minecore.service.teleport.BackService;
import com.xycm.frain.minecore.service.teleport.HomeService;
import com.xycm.frain.minecore.service.teleport.SpawnService;
import com.xycm.frain.minecore.service.teleport.TpHereService;
import com.xycm.frain.minecore.service.teleport.TpService;
import com.xycm.frain.minecore.service.teleport.WarpService;
import com.xycm.frain.minecore.util.CommandUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 命令注册入口：创建所有子命令，注册 /minecore 主命令和独立命令两条路径。
 * <p>
 * 无状态 service 在这里直接 new；有状态的 service（BackService）由 MineCore 注入。
 */
public final class CommandManager {

    private CommandManager() {}

    public static void register(BackService backService, DataManager dataManager) {
        MineCore plugin = MineCore.getInstance();

        // 有状态 service
        HomeService homeService = new HomeService(dataManager);
        WarpService warpService = new WarpService(dataManager);

        Map<String, SubCommand> subCommands = new LinkedHashMap<>();
        // teleport
        add(subCommands, new TpCommand(new TpService()));
        add(subCommands, new TpHereCommand(new TpHereService()));
        add(subCommands, new HomeCommand(homeService, dataManager));
        add(subCommands, new SetHomeCommand(homeService));
        add(subCommands, new DelHomeCommand(homeService, dataManager));
        add(subCommands, new WarpCommand(warpService, dataManager));
        add(subCommands, new SetWarpCommand(warpService));
        add(subCommands, new DelWarpCommand(warpService, dataManager));
        add(subCommands, new WarpListCommand(warpService));
        add(subCommands, new BackCommand(backService));
        add(subCommands, new SpawnCommand(new SpawnService()));
        // player
        add(subCommands, new FlyCommand(new FlyService()));
        add(subCommands, new GodCommand(new GodService()));
        add(subCommands, new HealCommand(new HealService()));
        add(subCommands, new FeedCommand(new FeedService()));
        add(subCommands, new SuicideCommand(new SuicideService()));
        add(subCommands, new GamemodeCommand(new GamemodeService()));
        add(subCommands, new VanishCommand(new VanishService()));
        // admin
        add(subCommands, new KickCommand(new KickService()));
        add(subCommands, new ReloadCommand());
        // help 需要拿到完整子命令映射，放在最后注册
        add(subCommands, new HelpCommand(subCommands));

        MainCommand main = new MainCommand(subCommands);
        plugin.getCommand("MineCore").setExecutor(main);
        plugin.getCommand("MineCore").setTabCompleter(main);

        // 独立命令（如 /fly），help 由 isStandalone() 排除
        CommandUtil.register(new ArrayList<>(subCommands.values()));
    }

    private static void add(Map<String, SubCommand> subCommands, SubCommand sc) {
        subCommands.put(sc.getName(), sc);
    }
}
