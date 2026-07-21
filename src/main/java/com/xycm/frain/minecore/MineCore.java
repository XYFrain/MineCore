package com.xycm.frain.minecore;

import com.xycm.frain.minecore.config.ConfigManager;
import com.xycm.frain.minecore.controller.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineCore extends JavaPlugin {

    private static MineCore instance;

    @Override
    public void onEnable() {
        instance = this;

        // 配置文件初始化
        ConfigManager.init();

        // 注册命令
        CommandManager.register();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /** 获取插件实例 */
    public static MineCore getInstance() {
        return instance;
    }
}
