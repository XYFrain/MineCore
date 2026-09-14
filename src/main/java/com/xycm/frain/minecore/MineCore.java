package com.xycm.frain.minecore;

import com.xycm.frain.minecore.manager.CommandManager;
import com.xycm.frain.minecore.manager.ConfigManager;
import com.xycm.frain.minecore.manager.ListenerManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;


public final class MineCore extends JavaPlugin {

    @Getter
    private static MineCore instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        ConfigManager.init();
        CommandManager.register();
        ListenerManager.register();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
