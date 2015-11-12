/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.milkbowl.vault.permission.Permission
 *  org.apache.commons.lang.Validate
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package Commands;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import main.dTools;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class RandomDropsForPlayers {
    public void RandomDrops() {
    }

    public static boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!sender.hasPermission("dtools.lottery")) {
            sender.sendMessage("Sorry you do not have permission to use the lottery system!");
            return false;
        }
        RegisteredServiceProvider rsp = dTools.plugin.getServer().getServicesManager().getRegistration((Class)Permission.class);
        Permission perms = (Permission)rsp.getProvider();
        if (perms == null) {
            System.out.println("Warning: perms wasn't found!");
        }
        if (args.length <= 0) {
            sender.sendMessage("Lottery System - Drops items for players @ drop parties / lotteries");
            File conf = new File("plugins/dTools/config.yml");
            if (!conf.exists()) {
                System.out.println("dTools:  Warning Configuration File Not Found! /plugins/dTools/config.yml");
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
            Boolean sameList = config.getBoolean("Rewards.UseSameList");
            Boolean BL_Mode = config.getBoolean("Rewards.BlackListMode");
            if (BL_Mode.booleanValue()) {
                sender.sendMessage((Object)ChatColor.YELLOW + "BLACKLIST" + (Object)ChatColor.RESET + " Mode enabled, items on the DP/Lottery list will NEVER drop.");
            } else {
                sender.sendMessage((Object)ChatColor.YELLOW + " WHITELIST" + (Object)ChatColor.RESET + " Mode enabled, only items on the DP/Lottery list will be used.");
            }
            if (sameList.booleanValue()) {
                sender.sendMessage("Rewards will use the same list for Drop Parties & Lotteries");
            } else {
                sender.sendMessage("Rewards will use separate lists for Drop Parties & Lotteries");
            }
            return true;
        }
        if (args[0].contains((CharSequence)"execute")) {
            Random rand = new Random();
            final Collection<? extends Player> players = (Collection<? extends Player>)Bukkit.getOnlinePlayers();
            for (Player player : players) {
                if (dTools.getLotto_List() == null) {
                    sender.sendMessage("something went wrong... LottoList is empty!");
                    return false;
                }
                String random = dTools.getLotto_List().get(rand.nextInt(dTools.getLotto_List().size()));
                ItemStack reward = new ItemStack(Material.getMaterial((String)random));
                if (reward == null) {
                    System.out.println("[dTools]: WARNING Item: " + random + " was not a valid material!  Check config.yml!");
                    continue;
                }
                player.getInventory().addItem(new ItemStack[]{reward});
            }
        }
        return true;
    }

    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull((Object)sender, (String)"Sender cannot be null");
        Validate.notNull((Object)args, (String)"Arguments cannot be null");
        Validate.notNull((Object)alias, (String)"Alias cannot be null");
        return ImmutableList.of();
    }
}

