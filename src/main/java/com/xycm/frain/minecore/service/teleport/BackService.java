package com.xycm.frain.minecore.service.teleport;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 位置回溯业务：记录玩家上一次位置，供 /minecore back 返回。
 * <p>
 * 有状态：持有 UUID → Location 的记忆 Map。
 * 玩家退出时由监听器调 {@link #onQuit} 清理，避免内存泄漏（修问题 #8）。
 */
public class BackService {

    private final Map<UUID, Location> lastLocation = new HashMap<>();

    /** 记录传送/死亡前的位置。 */
    public void recordLocation(Player player, Location loc) {
        lastLocation.put(player.getUniqueId(), loc);
    }

    /** 返回上一次位置并清除记录；没有记录时返回失败结果（修问题 #7）。 */
    public Result back(Player player) {
        Location loc = lastLocation.remove(player.getUniqueId());
        if (loc == null) {
            return Result.fail("BackNoLocation");
        }
        player.teleport(loc);
        return Result.ok();
    }

    /** 玩家退出时清理位置记忆。 */
    public void onQuit(Player player) {
        lastLocation.remove(player.getUniqueId());
    }
}
