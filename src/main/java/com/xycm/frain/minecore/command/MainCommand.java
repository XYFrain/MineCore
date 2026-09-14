package com.xycm.frain.minecore.command;

import com.xycm.frain.minecore.config.MessageConfig;
import com.xycm.frain.minecore.service.MessageService;

import com.xycm.frain.minecore.service.ServerReloadService;
import com.xycm.frain.minecore.service.PlayerFlyService;
import com.xycm.frain.minecore.service.PlayerGamemodeService;
import com.xycm.frain.minecore.service.PlayerGodService;
import com.xycm.frain.minecore.service.PlayerHealService;
import com.xycm.frain.minecore.service.PlayerSuicideService;
import com.xycm.frain.minecore.service.PlayerVanishService;
import com.xycm.frain.minecore.service.TeleportBackService;
import com.xycm.frain.minecore.service.TeleportHomeService;
import com.xycm.frain.minecore.service.TeleportSpawnService;
import com.xycm.frain.minecore.service.TeleportTpHereService;
import com.xycm.frain.minecore.service.TeleportWarpService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.SuggestWith;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.stream.StringStream;

@Command("minecore")
public class MainCommand {
    @CommandPermission("minecore.player.help")
    @Description("查看帮助")
    public void onDefault(CommandSender sender, StringStream input) {
        boolean hasExtraArgs = input.source().trim().contains(" ");
        if (!hasExtraArgs) {
            sender.sendMessage("""
                    === MineCore 帮助 ===
                    /minecore fly [玩家] - 切换飞行模式
                    /minecore god [玩家] - 切换无敌模式
                    /minecore heal [玩家] - 恢复生命值
                    /minecore suicide - 自杀
                    /minecore gamemode <模式> [玩家] - 切换游戏模式
                    /minecore vanish [玩家] - 切换隐身模式
                    /minecore reload - 重载插件配置
                    /minecore back - 返回上一位置
                    /minecore home [名字] - 回家
                    /minecore sethome <名字> - 设置家
                    /minecore delhome <名字> - 删除家
                    /minecore warp [名字] - 传送到公共传送点
                    /minecore setwarp <名字> - 创建传送点
                    /minecore delwarp <名字> - 删除传送点
                    /minecore warplist - 显示公共传送点列表
                    /minecore spawn [玩家] - 传送到出生点
                    /minecore tphere <玩家> - 传送玩家到自己身边""");
        } else {
            MessageService.send(sender, MessageConfig.getInstance().getExceptionUnknownCommand());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("fly")
    @CommandPermission("minecore.player.fly")
    @Description("切换飞行模式")
    public void onFly(CommandSender sender, @Optional @SuggestWith(MainTab.Player.class) String target) {
        PlayerFlyService.toggle(sender, target);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("gamemode")
    @CommandPermission("minecore.player.gamemode")
    @Description("切换游戏模式")
    public void onGamemode(CommandSender sender, @SuggestWith(MainTab.Gamemode.class) String gamemode, @Optional @SuggestWith(MainTab.Player.class) String target) {
        PlayerGamemodeService.change(sender, gamemode, target);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("god")
    @CommandPermission("minecore.player.god")
    @Description("切换无敌模式")
    public void onGod(CommandSender sender, @Optional @SuggestWith(MainTab.Player.class) String target) {
        PlayerGodService.toggle(sender, target);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("heal")
    @CommandPermission("minecore.player.heal")
    @Description("恢复生命值")
    public void onHeal(CommandSender sender, @Optional @SuggestWith(MainTab.Player.class) String target) {
        PlayerHealService.execute(sender, target);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("suicide")
    @CommandPermission("minecore.player.suicide")
    @Description("自杀")
    public void onSuicide(Player player) {
        PlayerSuicideService.execute(player);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("vanish")
    @CommandPermission("minecore.player.vanish")
    @Description("切换隐身模式")
    public void onVanish(CommandSender sender, @Optional @SuggestWith(MainTab.Player.class) String target) {
        PlayerVanishService.toggle(sender, target);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("reload")
    @CommandPermission("minecore.admin.reload")
    @Description("重载插件配置")
    public void onReload(CommandSender sender) {
        ServerReloadService.execute(sender);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("back")
    @CommandPermission("minecore.teleport.back")
    @Description("返回上一位置")
    public void onBack(Player player) {
        TeleportBackService.execute(player);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("home")
    @CommandPermission("minecore.teleport.home")
    @Description("回家")
    public void onHome(Player player, @Optional @SuggestWith(MainTab.Home.class) String homeName) {
        TeleportHomeService.teleport(player, homeName);
    }

    @Subcommand("sethome")
    @CommandPermission("minecore.teleport.home")
    @Description("设置家")
    public void onSetHome(Player player, String homeName) {
        TeleportHomeService.create(player, homeName);
    }

    @Subcommand("delhome")
    @CommandPermission("minecore.teleport.home")
    @Description("删除家")
    public void onDelHome(Player player, @SuggestWith(MainTab.Home.class) String homeName) {
        TeleportHomeService.delete(player, homeName);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("warp")
    @CommandPermission("minecore.teleport.warp")
    @Description("传送到公共传送点")
    public void onWarp(Player player, @Optional @SuggestWith(MainTab.Warp.class) String warpName) {
        TeleportWarpService.teleport(player, warpName);
    }

    @Subcommand("setwarp")
    @CommandPermission("minecore.teleport.warp")
    @Description("创建传送点")
    public void onSetWarp(Player player, String warpName) {
        TeleportWarpService.create(player, warpName);
    }

    @Subcommand("delwarp")
    @CommandPermission("minecore.teleport.warp")
    @Description("删除传送点")
    public void onDelWarp(CommandSender sender, @SuggestWith(MainTab.Warp.class) String warpName) {
        TeleportWarpService.delete(sender, warpName);
    }

    @Subcommand("warplist")
    @CommandPermission("minecore.teleport.warp")
    @Description("显示公共传送点列表")
    public void onWarpList(CommandSender sender) {
        TeleportWarpService.list(sender);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("spawn")
    @CommandPermission("minecore.teleport.spawn")
    @Description("传送到出生点")
    public void onSpawn(CommandSender sender, @Optional @SuggestWith(MainTab.Player.class) String target) {
        TeleportSpawnService.teleport(sender, target);
    }

    //------------------------------------------------------------------------------------------------------------------
    @Subcommand("tphere")
    @CommandPermission("minecore.teleport.tphere")
    @Description("传送玩家到自己身边")
    public void onTpHere(Player player, @SuggestWith(MainTab.Player.class) String target) {
        TeleportTpHereService.teleport(player, target);
    }
}
