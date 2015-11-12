/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package Commands;

import com.earth2me.essentials.User;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import main.dTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FirstJoin {
    public static boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (args.length >= 1) {
            UUID id = null;
            OfflinePlayer op = null;
            Player p = Bukkit.getPlayer((String)args[0]);
            if (p != null) {
                id = p.getUniqueId();
            }
            op = id != null ? Bukkit.getOfflinePlayer((UUID)id) : Bukkit.getOfflinePlayer((String)args[0]);
            if (op == null) {
                sender.sendMessage("Sorry could not find a player file for user: " + args[0] + "Are you sure you spelled it correctly?");
                return true;
            }
            long joindate = op.getFirstPlayed();
            Date time = new Date(joindate);
            sender.sendMessage((Object)ChatColor.AQUA + "Player: " + args[0] + " First joined This MegaCraft Server on: \n" + (Object)ChatColor.GOLD + time);
            if (id != null) {
                sender.sendMessage("Found user via ONLINE UUID. \n" + id);
            }
        } else {
            sender.sendMessage("You must specify a username in order to look up a user!");
            sender.sendMessage("Syntax:  /joindate <username>");
        }
        return true;
    }

    public static void ClearHomes(Player plr) {
        UUID id = plr.getUniqueId();
        OfflinePlayer op = Bukkit.getOfflinePlayer((UUID)id);
        if (op == null) {
            System.out.println("[dTools] Could not find a player file for user: " + plr.getName());
            return;
        }
        long lastdate = op.getLastPlayed();
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        long resetdate = config.getLong("General.ResetDateMS");
        long lastlogout = dTools.getES().getUser(plr).getLastLogout();
        if (resetdate >= 0 && lastlogout <= resetdate) {
            long difference = resetdate - lastdate;
            Date time = new Date(resetdate);
            Date timeleft = new Date(difference);
            if (resetdate > lastdate) {
                int days = (int)(difference / 86400000);
                plr.sendMessage((Object)ChatColor.RED + "WARNING! " + (Object)ChatColor.GOLD + "The map for this server will be reset shortly after the 1.8 update..   Please store anything you wish to keep in your inventory or an enderchest!");
                plr.sendMessage((Object)ChatColor.RED + "The map will be reset on" + time + "there are " + days + " day(s) left until the map reset!");
            }
            if (resetdate < lastdate || plr.getName().equalsIgnoreCase("dniym")) {
                System.out.println((Object)ChatColor.GOLD + "Welcome Back to " + (Object)ChatColor.AQUA + "MegaCraft " + (Object)ChatColor.RESET + plr.getName() + "! The world has been reset since you last visited!  Your /home(s), /back and your faction all been removed.");
                if (plr.getName().equalsIgnoreCase("dniym")) {
                    plr.setBedSpawnLocation(null);
                    dTools.getES().getUser(plr).setLogoutLocation(null);
                    final List<String> homes = (List<String>)dTools.getES().getUser(plr).getHomes();
                    dTools.getES().getUser(plr).setLastLocation(null);
                    User u = dTools.getES().getUser(plr);
                    String deletedhomes = "";
                    for (String delhome : homes) {
                        deletedhomes = String.valueOf(deletedhomes) + ", " + delhome;
                        try {
                            dTools.getES().getUser(plr).delHome(delhome);
                            continue;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("[dTools]: Deleted homes for user: " + plr.getName() + " : " + deletedhomes);
                }
            }
            return;
        }
    }
}

