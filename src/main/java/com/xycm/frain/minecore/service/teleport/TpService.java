package com.xycm.frain.minecore.service.teleport;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * tp 传送业务：自身传送到玩家/坐标。
 * <p>
 * 只支持 tp 的两种形式：<玩家> 和 <x> <y> <z>。
 * 传玩家到玩家/坐标是 tphere 的职责。
 */
public class TpService {

    /** 把自己传送到指定玩家身边。 */
    public Result selfToPlayer(Player self, String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        self.teleport(target);
        return Result.ok();
    }

    /** 把自己传送到指定坐标。 */
    public Result selfToLocation(Player self, Location loc) {
        self.teleport(loc);
        return Result.ok();
    }
}
