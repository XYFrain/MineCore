package com.xycm.frain.minecore.manager;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.command.ExceptionHandler;
import com.xycm.frain.minecore.command.MainCommand;
import lombok.experimental.UtilityClass;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

@UtilityClass
public class CommandManager {

    public void register() {
        Lamp<BukkitCommandActor> commandFramework = BukkitLamp.builder(MineCore.getInstance())
                .exceptionHandler(ExceptionHandler.instance)
                .build();
        commandFramework.register(new MainCommand());
    }
}