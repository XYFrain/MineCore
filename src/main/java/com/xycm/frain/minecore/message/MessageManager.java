package com.xycm.frain.minecore.message;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.util.ColorUtil;
import org.bukkit.command.CommandSender;

/**
 * 消息管理 —— 自动加前缀、颜色转换、发送。
 * <p>
 * 原始数据由 {@link MessageConfig} 提供，本类负责前缀拼接和颜色处理。
 * 消息值为空时跳过发送。
 * <p>
 * 支持 {@code {key}} 占位符替换，替换组以键值对形式传入。
 */
public final class MessageManager {

    private MessageManager() {}

    public static void sendReloadSuccess(CommandSender sender) {
        send(sender, MessageConfig.getReloadSuccess());
    }

    public static void sendNoPermission(CommandSender sender) {
        send(sender, MessageConfig.getNoPermission());
    }

    public static void sendPlayerOnly(CommandSender sender) {
        send(sender, MessageConfig.getPlayerOnly());
    }

    public static void sendInvalidArgument(CommandSender sender) {
        send(sender, MessageConfig.getInvalidArgument());
    }

    public static void sendPlayerNotFound(CommandSender sender, String playerName) {
        send(sender, MessageConfig.getPlayerNotFound(), "{player}", playerName);
    }

    public static void sendFeedSuccess(CommandSender sender) {
        send(sender, MessageConfig.getFeedSuccess());
    }

    public static void sendFeedOthers(CommandSender sender, String targetName) {
        send(sender, MessageConfig.getFeedOthers(), "{player}", targetName);
    }

    public static void sendHealSuccess(CommandSender sender) {
        send(sender, MessageConfig.getHealSuccess());
    }

    public static void sendHealOthers(CommandSender sender, String targetName) {
        send(sender, MessageConfig.getHealOthers(), "{player}", targetName);
    }

    public static void sendSuicideSuccess(CommandSender sender) {
        send(sender, MessageConfig.getSuicideSuccess());
    }

    public static void sendFlyToggle(CommandSender sender, boolean enabled) {
        send(sender, enabled ? MessageConfig.getFlyEnabled() : MessageConfig.getFlyDisabled());
    }

    public static void sendFlyToggleOthers(CommandSender sender, String targetName, boolean enabled) {
        String key = enabled ? MessageConfig.getFlyEnabledOthers() : MessageConfig.getFlyDisabledOthers();
        send(sender, key, "{player}", targetName);
    }

    public static void sendGodToggle(CommandSender sender, boolean enabled) {
        send(sender, enabled ? MessageConfig.getGodEnabled() : MessageConfig.getGodDisabled());
    }

    public static void sendGodToggleOthers(CommandSender sender, String targetName, boolean enabled) {
        String key = enabled ? MessageConfig.getGodEnabledOthers() : MessageConfig.getGodDisabledOthers();
        send(sender, key, "{player}", targetName);
    }

    private static void send(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) return;
        sender.sendMessage(ColorUtil.colorize(MessageConfig.getPrefix() + message));
    }

    private static void send(CommandSender sender, String message, String... replacements) {
        if (message == null || message.isEmpty()) return;
        for (int i = 0; i < replacements.length - 1; i += 2) {
            message = message.replace(replacements[i], replacements[i + 1]);
        }
        sender.sendMessage(ColorUtil.colorize(MessageConfig.getPrefix() + message));
    }
}
