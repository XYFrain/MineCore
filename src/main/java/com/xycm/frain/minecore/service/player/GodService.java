package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 无敌业务：切换自身或目标玩家的无敌状态。
 */
public class GodService {

    public Result toggleSelf(Player player) {
        boolean enabled = !player.isInvulnerable();
        player.setInvulnerable(enabled);
        return Result.ok(enabled, enabled ? "GodEnabled" : "GodDisabled");
    }

    public Result toggleOther(String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        boolean enabled = !target.isInvulnerable();
        target.setInvulnerable(enabled);
        return Result.ok(enabled, enabled ? "GodEnabledOthers" : "GodDisabledOthers",
                "{player}", target.getName());
    }
}
