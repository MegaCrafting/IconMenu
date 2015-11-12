/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package Util;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import java.io.PrintStream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Tools {
    public static Player ConvUtoP(User u) {
        Essentials ess = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        User user = null;
        for (Player pl : Bukkit.getOnlinePlayers()) {
            user = ess.getUser(pl);
            if (user == null) continue;
            return pl;
        }
        return null;
    }

    public static User ConvPtoU(Player player) {
        Essentials ess = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        User user = null;
        user = ess.getUser(player);
        if (user != null) {
            return user;
        }
        System.out.println("[dTools] Broadcast - unable to use essentials to convert Player to User");
        return null;
    }

    public static BlockFace getBlockFaceDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0f) % 360.0f;
        if (rotation < 0.0) {
            rotation+=360.0;
        }
        if (0.0 <= rotation && rotation < 22.5) {
            return BlockFace.NORTH;
        }
        if (22.5 <= rotation && rotation < 67.5) {
            return BlockFace.NORTH_EAST;
        }
        if (67.5 <= rotation && rotation < 112.5) {
            return BlockFace.EAST;
        }
        if (112.5 <= rotation && rotation < 157.5) {
            return BlockFace.SOUTH_EAST;
        }
        if (157.5 <= rotation && rotation < 202.5) {
            return BlockFace.SOUTH;
        }
        if (202.5 <= rotation && rotation < 247.5) {
            return BlockFace.SOUTH_WEST;
        }
        if (247.5 <= rotation && rotation < 292.5) {
            return BlockFace.WEST;
        }
        if (292.5 <= rotation && rotation < 337.5) {
            return BlockFace.NORTH_WEST;
        }
        if (337.5 <= rotation && rotation < 360.0) {
            return BlockFace.NORTH;
        }
        return null;
    }
}

