package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.manager.ConfigManager;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;

@UtilityClass
public class ServerReloadService {

    public static void execute(CommandSender sender) {
        ConfigManager.reload();
        MessageService.send(sender, MessageConfig.getInstance().getReloadSuccess());
    }
}
