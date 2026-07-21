package com.xycm.frain.minecore.controller.command.subcommand.admin;

import com.xycm.frain.minecore.config.ConfigManager;
import com.xycm.frain.minecore.controller.command.subcommand.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.util.PermissionUtil;
import org.bukkit.command.CommandSender;

/**
 * /minecore reload —— 重载配置与消息。
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
        if (!PermissionUtil.check(sender, "minecore.admin.reload")) return true;
        ConfigManager.reload();
        MessageManager.sendReloadSuccess(sender);
        return true;
    }
}
