package com.regularbox.multiworld.util;

import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import com.regularbox.multiworld.MultiWorld;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WorldManager {

    private MultiWorld plugin;

    private Map<String, Config> worldFiles;

    public WorldManager(MultiWorld plugin) {
        this.plugin = plugin;
        this.worldFiles = new HashMap<>();

        loadWorlds();
    }

    public void loadWorlds() {

        // Create worlds folder if it does not exist.
        if(!Files.isDirectory(Paths.get(plugin.getDataFolder().getAbsolutePath() + "/worlds"))) {
            try {
                Files.createDirectory(Paths.get(plugin.getDataFolder().getAbsolutePath() + "/worlds"));
                plugin.getLogger().info("Created 'worlds' in plugins/MultiWorld");
            } catch (IOException e) {
                plugin.getLogger().critical("Failed to create worlds folder, MultiWorld will not function correctly!");
                e.printStackTrace();
            }
        }

        // Iterate all .yml files in the worlds folder and store them.
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(plugin.getDataFolder().getAbsolutePath() + "/worlds"),
                entry -> {
                    return entry.getFileName().toString().endsWith(".yml");
                })) {
            for (Path path : directoryStream) {
                this.worldFiles.put(path.getFileName().toString().substring(0, path.getFileName().toString().length() - 4),
                        new Config(path.toString()));
            }
        } catch(IOException e) {
            plugin.getLogger().critical("Failed to load a world file!");
            e.printStackTrace();
        }

        // Iterate all world files and load the worlds that are missing.
        for(Config world : worldFiles.values()) {
            String name = (String) world.get("name");
            if(name == null) continue;
            // If the level is loaded it is usually because the iterated world is the default one. We don't touch that.
            if(plugin.getServer().isLevelLoaded(name)) continue;
            // Check if the world actually exists, skip if it doesn't.
            if(!plugin.getServer().isLevelGenerated(name)) {
                plugin.getLogger().warning("Skipping trying to load " + name + ", folder does not exist in your Nukkit worlds folder.");
                continue;
            }
            plugin.getServer().loadLevel(name);
            //Level level = plugin.getServer().getLevelByName(name);
            // TODO: Set metadata later. We don't need this right now.
        }

    }

    /**
     * Creates a world file and stores it in the world map.
     *
     * @param name Name of world
     */
    public void createWorld(String name) {
        // TODO: Add support for metadata.
        // This shouldn't really happen... But let's check for it anyway.
        if(this.worldFiles.containsKey(name)) return;
        plugin.saveResource("world.yml", "worlds/" + name + ".yml", false);
        Config world = new Config(plugin.getDataFolder().getPath() + "/worlds/" + name + ".yml");
        world.set("name", name);
        world.save();
        this.worldFiles.put(name, world);
    }

    public void saveToDisk() {
        this.worldFiles.values().forEach(Config::save);
    }

}