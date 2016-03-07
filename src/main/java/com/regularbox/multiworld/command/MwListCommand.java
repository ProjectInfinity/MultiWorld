package com.regularbox.multiworld.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.regularbox.multiworld.MultiWorld;

public class MwListCommand extends Command {

    private MultiWorld plugin;

    public MwListCommand(MultiWorld plugin) {
        super("mwlist", "Lists loaded worlds in MultiWorld.", "/mwlist");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        if(!sender.isOp()) {
            sender.sendMessage(TextFormat.RED + "Only operators are allowed to use MultiWorld.");
            return true;
        }

        StringBuilder worlds = new StringBuilder();

        boolean color = true;

        for(String world : plugin.getWorldManager().getWorlds()) {
            worlds.append(color ? TextFormat.WHITE : TextFormat.GRAY).append(world).append(", ");
            color = !color;
        }

        sender.sendMessage(TextFormat.YELLOW + "Worlds currently handled by MultiWorld:");
        sender.sendMessage(worlds.length() > 0 ? worlds.toString().substring(0, worlds.length() - 2) : TextFormat.GRAY + "None.");

        return true;

    }
}