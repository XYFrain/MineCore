package com.xycm.frain.minecore.controller.command;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * 子命令接口 —— /minecore &lt;子命令&gt; 与独立命令（如 /fly）共用的命令契约。
 * <p>
 * 权限检查不写在实现类里，由分发层（MainCommand / 独立命令包装器）统一处理。
 */
public interface SubCommand {

    /** 子命令名称（小写）。既是 /minecore &lt;name&gt; 的匹配键，也是独立命令的命令名。 */
    String getName();

    /** 简短描述，用于帮助信息。 */
    String getDescription();

    /** 用法示例（如 "[玩家]"），无参数时返回空字符串。 */
    default String getUsage() {
        return "";
    }

    /** 执行此命令所需的权限节点（格式：minecore.&lt;分类&gt;.&lt;命令名&gt;）。 */
    String getPermission();

    /**
     * 执行子命令逻辑（权限已在分发层检查过）。
     *
     * @param sender 命令发送者
     * @param args   剩余参数数组（不含子命令名本身）
     * @return 命令是否已处理（true 时 Bukkit 不再显示用法提示）
     */
    boolean execute(CommandSender sender, String[] args);

    /** 是否支持以其他玩家为目标。为 true 时补全第一个参数时给出在线玩家名。 */
    default boolean supportsPlayerTarget() {
        return false;
    }

    /** 是否注册为独立命令（如 /fly）。help 不注册（保留原版 /help）。 */
    default boolean isStandalone() {
        return true;
    }

    /**
     * 自定义 Tab 补全。返回空列表时走分发层的默认补全。
     * 实现类需自行按已输入前缀过滤结果。
     *
     * @param sender 命令发送者
     * @param args   完整参数数组（含子命令名本身，即 args[0] 是子命令名）
     */
    default List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
