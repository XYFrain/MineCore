package com.xycm.frain.minecore;

import com.xycm.frain.minecore.config.ConfigManager;
import com.xycm.frain.minecore.controller.command.CommandManager;
import com.xycm.frain.minecore.controller.listener.ListenerManager;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.service.teleport.BackService;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MineCore 插件入口。
 * <p>
 * 启动时按依赖顺序装配各层：先加载配置，再创建有状态的 service 与 data 层，
 * 最后把它们注入给命令系统与监听器。
 */
public final class MineCore extends JavaPlugin {

    private static MineCore instance;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.init();
        dataManager = new DataManager();

        BackService backService = new BackService();
        CommandManager.register(backService, dataManager);
        ListenerManager.register(backService, dataManager);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /** 获取插件实例 */
    public static MineCore getInstance() {
        return instance;
    }

    /** 获取数据层服务 */
    public DataManager getDataManager() {
        return dataManager;
    }
}
