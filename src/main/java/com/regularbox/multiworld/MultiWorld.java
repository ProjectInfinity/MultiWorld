package com.regularbox.multiworld;

import cn.nukkit.plugin.PluginBase;
import com.regularbox.multiworld.command.MwCreateCommand;
import com.regularbox.multiworld.command.MwDeleteCommand;
import com.regularbox.multiworld.command.MwListCommand;
import com.regularbox.multiworld.util.WorldManager;

public class MultiWorld extends PluginBase {

    private static MultiWorld plugin;
    private WorldManager worldManager;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        this.worldManager = new WorldManager(plugin);
        getServer().getCommandMap().register("mwcreate", new MwCreateCommand(plugin));
        getServer().getCommandMap().register("mwdelete", new MwDeleteCommand(plugin));
        getServer().getCommandMap().register("mwlist", new MwListCommand(plugin));
    }

    @Override
    public void onDisable() {
        this.worldManager.saveToDisk();
    }

    public static MultiWorld getPlugin() {
        return plugin;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }

}