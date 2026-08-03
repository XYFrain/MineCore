package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 隐身业务：切换自身或目标玩家的隐身状态。
 */
public class VanishService {

    public Result toggleSelf(Player player) {
        boolean enabled = !player.isInvisible();
        player.setInvisible(enabled);
        return Result.ok(enabled, enabled ? "VanishEnabled" : "VanishDisabled");
    }

    public Result toggleOther(String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        boolean enabled = !target.isInvisible();
        target.setInvisible(enabled);
        return Result.ok(enabled, enabled ? "VanishEnabledOthers" : "VanishDisabledOthers",
                "{player}", target.getName());
    }
}
