/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Warps
 *  com.earth2me.essentials.commands.WarpNotFoundException
 *  net.ess3.api.InvalidWorldException
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package Util;

import com.earth2me.essentials.Warps;
import com.earth2me.essentials.commands.WarpNotFoundException;
import main.RollbackAPI;
import main.dTools;
import net.ess3.api.InvalidWorldException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtil {
    public static void CheckInventoryItems(Player plr) {
        PlayerInventory inv = plr.getInventory();
        InventoryUtil.CheckInventoryItems((Inventory)inv, Material.POTION, plr);
    }

    public static void CheckInventoryItems(Inventory inv, Material type, Player plr) {
        ItemStack[] items = inv.getContents();
        boolean wasLegal = true;
        for (int i = 0; i < items.length; ++i) {
            ItemStack is = items[i];
            if (is == null || is.getType() == type || is.getType() == Material.GLASS_BOTTLE || is.getType() == Material.IRON_INGOT || is.getType() == Material.GOLD_INGOT) continue;
            wasLegal = false;
        }
        ItemStack is = null;
        is = plr.getInventory().getBoots();
        if (is != null || (is = plr.getInventory().getChestplate()) != null || (is = plr.getInventory().getHelmet()) != null || (is = plr.getInventory().getLeggings()) != null) {
            wasLegal = false;
        }
        if (!wasLegal) {
            World world = plr.getWorld();
            Location loc = world.getSpawnLocation();
            Warps warps = dTools.getES().getWarps();
            try {
                loc = RollbackAPI.isWithinRegion(plr.getLocation(), "B_Arena", plr) ? warps.getWarp("B_Arena") : warps.getWarp("B_Arena_2");
            }
            catch (WarpNotFoundException e) {
                e.printStackTrace();
            }
            catch (InvalidWorldException e) {
                e.printStackTrace();
            }
            if (loc.equals((Object)null)) {
                plr.sendMessage("Something went very wrong. Could not locate warp name (spawn!, ask an admin for help!");
                return;
            }
            plr.teleport(loc);
            plr.sendMessage((Object)ChatColor.RED + "Only specific items are allowed in the Bending Arenas!!  You have been returned to spawn!");
            plr.sendMessage((Object)ChatColor.RED + "The only legal items are:  Water Bottles, Iron Ignots & Gold Ignots");
        }
    }
}

