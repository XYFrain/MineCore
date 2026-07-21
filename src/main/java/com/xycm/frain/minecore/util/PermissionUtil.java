package com.xycm.frain.minecore.util;

import com.xycm.frain.minecore.message.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 权限工具类 — 统一权限检查
 */
public final class PermissionUtil {

    private PermissionUtil() {}

    /**
     * 检查是否有权限，无权限时自动发送提示消息
     *
     * @return true=有权限, false=无权限（已自动发送提示）
     */
    public static boolean check(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        MessageManager.sendNoPermission(sender);
        return false;
    }

    /**
     * 仅限玩家使用，控制台不通过
     */
    public static boolean playerOnly(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        MessageManager.sendPlayerOnly(sender);
        return false;
    }
}
