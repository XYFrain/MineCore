package com.xycm.frain.minecore.manager;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.listener.BackListener;
import com.xycm.frain.minecore.listener.PlayerDataListener;
import com.xycm.frain.minecore.listener.SpawnListener;
import com.xycm.frain.minecore.listener.TeleportListener;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@UtilityClass
public class ListenerManager {
    public void register() {
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(), MineCore.getInstance());
        Bukkit.getPluginManager().registerEvents(new SpawnListener(), MineCore.getInstance());
        Bukkit.getPluginManager().registerEvents(new BackListener(), MineCore.getInstance());
        Bukkit.getPluginManager().registerEvents(new TeleportListener(), MineCore.getInstance());
    }
}
