package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 喂食业务：恢复自身或目标玩家的饱食度。
 */
public class FeedService {

    public Result feedSelf(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(10);
        return Result.ok(null, "FeedSuccess");
    }

    public Result feedOther(String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        target.setFoodLevel(20);
        target.setSaturation(10);
        return Result.ok(null, "FeedOthers", "{player}", target.getName());
    }
}
