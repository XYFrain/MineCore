package com.xycm.frain.minecore.service.player;

import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * 游戏模式业务：切换自身或目标玩家的游戏模式。
 * <p>
 * 模式名的字符串解析（如 "creative" → GameMode）由 controller 负责，
 * service 只接收已解析的 GameMode。
 */
public class GamemodeService {

    private static final Map<GameMode, String> MODE_DISPLAY = Map.of(
            GameMode.SURVIVAL, "生存模式",
            GameMode.CREATIVE, "创造模式",
            GameMode.ADVENTURE, "冒险模式",
            GameMode.SPECTATOR, "旁观模式"
    );

    public Result applySelf(Player player, GameMode mode) {
        player.setGameMode(mode);
        return Result.ok(null, "GamemodeChanged", "{mode}", MODE_DISPLAY.get(mode));
    }

    public Result applyOther(String targetName, GameMode mode) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }
        target.setGameMode(mode);
        return Result.ok(null, "GamemodeOthers",
                "{player}", target.getName(), "{mode}", MODE_DISPLAY.get(mode));
    }
}
