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

    /**
     * 按 messages yml 的键名发送消息（键不存在或值为空时跳过）。
     * 供 service 返回的 Result 翻译成消息时使用。
     *
     * @param placeholders 以「占位符, 值」成对传入，如 "{player}", "Steve"
     */
    public static void sendByKey(CommandSender sender, String messageKey, String... placeholders) {
        if (messageKey == null) return;
        send(sender, MessageConfig.get(messageKey, ""), placeholders);
    }

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

    public static void sendGamemodeChanged(CommandSender sender, String modeName) {
        send(sender, MessageConfig.getGamemodeChanged(), "{mode}", modeName);
    }

    public static void sendGamemodeOthers(CommandSender sender, String targetName, String modeName) {
        send(sender, MessageConfig.getGamemodeOthers(), "{player}", targetName, "{mode}", modeName);
    }

    public static void sendVanishToggle(CommandSender sender, boolean enabled) {
        send(sender, enabled ? MessageConfig.getVanishEnabled() : MessageConfig.getVanishDisabled());
    }

    public static void sendVanishToggleOthers(CommandSender sender, String targetName, boolean enabled) {
        String key = enabled ? MessageConfig.getVanishEnabledOthers() : MessageConfig.getVanishDisabledOthers();
        send(sender, key, "{player}", targetName);
    }

    /** 发送 home 列表（/home 无参数、多家时）。 */
    public static void sendHomeList(CommandSender sender, java.util.Set<String> names) {
        String header = MessageConfig.getPrefix() + "&a你的家：";
        sender.sendMessage(com.xycm.frain.minecore.util.ColorUtil.colorize(header));
        for (String name : names) {
            sender.sendMessage(com.xycm.frain.minecore.util.ColorUtil.colorize(
                    MessageConfig.getPrefix() + "&7- &f" + name));
        }
    }

    /** 发送 warp 列表。 */
    public static void sendWarpList(CommandSender sender, java.util.Set<String> names) {
        String header = MessageConfig.getPrefix() + "&a公共传送点：";
        sender.sendMessage(com.xycm.frain.minecore.util.ColorUtil.colorize(header));
        for (String name : names) {
            sender.sendMessage(com.xycm.frain.minecore.util.ColorUtil.colorize(
                    MessageConfig.getPrefix() + "&7- &f" + name));
        }
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
