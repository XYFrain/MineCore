package com.xycm.frain.minecore.service.teleport;

import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.model.PlayerData;
import com.xycm.frain.minecore.model.Result;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Home 业务逻辑。
 */
public class HomeService {

    private final DataManager dataManager;

    public HomeService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public Result setHome(Player player, String name) {
        PlayerData data = dataManager.loadPlayerData(player.getUniqueId());
        data.setHome(name, player.getLocation());
        dataManager.savePlayerData(data);
        return Result.ok(null, "HomeSet", "{home}", name);
    }

    public Result delHome(Player player, String name) {
        PlayerData data = dataManager.loadPlayerData(player.getUniqueId());
        if (!data.delHome(name)) {
            return Result.fail("HomeNotFound", "{home}", name);
        }
        dataManager.savePlayerData(data);
        return Result.ok(null, "HomeDeleted", "{home}", name);
    }

    /**
     * 前往 home。
     * name 为 null 时：单家直接回，多家返回 home 列表让 controller 显示。
     */
    public Result goHome(Player player, String name) {
        PlayerData data = dataManager.loadPlayerData(player.getUniqueId());

        if (name == null) {
            // 无参数
            if (!data.hasAnyHome()) {
                return Result.fail("NoHomes");
            }
            String single = data.getSingleHomeName();
            if (single != null) {
                // 单家，直接回
                return doTeleport(player, data.getHome(single), single);
            } else {
                // 多家，返回列表
                return Result.ok(data.getHomeNames(), "HomeList");
            }
        } else {
            // 有参数
            if (!data.hasHome(name)) {
                return Result.fail("HomeNotFound", "{home}", name);
            }
            return doTeleport(player, data.getHome(name), name);
        }
    }

    private Result doTeleport(Player player, Location location, String homeName) {
        player.teleport(location);
        return Result.ok(null, "TeleportedToHome", "{home}", homeName);
    }
}
