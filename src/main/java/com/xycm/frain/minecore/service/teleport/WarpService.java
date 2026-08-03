package com.xycm.frain.minecore.service.teleport;

import com.xycm.frain.minecore.data.DataManager;
import com.xycm.frain.minecore.model.Result;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Warp 业务逻辑。
 */
public class WarpService {

    private final DataManager dataManager;

    public WarpService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public Result setWarp(String name, Location location) {
        dataManager.setWarp(name, location);
        return Result.ok(null, "WarpSet", "{warp}", name);
    }

    public Result delWarp(String name) {
        if (!dataManager.hasWarp(name)) {
            return Result.fail("WarpNotFound", "{warp}", name);
        }
        dataManager.delWarp(name);
        return Result.ok(null, "WarpDeleted", "{warp}", name);
    }

    public Result goWarp(Player player, String name) {
        if (!dataManager.hasWarp(name)) {
            return Result.fail("WarpNotFound", "{warp}", name);
        }
        Location loc = dataManager.getWarp(name);
        player.teleport(loc);
        return Result.ok(null, "TeleportedToWarp", "{warp}", name);
    }

    public Result listWarps() {
        Set<String> names = dataManager.getAllWarps().keySet();
        if (names.isEmpty()) {
            return Result.fail("NoWarps");
        }
        return Result.ok(names, "WarpList");
    }
}
