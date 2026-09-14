package com.xycm.frain.minecore.manager;

import com.xycm.frain.minecore.MineCore;
import com.xycm.frain.minecore.command.ExceptionHandler;
import com.xycm.frain.minecore.command.MainCommand;
import com.xycm.frain.minecore.command.SubcommandAlias;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.util.List;

@UtilityClass
public class CommandManager {


    private static final List<String> SHORTCUTS = List.of(
            "fly", "god", "heal", "suicide", "gamemode", "vanish", "back",
            "home", "sethome", "delhome", "warp", "setwarp", "delwarp",
            "warplist", "spawn", "tphere");

    public static void register() {
        Lamp<BukkitCommandActor> commandFramework = BukkitLamp.builder(MineCore.getInstance())
                .exceptionHandler(ExceptionHandler.instance)
                .build();

        commandFramework.register(new MainCommand());
        registerShortcuts();
    }


    private static void registerShortcuts() {
        CommandMap commandMap = Bukkit.getCommandMap();
        Command minecore = commandMap.getCommand("minecore");
        if (minecore == null) return;
        for (String name : SHORTCUTS) {
            commandMap.register("minecore", new SubcommandAlias(name, name, minecore));
        }
    }
}