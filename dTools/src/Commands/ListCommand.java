/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.milkbowl.vault.permission.Permission
 *  org.apache.commons.lang.Validate
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.defaults.VanillaCommand
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package Commands;

import com.google.common.collect.ImmutableList;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import main.dTools;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.VanillaCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class ListCommand
extends VanillaCommand {
    public ListCommand() {
        super("list");
        this.description = "Lists all online players";
        this.usageMessage = "/list";
        this.setPermission("dTools.listcommand");
    }

    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        RegisteredServiceProvider rsp = dTools.plugin.getServer().getServicesManager().getRegistration((Class)Permission.class);
        Permission perms = (Permission)rsp.getProvider();
        if (perms == null) {
            System.out.println("Warning: perms wasn't found!");
        }
        StringBuilder online = new StringBuilder();
        final Collection<? extends Player> players = (Collection<? extends Player>)Bukkit.getOnlinePlayers();
        final ImmutableList<String> adminRanks = (ImmutableList<String>)ImmutableList.of("owner", "coowner", "manager", "admin", "megaadmin", "coder", "megaavataradmin");
        final ImmutableList<String> modRanks = (ImmutableList<String>)ImmutableList.of("mod", "moderator", "jrmoderator", "jrmegaavatarmod", "megaavatarmod", "modmega", "modiron", "modgold", "modemerald", "modvip", "moddiamond", "emeraldmoderator", new String[] { "modmaster" });
        final ImmutableList<String> helperRanks = (ImmutableList<String>)ImmutableList.of("helper", "jrhelper", "megaavatarhelper", "jrmegaavatarhelper", "helpermega", "helperiron", "helpergold", "helperemerald", "helpervip,helperdiamond", "helpermaster");
        final ImmutableList<String> benderRanks = (ImmutableList<String>)ImmutableList.of("water", "air", "fire", "earth", "chi", "notchosen", "default");
        final ImmutableList<String> avatarRanks = (ImmutableList<String>)ImmutableList.of("avatar", "megaavatar");
        final ImmutableList<String> donorRanks = (ImmutableList<String>)ImmutableList.of("vip", "gold", "emerald", "diamond", "mega", "iron", "god", "master", "sheep", "mooshroom", "zombie", "creeper", new String[] { "wither", "scarecrow", "chameleon" });
        String pName = "";
        String donorList = "";
        String avatarList = "";
        String helperList = "";
        String modList = "";
        String adminList = "";
        String playerList = "";
        Boolean added = false;
        for (final Player player : players) {
            if (sender instanceof Player && !((Player)sender).canSee(player)) {
                continue;
            }
            String o = perms.getPrimaryGroup(player);
            o = o.toLowerCase();
            pName = player.getDisplayName();
            for (int i = 0; i < pName.length(); ++i) {
                final String c = pName.substring(i);
                final int z = c.indexOf("]");
                if (z > 0) {
                    final String deleteme = pName = pName.substring(z + 1);
                    break;
                }
            }
            for (int i = 0; i < pName.length(); ++i) {
                final String c = pName.substring(i);
                final int z = c.indexOf("[");
                if (z > 0) {
                    final String deleteme = pName = pName.substring(0, z);
                    break;
                }
            }
            if (!player.getDisplayName().contains(player.getName())) {
                pName = String.valueOf(pName) + ChatColor.RESET + "(" + player.getName() + ")";
            }
            pName = String.valueOf(pName) + ", ";
            pName = pName.replace("[", "");
            for (final String r : adminRanks) {
                if (r.equalsIgnoreCase(o)) {
                    adminList = String.valueOf(adminList) + pName;
                    added = true;
                }
            }
            for (final String r : modRanks) {
                if (r.equalsIgnoreCase(o)) {
                    modList = String.valueOf(modList) + pName;
                    added = true;
                }
            }
            for (final String r : helperRanks) {
                if (r.equalsIgnoreCase(o)) {
                    helperList = String.valueOf(helperList) + pName;
                    added = true;
                }
            }
            for (final String r : avatarRanks) {
                if (r.equalsIgnoreCase(o)) {
                    avatarList = String.valueOf(avatarList) + pName;
                    added = true;
                }
            }
            for (final String r : donorRanks) {
                if (r.equalsIgnoreCase(o)) {
                    donorList = String.valueOf(donorList) + pName;
                    added = true;
                }
            }
            for (final String r : benderRanks) {
                if (r.equalsIgnoreCase(o)) {
                    playerList = String.valueOf(playerList) + pName;
                    added = true;
                }
            }
            if (!added) {
                playerList = String.valueOf(playerList) + pName;
            }
            added = false;
        }
        if (adminList != "") {
            online.append("- " + (Object)ChatColor.GOLD + "Administrators\n" + (Object)ChatColor.RESET + adminList + "\n-\n");
        }
        if (modList != "") {
            online.append("- " + (Object)ChatColor.AQUA + "Moderators\n" + (Object)ChatColor.RESET + modList + "\n-\n");
        }
        if (helperList != "") {
            online.append("- " + (Object)ChatColor.GREEN + "Helpers\n" + (Object)ChatColor.RESET + helperList + "\n-\n");
        }
        if (avatarList != "") {
            online.append("- " + (Object)ChatColor.YELLOW + "Avatars\n" + (Object)ChatColor.RESET + avatarList + "\n-\n");
        }
        if (donorList != "") {
            online.append("- " + (Object)ChatColor.YELLOW + "Donors\n" + (Object)ChatColor.RESET + donorList + "\n-\n");
        }
        if (playerList != "") {
            online.append("- " + (Object)ChatColor.GRAY + "Players\n" + (Object)ChatColor.RESET + playerList + "\n-\n");
        }
        sender.sendMessage("- There are " + players.size() + "/" + Bukkit.getMaxPlayers() + " players online:\n" + online.toString());
        return true;
    }

    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull((Object)sender, (String)"Sender cannot be null");
        Validate.notNull((Object)args, (String)"Arguments cannot be null");
        Validate.notNull((Object)alias, (String)"Alias cannot be null");
        return ImmutableList.of();
    }
}

