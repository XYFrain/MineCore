package com.xycm.frain.minecore.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类 — 时间格式化与转换
 */
public final class TimeUtil {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TimeUtil() {}

    /**
     * 将秒数转为可读的中文时间
     * 3661 → "1小时 1分 1秒"
     */
    public static String formatSeconds(long totalSeconds) {
        if (totalSeconds <= 0) return "0秒";

        long days = TimeUnit.SECONDS.toDays(totalSeconds);
        long hours = TimeUnit.SECONDS.toHours(totalSeconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60;
        long seconds = totalSeconds % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("天 ");
        if (hours > 0) sb.append(hours).append("小时 ");
        if (minutes > 0) sb.append(minutes).append("分 ");
        if (seconds > 0) sb.append(seconds).append("秒");

        return sb.toString().trim();
    }

    /**
     * 将 Duration 转为可读的中文时间
     */
    public static String formatDuration(Duration duration) {
        return formatSeconds(duration.getSeconds());
    }

    /**
     * 时间戳（毫秒）→ 日期字符串
     */
    public static String formatTimestamp(long millis) {
        LocalDateTime time = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(millis), ZoneId.systemDefault()
        );
        return time.format(DATE_FORMAT);
    }

    /**
     * 当前时间的日期字符串
     */
    public static String now() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }
}
