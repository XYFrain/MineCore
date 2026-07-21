package com.xycm.frain.minecore.controller.listener;

import com.xycm.frain.minecore.MineCore;
import org.bukkit.Bukkit;

/**
 * 监听器管理器 —— 负责注册所有事件监听器。
 */
public final class ListenerManager {

    private ListenerManager() {}

    /** 注册所有监听器到 Bukkit，供 {@link MineCore#onEnable()} 调用。 */
    public static void register() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), MineCore.getInstance());
    }
}
