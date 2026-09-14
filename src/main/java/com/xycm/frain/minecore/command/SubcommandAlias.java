package com.xycm.frain.minecore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;


public final class SubcommandAlias extends Command {

    private final Command parent;
    private final String subcommand;

    public SubcommandAlias(String name, String subcommand, Command parent) {
        super(name);
        this.parent = parent;
        this.subcommand = subcommand;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return parent.execute(sender, label, prependSubcommand(args));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args)
            throws IllegalArgumentException {
        return parent.tabComplete(sender, alias, prependSubcommand(args));
    }


    private String[] prependSubcommand(String[] args) {
        String[] result = new String[args.length + 1];
        result[0] = subcommand;
        System.arraycopy(args, 0, result, 1, args.length);
        return result;
    }
}