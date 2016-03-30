package com.regularbox.multiworld.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.regularbox.multiworld.MultiWorld;

public class MwCreateCommand extends Command {

    private MultiWorld plugin;

    public MwCreateCommand(MultiWorld plugin) {
        super("mwcreate", "Creates a world with the specified settings.", "/mwcreate [name] [seed] [options]");
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

        // TODO: Add metadata options.

        String name = args[0];
        long seed = args.length > 1 ? args[1].hashCode() : -1;

        if(plugin.getServer().isLevelGenerated(name)) {
            sender.sendMessage(TextFormat.RED + "A world with that name already exists.");
            return true;
        }

        boolean result = seed != -1 ? plugin.getServer().generateLevel(name, seed) : plugin.getServer().generateLevel(name);

        if(result) {
            // Create world file now.
            plugin.getWorldManager().createWorld(name);
            sender.sendMessage(TextFormat.GREEN + "Successfully generated " + name);
        } else {
            sender.sendMessage(TextFormat.RED + "Failed to generate " + name);
        }

        return true;

    }
}