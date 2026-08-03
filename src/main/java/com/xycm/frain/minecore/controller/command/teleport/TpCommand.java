package com.xycm.frain.minecore.controller.command.teleport;

import com.xycm.frain.minecore.controller.command.SubCommand;
import com.xycm.frain.minecore.message.MessageManager;
import com.xycm.frain.minecore.model.Result;
import com.xycm.frain.minecore.service.teleport.TpService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * /minecore tp（或 /tp）—— 传送。
 * <ul>
 *   <li>tp &lt;玩家&gt; —— 把自己传送到玩家身边</li>
 *   <li>tp &lt;x&gt; &lt;y&gt; &lt;z&gt; —— 把自己传送到坐标</li>
 * </ul>
 * 传玩家到玩家是 tphere 的职责；传玩家到坐标也由 tphere 负责（PLAN 第 3.1 节）。
 */
public class TpCommand implements SubCommand {

    private final TpService tpService;

    public TpCommand(TpService tpService) {
        this.tpService = tpService;
    }

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return "传送";
    }

    @Override
    public String getUsage() {
        return "<玩家> | <x> <y> <z>";
    }

    @Override
    public String getPermission() {
        return "minecore.teleport.tp";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Result result = switch (args.length) {
            case 1 -> tpSelfToPlayer(sender, args[0]);
            case 3 -> tpSelfToCoords(sender, args[0], args[1], args[2]);
            default -> Result.fail("InvalidArgument");
        };

        if (result.getMessageKey() != null) {
            MessageManager.sendByKey(sender, result.getMessageKey(), result.getPlaceholders());
        }
        return true;
    }

    private Result tpSelfToPlayer(CommandSender sender, String targetName) {
        if (!(sender instanceof Player player)) {
            MessageManager.sendPlayerOnly(sender);
            return Result.ok();
        }
        return tpService.selfToPlayer(player, targetName);
    }

    private Result tpSelfToCoords(CommandSender sender, String x, String y, String z) {
        if (!(sender instanceof Player player)) {
            MessageManager.sendPlayerOnly(sender);
            return Result.ok();
        }
        Location loc = parseLocation(player.getWorld(), x, y, z);
        if (loc == null) return Result.fail("InvalidArgument");
        return tpService.selfToLocation(player, loc);
    }

    private Location parseLocation(org.bukkit.World world, String x, String y, String z) {
        try {
            return new Location(world, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String prefix = args[1].toLowerCase(Locale.ROOT);
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(prefix))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
