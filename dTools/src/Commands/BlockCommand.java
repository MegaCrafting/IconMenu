/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package Commands;

import java.io.PrintStream;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockCommand {
    public static boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage((Object)ChatColor.RED + "Only players can use this command!");
            return false;
        }
        Player player = (Player)sender;
        try {
            int amountOfDiamonds = 0;
            int amountOfEmeralds = 0;
            int amountOfIron = 0;
            int amountOfGold = 0;
            int amountOfGlowstone = 0;
            int coal = 0;
            int redstone = 0;
            int lapis = 0;
            int itemsChanged = 0;
            for (ItemStack is : player.getInventory().getContents()) {
                if (is == null) continue;
                if (is.getType() == Material.DIAMOND) {
                    player.getInventory().remove(is);
                    amountOfDiamonds+=is.getAmount();
                }
                if (is.getType() == Material.EMERALD) {
                    amountOfEmeralds+=is.getAmount();
                    player.getInventory().remove(is);
                }
                if (is.getType() == Material.IRON_INGOT) {
                    player.getInventory().remove(is);
                    amountOfIron+=is.getAmount();
                }
                if (is.getType() == Material.GLOWSTONE_DUST) {
                    amountOfGlowstone+=is.getAmount();
                    player.getInventory().remove(is);
                }
                if (is.getType() == Material.GOLD_INGOT) {
                    player.getInventory().remove(is);
                    amountOfGold+=is.getAmount();
                }
                if (is.getType() == Material.COAL) {
                    player.getInventory().remove(is);
                    coal+=is.getAmount();
                }
                if (is.getType() == Material.REDSTONE) {
                    redstone+=is.getAmount();
                    player.getInventory().remove(is);
                }
                if (is.getType() != Material.INK_SACK) continue;
                ItemMeta im = is.getItemMeta();
                if (is.getDurability() == DyeColor.BLUE.getDyeData()) {
                    player.getInventory().remove(is);
                    lapis+=is.getAmount();
                    continue;
                }
                System.out.println("none of that shit worked.");
            }
            player.updateInventory();
            itemsChanged = amountOfDiamonds + amountOfEmeralds + amountOfGlowstone + amountOfGold + amountOfIron + coal + redstone + lapis;
            int diamondsToTransform = amountOfDiamonds / 9;
            int diamondOverflow = amountOfDiamonds % 9;
            int emeraldsToTransform = amountOfEmeralds / 9;
            int emeraldsOverflow = amountOfEmeralds % 9;
            int ironToTransform = amountOfIron / 9;
            int ironOverflow = amountOfIron % 9;
            int goldToTransform = amountOfGold / 9;
            int goldOverflow = amountOfGold % 9;
            int glowstoneToTransform = amountOfGlowstone / 4;
            int glowstoneOverflow = amountOfGlowstone % 4;
            int rT = redstone / 9;
            int rO = redstone % 9;
            int lT = lapis / 9;
            int lO = lapis % 9;
            int cT = coal / 9;
            int cO = coal % 9;
            ItemStack[] arritemStack = new ItemStack[10];
            arritemStack[0] = new ItemStack(diamondsToTransform > 0 ? Material.DIAMOND_BLOCK : Material.AIR, diamondsToTransform);
            arritemStack[1] = new ItemStack(emeraldsToTransform > 0 ? Material.EMERALD_BLOCK : Material.AIR, emeraldsToTransform);
            arritemStack[2] = new ItemStack(diamondOverflow > 0 ? Material.DIAMOND : Material.AIR, diamondOverflow);
            arritemStack[3] = new ItemStack(emeraldsOverflow > 0 ? Material.EMERALD : Material.AIR, emeraldsOverflow);
            arritemStack[4] = new ItemStack(ironToTransform > 0 ? Material.IRON_BLOCK : Material.AIR, ironToTransform);
            arritemStack[5] = new ItemStack(goldToTransform > 0 ? Material.GOLD_BLOCK : Material.AIR, goldToTransform);
            arritemStack[6] = new ItemStack(glowstoneToTransform > 0 ? Material.GLOWSTONE : Material.AIR, glowstoneToTransform);
            arritemStack[7] = new ItemStack(ironOverflow > 0 ? Material.IRON_INGOT : Material.AIR, ironOverflow);
            arritemStack[8] = new ItemStack(goldOverflow > 0 ? Material.GOLD_INGOT : Material.AIR, goldOverflow);
            arritemStack[9] = new ItemStack(glowstoneOverflow > 0 ? Material.GLOWSTONE_DUST : Material.AIR, glowstoneOverflow);
            player.getInventory().addItem(arritemStack);
            ItemStack[] arritemStack2 = new ItemStack[1];
            arritemStack2[0] = new ItemStack(rT > 0 ? Material.REDSTONE_BLOCK : Material.AIR, rT);
            player.getInventory().addItem(arritemStack2);
            ItemStack[] arritemStack3 = new ItemStack[1];
            arritemStack3[0] = new ItemStack(lT > 0 ? Material.LAPIS_BLOCK : Material.AIR, lT);
            player.getInventory().addItem(arritemStack3);
            ItemStack[] arritemStack4 = new ItemStack[1];
            arritemStack4[0] = new ItemStack(cT > 0 ? Material.COAL_BLOCK : Material.AIR, cT);
            player.getInventory().addItem(arritemStack4);
            ItemStack[] arritemStack5 = new ItemStack[1];
            arritemStack5[0] = new ItemStack(rO > 0 ? Material.REDSTONE : Material.AIR, rO);
            player.getInventory().addItem(arritemStack5);
            ItemStack ilapis = new ItemStack(lO > 0 ? Material.INK_SACK : Material.AIR, lO);
            ilapis.setDurability((short)DyeColor.BLUE.getDyeData());
            player.getInventory().addItem(new ItemStack[]{ilapis});
            ItemStack[] arritemStack6 = new ItemStack[1];
            arritemStack6[0] = new ItemStack(cO > 0 ? Material.COAL : Material.AIR, cO);
            player.getInventory().addItem(arritemStack6);
            if ((itemsChanged-=diamondOverflow + emeraldsOverflow + ironOverflow + goldOverflow + glowstoneOverflow + rO + cO + lO) > 0) {
                player.sendMessage((Object)ChatColor.AQUA + "Converted (" + itemsChanged + ") items into " + itemsChanged / 9 + " blocks!");
            } else {
                player.sendMessage((Object)ChatColor.AQUA + "You did not have enough items to convert to blocks!");
            }
            player.updateInventory();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

