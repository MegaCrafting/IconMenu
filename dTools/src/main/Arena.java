/*
 * Decompiled with CFR 0_102.
 */
package main;

import main.Files;

public class Arena {
    private String name;
    private String world;
    private String TriggerRegion;
    private String ActiveRegion;
    private String Schematic;

    public Arena(String name) {
        this.name = name;
    }

    public String ArenaWorld(String world) {
        this.world = world;
        return world;
    }

    public String getTrigger(String region) {
        this.TriggerRegion = region;
        return region;
    }

    public String getCreator() {
        return Files.getArenas().getString("Arenas." + this.name + ".Creator");
    }

    public String getName() {
        return this.name;
    }
}

