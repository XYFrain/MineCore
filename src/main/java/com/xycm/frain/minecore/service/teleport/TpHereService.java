package com.xycm.frain.minecore.service.teleport;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.entity.Player;

/**
 * TpHere 业务逻辑：把目标玩家传送到执行者身边。
 */
public class TpHereService {

    public Result tpHere(Player executor, Player target) {
        target.teleport(executor.getLocation());
        return Result.ok(null, "TpHereSuccess", "{player}", target.getName());
    }
}
