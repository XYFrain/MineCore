package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * 飞行业务：切换自身或目标玩家的飞行状态。
 * <p>
 * 不发消息、不解析命令行参数，结果通过 Result 返回（消息 key 见 messages yml）。
 */
public class FlyService {

    /** 切换自身飞行状态。sender 必须是玩家（controller 已保证）。 */
    public Result toggleSelf(Player player) {
        boolean enabled = !player.getAllowFlight();
        player.setAllowFlight(enabled);
        return Result.ok(enabled, enabled ? "FlyEnabled" : "FlyDisabled");
    }

    /** 切换指定玩家的飞行状态。玩家不存在时返回失败结果。 */
    public Result toggleOther(String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        boolean enabled = !target.getAllowFlight();
        target.setAllowFlight(enabled);
        return Result.ok(enabled, enabled ? "FlyEnabledOthers" : "FlyDisabledOthers",
                "{player}", target.getName());
    }
}
