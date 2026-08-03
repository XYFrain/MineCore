package com.xycm.frain.minecore.controller.command.admin;

import com.xycm.frain.minecore.config.ConfigManager;
import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import org.bukkit.command.CommandSender;

/**
 * /minecore reload（或 /reload）—— 重载配置与消息。
 * <p>
 * 按 PLAN 例外不建 service，直接调 ConfigManager。
 */
public class ReloadCommand implements SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "重载插件配置";
    }

    @Override
    public String getPermission() {
        return "minecore.admin.reload";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        ConfigManager.reload();
        MessageManager.sendReloadSuccess(sender);
        return true;
    }
}
