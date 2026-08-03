package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.entity.Player;

/**
 * 自杀业务：将玩家生命值清零。
 */
public class SuicideService {

    public Result suicide(Player player) {
        player.setHealth(0);
        return Result.ok(null, "SuicideSuccess");
    }
}
