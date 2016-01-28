package com.regularbox.multiworld.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.regularbox.multiworld.MultiWorld;

public class MwCreateCommand extends Command {

    private MultiWorld plugin;

    public MwCreateCommand(MultiWorld plugin) {
        super("mwcreate", "Creates a world with the specified settings.", "/mwcreate [name] [options]");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if(!sender.isOp()) {
            sender.sendMessage(TextFormat.RED + "Only operators are allowed to use MultiWorld.");
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(TextFormat.RED + "You need to at least specify a name when creating a world.");
            return true;
        }

        String name = args[0];

        if(plugin.getServer().isLevelGenerated(name)) {
            sender.sendMessage(TextFormat.RED + "A world with that name already exists.");
            return true;
        }

        plugin.getServer().generateLevel(name);

        return true;

    }
}