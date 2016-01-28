package com.regularbox.multiworld.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.regularbox.multiworld.MultiWorld;

public class MwDeleteCommand extends Command {

    private MultiWorld plugin;
    private String defaultWorld;

    public MwDeleteCommand(MultiWorld plugin) {
        super("mwdelete", "Deletes a world", "/mwdelete [world]");
        this.plugin = plugin;
        this.defaultWorld = plugin.getServer().getDefaultLevel().getName();
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if(!sender.isOp()) {
            sender.sendMessage(TextFormat.RED + "Only operators are allowed to use MultiWorld.");
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(TextFormat.RED + "You need to at least specify a name when deleting a world.");
            return true;
        }

        String world = args[0];

        if(world.equalsIgnoreCase(defaultWorld)) {
            sender.sendMessage(TextFormat.RED + "You cannot delete the default world.");
            return true;
        }

        if(!plugin.getServer().isLevelGenerated(world)) {
            sender.sendMessage(TextFormat.RED + "Found no world by that name.");
            return true;
        }

        if(plugin.getWorldManager().deleteWorld(world)) {
            sender.sendMessage(TextFormat.GREEN + "Deleted world '" + world + "'.");
        } else {
            sender.sendMessage(TextFormat.RED + "Failed to delete world '" + world + "'.");
        }

        return true;

    }
}