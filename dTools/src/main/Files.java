/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.Plugin
 */
package main;

import java.io.File;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Files {
    private static YamlConfiguration arenas = null;
    private static File arenasFile = null;
    public static List<?> arenaList = null;

    public static FileConfiguration getArenas() {
        if (arenas == null) {
            Files.reloadArenas();
        }
        return arenas;
    }

    public static void reloadArenas() {
        if (arenasFile == null) {
            arenasFile = new File(Bukkit.getPluginManager().getPlugin("dTools").getDataFolder(), "Arenas.yml");
        }
        arenas = YamlConfiguration.loadConfiguration((File)arenasFile);
        arenaList = arenas.getList("Arenas");
        LinkedList regions = new LinkedList();
        int i = 0;
        for (i = 0; i <= arenaList.size(); ++i) {
            String category = (String)arenaList.get(i);
            System.out.println("YAML Category Found!: " + category);
            Map key = arenas.getValues(false);
            String value = (String)key.get(category);
            System.out.println("Yaml Key Found!: " + value);
        }
    }
}

