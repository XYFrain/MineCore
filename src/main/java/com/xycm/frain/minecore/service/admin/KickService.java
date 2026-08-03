package com.xycm.frain.minecore.service.admin;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.model.Result;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * kick 业务逻辑。
 * <p>
 * 执行时记录控制台日志（时间/执行者/对象/理由），不全服广播。
 */
public class KickService {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** 踢出玩家。reason 为空时不显示理由。 */
    public Result kick(String executorName, String targetName, String reason) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            return Result.fail("PlayerNotFound", "{player}", targetName);
        }

        // 记录日志
        Logger logger = MineCore.getInstance().getLogger();
        String time = LocalDateTime.now().format(TIME_FORMAT);
        if (reason.isEmpty()) {
            logger.info(String.format("[%s] %s kicked %s", time, executorName, targetName));
        } else {
            logger.info(String.format("[%s] %s kicked %s: %s", time, executorName, targetName, reason));
        }

        // 踢出
        target.kickPlayer(reason);
        return Result.ok(null, "KickSuccess", "{player}", targetName);
    }
}
