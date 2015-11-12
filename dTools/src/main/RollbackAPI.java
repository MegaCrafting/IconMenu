/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.CuboidClipboard
 *  com.sk89q.worldedit.EditSession
 *  com.sk89q.worldedit.LocalWorld
 *  com.sk89q.worldedit.MaxChangedBlocksException
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.bukkit.BukkitWorld
 *  com.sk89q.worldedit.data.DataException
 *  com.sk89q.worldedit.world.DataException
 *  com.sk89q.worldguard.bukkit.WGBukkit
 *  com.sk89q.worldguard.domains.DefaultDomain
 *  com.sk89q.worldguard.protection.ApplicableRegionSet
 *  com.sk89q.worldguard.protection.managers.RegionManager
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  net.milkbowl.vault.permission.Permission
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package main;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import main.dTools;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class RollbackAPI {
    public static World getWorld() {
        World world = Bukkit.getServer().getWorld("world");
        if (world == null) {
            System.out.println("WARNING!!!  World Not Found!  Worldname in config file must match actual world name!");
            return null;
        }
        return world;
    }

    public static LinkedList<String> getRegion(Player pl) {
        World world = pl.getWorld();
        RegionManager regionManager = WGBukkit.getRegionManager((World)world);
        ApplicableRegionSet set = regionManager.getApplicableRegions(pl.getLocation());
        LinkedList<String> regions = new LinkedList<String>();
        for (ProtectedRegion region : set) {
            String id = region.getId();
            regions.add(id);
        }
        return regions;
    }

    public static boolean isRegionMember(String regionName, Player pl) {
        World world = pl.getWorld();
        RegionManager regionManager = WGBukkit.getRegionManager((World)world);
        ApplicableRegionSet set = regionManager.getApplicableRegions(pl.getLocation());
        LinkedList<String> regions = new LinkedList<String>();
        for (ProtectedRegion region : set) {
            String id = region.getId();
            regions.add(id);
            if (region.getMembers() == null) continue;
            Permission perms = null;
            RegisteredServiceProvider rsp = dTools.plugin.getServer().getServicesManager().getRegistration((Class)Permission.class);
            perms = (Permission)rsp.getProvider();
            if (perms == null) {
                System.out.println("Warning: perms wasn't found!");
            }
            for (String o : perms.getPlayerGroups(pl)) {
                o = o.toLowerCase();
                if (region.getMembers().toGroupsString().contains((CharSequence)o.toLowerCase())) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isWithinRegion(Location loc, String regionName, Player pl) {
        World world = null;
        world = loc.getWorld();
        RegionManager regionManager = WGBukkit.getRegionManager((World)world);
        ApplicableRegionSet set = regionManager.getApplicableRegions(loc);
        LinkedList<String> regions = new LinkedList<String>();
        for (ProtectedRegion region : set) {
            String id = region.getId();
            regions.add(id);
            if (!region.getId().equalsIgnoreCase(regionName)) continue;
            return true;
        }
        return false;
    }

    public static void ReloadRegion(Player player) throws IOException, MaxChangedBlocksException, com.sk89q.worldedit.world.DataException {
        RegionManager rm = WGBukkit.getRegionManager((World)((World)Bukkit.getWorlds().get(3)));
        ProtectedRegion region = rm.getRegion("b_arena_area");
        final Vector minx = (Vector)region.getMinimumPoint();
        boolean empty = true;
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (!RollbackAPI.isWithinRegion(plr.getLocation(), "b_arena_area", plr)) continue;
            empty = false;
        }
        if (empty) {
            File file = new File("plugins/WorldEdit/schematics/b_arena_area.schematic");
            World world = (World)Bukkit.getWorlds().get(3);
            int aX = (int)minx.getX();
            int aY = (int)minx.getY();
            int aZ = (int)minx.getZ();
            BukkitWorld BW = new BukkitWorld(world);
            EditSession es = new EditSession((LocalWorld)BW, 2000000);
            CuboidClipboard c = null;
            try {
                c = CuboidClipboard.loadSchematic((File)file);
            }
            catch (Exception e) {
                System.out.println("dTools failed to load the proper schematic!  B_arena_area!");
                e.printStackTrace();
            }
            if (c == null) {
                return;
            }
            c.place(es, (Vector)minx, false);
            Block blk = world.getBlockAt(aX, aY, aZ);
            Location nloc = blk.getLocation();
            if (player != null) {
                player.teleport(nloc);
            }
            System.out.println("NOTICE:  The bending arena has been reset");
            dTools.ArenaTimer = 0;
        }
    }

    public static void ReloadRegion3(Player player) throws IOException, MaxChangedBlocksException, com.sk89q.worldedit.world.DataException {
        RegionManager rm = WGBukkit.getRegionManager((World)((World)Bukkit.getWorlds().get(3)));
        ProtectedRegion region = rm.getRegion("b_arena_2_area");
        BlockVector minx = region.getMinimumPoint();
        boolean empty = true;
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (!RollbackAPI.isWithinRegion(plr.getLocation(), "b_arena_2_area", plr)) continue;
            empty = false;
        }
        if (empty) {
            File file = new File("plugins/WorldEdit/schematics/b_arena_2_area.schematic");
            World world = (World)Bukkit.getWorlds().get(3);
            int aX = (int)minx.getX();
            int aY = (int)minx.getY();
            int aZ = (int)minx.getZ();
            BukkitWorld BW = new BukkitWorld(world);
            EditSession es = new EditSession((LocalWorld)BW, 2000000);
            CuboidClipboard c = null;
            try {
                c = CuboidClipboard.loadSchematic((File)file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (c == null) {
                return;
            }
            c.place(es, (Vector)minx, false);
            Block blk = world.getBlockAt(aX, aY, aZ);
            Location nloc = blk.getLocation();
            if (player != null) {
                player.teleport(nloc);
            }
            System.out.println("NOTICE:  The bending arena 2 has been reset");
            dTools.ArenaTimer2 = 0;
        }
    }

    public static void ReloadPractice(Player player) throws com.sk89q.worldedit.world.DataException {
        System.out.println("trying to reload 4");
        RegionManager rm = WGBukkit.getRegionManager((World)((World)Bukkit.getWorlds().get(3)));
        ProtectedRegion region = rm.getRegion("b_arena_4_area");
        BlockVector minx = region.getMinimumPoint();
        boolean empty = true;
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (!RollbackAPI.isWithinRegion(plr.getLocation(), "b_arena_4_area", plr)) continue;
            empty = false;
        }
        if (empty) {
            File file = new File("plugins/WorldEdit/schematics/b_arena_4_area.schematic");
            World world = (World)Bukkit.getWorlds().get(3);
            int aX = (int)minx.getX();
            int aY = (int)minx.getY();
            int aZ = (int)minx.getZ();
            BukkitWorld BW = new BukkitWorld(world);
            EditSession es = new EditSession((LocalWorld)BW, 2000000);
            CuboidClipboard c = null;
            try {
                c = CuboidClipboard.loadSchematic((File)file);
            }
            catch (DataException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                c.place(es, (Vector)minx, false);
            }
            catch (MaxChangedBlocksException e) {
                e.printStackTrace();
            }
            Block blk = world.getBlockAt(aX, aY, aZ);
            Location nloc = blk.getLocation();
            if (player != null) {
                player.teleport(nloc);
            }
            System.out.println("NOTICE:  The bending arena 4 has been reset");
            dTools.ArenaTimer4 = 0;
        }
    }

    public static void ReloadRegion4(Player player) throws IOException, MaxChangedBlocksException, com.sk89q.worldedit.world.DataException {
        RegionManager rm = WGBukkit.getRegionManager((World)((World)Bukkit.getWorlds().get(3)));
        ProtectedRegion region = rm.getRegion("probending_floor");
        BlockVector minx = region.getMinimumPoint();
        boolean empty = true;
        if (empty) {
            File file = new File("plugins/WorldEdit/schematics/probending_floor.schematic");
            World world = (World)Bukkit.getWorlds().get(3);
            int aX = (int)minx.getX();
            int aY = (int)minx.getY();
            int aZ = (int)minx.getZ();
            BukkitWorld BW = new BukkitWorld(world);
            EditSession es = new EditSession((LocalWorld)BW, 2000000);
            CuboidClipboard c = CuboidClipboard.loadSchematic((File)file);
            c.place(es, (Vector)minx, false);
            Block blk = world.getBlockAt(aX, aY, aZ);
            Location location = blk.getLocation();
        }
    }

    public static void ReloadRegion2(Player player) throws IOException, MaxChangedBlocksException, com.sk89q.worldedit.world.DataException {
        RegionManager rm = WGBukkit.getRegionManager((World)((World)Bukkit.getWorlds().get(3)));
        ProtectedRegion region = rm.getRegion("not_chosen_area");
        BlockVector minx = region.getMinimumPoint();
        boolean empty = true;
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (!RollbackAPI.isWithinRegion(plr.getLocation(), "not_chosen_area", plr)) continue;
            empty = false;
        }
        if (empty) {
            File file = new File("plugins/WorldEdit/schematics/not_chosen_area.schematic");
            World world = (World)Bukkit.getWorlds().get(3);
            int aX = (int)minx.getX();
            int aY = (int)minx.getY();
            int aZ = (int)minx.getZ();
            BukkitWorld BW = new BukkitWorld(world);
            EditSession es = new EditSession((LocalWorld)BW, 2000000);
            CuboidClipboard c = CuboidClipboard.loadSchematic((File)file);
            c.place(es, (Vector)minx, false);
            Block blk = world.getBlockAt(aX, aY, aZ);
            Location nloc = blk.getLocation();
            if (player != null) {
                player.teleport(nloc);
            }
            System.out.println("NOTICE:  The not_chosen area has been reset");
            dTools.NotChosenTimer = 0;
        }
    }
}

