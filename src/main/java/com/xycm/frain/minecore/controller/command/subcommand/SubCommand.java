package com.xycm.frain.minecore.controller.command.subcommand;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * 子命令接口，所有 /minecore <子命令> 都实现此接口。
 */
public interface SubCommand {

    /**
     * 子命令名称（小写），用于匹配 /minecore <name>。
     */
    String getName();

    /**
     * 子命令简短描述，用于帮助信息。
     */
    String getDescription();

    /**
     * 子命令用法示例（如 "&lt;玩家&gt;"），无参数时返回空字符串。
     */
    default String getUsage() {
        return "";
    }

    /**
     * 执行此命令所需的权限节点（格式：minecore.<分类>.<指令名>，必须设置）。
     */
    String getPermission();

    /**
     * 执行子命令逻辑。
     *
     * @param sender 命令发送者
     * @param args   剩余参数数组（不含子命令名本身）
     * @return 命令是否处理成功
     */
    boolean execute(CommandSender sender, String[] args);

    /**
     * 是否支持以其他玩家为目标。Tab 补全时若返回 {@code true}，第二个参数补全在线玩家名。
     */
    default boolean supportsPlayerTarget() {
        return false;
    }

    /**
     * 自定义 Tab 补全。返回非空列表时直接使用，返回空列表时走默认逻辑。
     *
     * @param sender 命令发送者
     * @param args   完整参数数组（含子命令名本身）
     */
    default List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
