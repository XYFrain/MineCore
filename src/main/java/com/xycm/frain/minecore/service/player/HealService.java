package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

/**
 * 治疗业务：恢复自身或目标玩家的生命值。
 */
public class HealService {

    public Result healSelf(Player player) {
        double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(max);
        return Result.ok(null, "HealSuccess");
    }

    public Result healOther(String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        double max = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        target.setHealth(max);
        return Result.ok(null, "HealOthers", "{player}", target.getName());
    }
}
