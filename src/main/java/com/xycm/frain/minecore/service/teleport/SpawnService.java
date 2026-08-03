package com.xycm.frain.minecore.service.teleport;

import com.xycm.frain.minecore.config.MainConfig;
import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * 出生点业务：把玩家传送到配置的全局出生点。
 * <p>
 * 配置里没设出生点时回退到所在世界的默认出生点。
 */
public class SpawnService {

    /** 把自己传送到出生点。 */
    public Result self(Player self) {
        self.teleport(resolveSpawn(self));
        return Result.ok();
    }

    /** 把指定玩家传送到出生点。 */
    public Result other(String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        target.teleport(resolveSpawn(target));
        return Result.ok();
    }

    /** 配置出生点不存在时回退世界默认出生点。 */
    private Location resolveSpawn(Player player) {
        Location spawn = MainConfig.getSpawnLocation();
        return spawn != null ? spawn : player.getWorld().getSpawnLocation();
    }
}
