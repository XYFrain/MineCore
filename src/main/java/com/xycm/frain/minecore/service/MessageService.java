package com.xycm.frain.minecore.service;

import com.xycm.frain.minecore.config.MessageConfig;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

@UtilityClass
public class MessageService {
    public void send(CommandSender sender, String message, String... replacements) {
        if (message.isEmpty()) return;
        for (int i = 0; i < replacements.length - 1; i += 2) {
            message = message.replace(replacements[i], replacements[i + 1]);
        }
        Component prefix = MiniMessage.miniMessage().deserialize(MessageConfig.getInstance().getPrefix());
        Component content = MiniMessage.miniMessage().deserialize(message);
        sender.sendMessage(prefix.append(content));
    }
}
