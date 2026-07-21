package com.xycm.frain.minecore.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * messages/<语言>.yml 对应的配置类。
 * <p>
 * 纯读取 YAML，返回原始值，不做任何格式化或颜色处理。
 */
public final class MessageConfig {

    private static FileConfiguration messages;

    private MessageConfig() {}

    static void loadConfig(File file) {
        messages = YamlConfiguration.loadConfiguration(file);
    }

    /** 通用取值，未配置时返回 {@code defaultValue}。 */
    public static String get(String path, String defaultValue) {
        return messages.getString(path, defaultValue);
    }

    /** 获取消息前缀（原始字符串，含颜色码与占位符）。 */
    public static String getPrefix() {
        return messages.getString("Prefix", "");
    }

    /** 重载成功消息（原始值）。 */
    public static String getReloadSuccess() {
        return messages.getString("ReloadSuccess");
    }

    /** 无权限消息（原始值）。 */
    public static String getNoPermission() {
        return messages.getString("NoPermission");
    }

    /** 仅限玩家消息（原始值）。 */
    public static String getPlayerOnly() {
        return messages.getString("PlayerOnly");
    }

    /** 无效参数消息（原始值）。 */
    public static String getInvalidArgument() {
        return messages.getString("InvalidArgument");
    }

    /** 喂食成功消息（原始值）。 */
    public static String getFeedSuccess() {
        return messages.getString("FeedSuccess");
    }

    /** 治疗成功消息（原始值）。 */
    public static String getHealSuccess() {
        return messages.getString("HealSuccess");
    }

    /** 自杀成功消息（原始值）。 */
    public static String getSuicideSuccess() {
        return messages.getString("SuicideSuccess");
    }

    /** 飞行开启消息（原始值）。 */
    public static String getFlyEnabled() {
        return messages.getString("FlyEnabled");
    }

    /** 飞行关闭消息（原始值）。 */
    public static String getFlyDisabled() {
        return messages.getString("FlyDisabled");
    }

    /** 找不到玩家消息（原始值）。 */
    public static String getPlayerNotFound() {
        return messages.getString("PlayerNotFound");
    }

    /** 为他人喂食消息（原始值）。 */
    public static String getFeedOthers() {
        return messages.getString("FeedOthers");
    }

    /** 为他人治疗消息（原始值）。 */
    public static String getHealOthers() {
        return messages.getString("HealOthers");
    }

    /** 为他人开启飞行消息（原始值）。 */
    public static String getFlyEnabledOthers() {
        return messages.getString("FlyEnabledOthers");
    }

    /** 为他人关闭飞行消息（原始值）。 */
    public static String getFlyDisabledOthers() {
        return messages.getString("FlyDisabledOthers");
    }

    /** 无敌开启消息（原始值）。 */
    public static String getGodEnabled() {
        return messages.getString("GodEnabled");
    }

    /** 无敌关闭消息（原始值）。 */
    public static String getGodDisabled() {
        return messages.getString("GodDisabled");
    }

    /** 为他人开启无敌消息（原始值）。 */
    public static String getGodEnabledOthers() {
        return messages.getString("GodEnabledOthers");
    }

    /** 为他人关闭无敌消息（原始值）。 */
    public static String getGodDisabledOthers() {
        return messages.getString("GodDisabledOthers");
    }

}
