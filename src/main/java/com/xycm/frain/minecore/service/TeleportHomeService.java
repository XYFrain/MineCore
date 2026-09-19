package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.manager.DataManager;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class TeleportHomeService {
    public void teleport(Player player, String name) {
        UUID uuid = player.getUniqueId();
        if (name == null) {
            // 无参数
            List<String> names = DataManager.getAllHomes(uuid);
            if (names.isEmpty()) {
                MessageService.send(player, MessageConfig.getInstance().getNoHomes());
                return;
            }
            if (names.size() == 1) {
                // 单家，直接回
                String single = names.get(0);
                player.teleport(DataManager.getHome(uuid, single));
                MessageService.send(player, MessageConfig.getInstance().getTeleportedToHome(), "{home}", single);
                return;
            }
            // 多家，显示列表
            for (String homeName : names) {
                MessageService.send(player, "<gray>- <white>" + homeName);
            }
            return;
        }
        // 有参数
        if (!DataManager.hasHome(uuid, name)) {
            MessageService.send(player, MessageConfig.getInstance().getHomeNotFound(), "{home}", name);
            return;
        }
        player.teleport(DataManager.getHome(uuid, name));
        MessageService.send(player, MessageConfig.getInstance().getTeleportedToHome(), "{home}", name);
    }

    public void create(Player player, String name) {
        DataManager.setHome(player.getUniqueId(), name, player.getLocation());
        MessageService.send(player, MessageConfig.getInstance().getHomeSet(), "{home}", name);
    }

    public void delete(Player player, String name) {
        if (!DataManager.hasHome(player.getUniqueId(), name)) {
            MessageService.send(player, MessageConfig.getInstance().getHomeNotFound(), "{home}", name);
            return;
        }
        DataManager.delHome(player.getUniqueId(), name);
        MessageService.send(player, MessageConfig.getInstance().getHomeDeleted(), "{home}", name);
    }
}