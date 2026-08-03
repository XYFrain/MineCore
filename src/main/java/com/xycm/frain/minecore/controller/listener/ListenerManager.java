package com.xycm.frain.minecore.controller.listener;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.service.teleport.BackService;
import org.bukkit.Bukkit;

/**
 * 监听器管理器 —— 注册所有事件监听器。
 */
public final class ListenerManager {

    private ListenerManager() {}

    public static void register(BackService backService, DataManager dataManager) {
        Bukkit.getPluginManager().registerEvents(
                new PlayerListener(backService, dataManager), MineCore.getInstance());
    }
}
