package com.xycm.frain.minecore.command;

import com.xycm.frain.minecore.manager.DataManager;
import org.bukkit.Bukkit;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings("NullableProblems")
final class MainTab {


    static class Player implements SuggestionProvider<BukkitCommandActor> {
        @Override
        public Collection<String> getSuggestions(ExecutionContext<BukkitCommandActor> context) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(org.bukkit.entity.Player::getName)
                    .collect(Collectors.toList());
        }
    }


    static class Home implements SuggestionProvider<BukkitCommandActor> {
        @Override
        public Collection<String> getSuggestions(ExecutionContext<BukkitCommandActor> context) {
            if (context.actor().sender() instanceof org.bukkit.entity.Player player) {
                return DataManager.getAllHomes(player.getUniqueId());
            }
            return List.of();
        }
    }


    static class Warp implements SuggestionProvider<BukkitCommandActor> {
        @Override
        public Collection<String> getSuggestions(ExecutionContext<BukkitCommandActor> context) {
            return DataManager.getAllWarps();
        }
    }


    static class Gamemode implements SuggestionProvider<BukkitCommandActor> {
        @Override
        public Collection<String> getSuggestions(ExecutionContext<BukkitCommandActor> context) {
            return List.of("survival", "creative", "adventure", "spectator");
        }
    }
}
