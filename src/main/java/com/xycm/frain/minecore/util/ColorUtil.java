package com.xycm.frain.minecore.util;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 颜色工具类 — 文字颜色格式化
 */
public final class ColorUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    private ColorUtil() {}

    /**
     * 将 & 颜色代码和 &#RRGGBB 十六进制颜色转为 Minecraft 颜色符号
     */
    public static String colorize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        // 先处理十六进制颜色
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            String hex = matcher.group(1);
            matcher.appendReplacement(builder, translateHex(hex));
        }
        matcher.appendTail(builder);
        return ChatColor.translateAlternateColorCodes('&', builder.toString());
    }

    /**
     * 纯十六进制颜色转换 FF0000 → §x§f§f§0§0§0§0
     */
    private static String translateHex(String hex) {
        StringBuilder sb = new StringBuilder("§x");
        for (char c : hex.toCharArray()) {
            sb.append('§').append(c);
        }
        return sb.toString();
    }

    /**
     * 去掉所有颜色符号，返回纯文本
     */
    public static String strip(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return ChatColor.stripColor(text);
    }
}
