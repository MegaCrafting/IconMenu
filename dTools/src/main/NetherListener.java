/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.dsh105.echopet.api.EchoPetAPI
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  com.earth2me.essentials.Warps
 *  com.earth2me.essentials.commands.WarpNotFoundException
 *  com.sk89q.worldedit.MaxChangedBlocksException
 *  com.sk89q.worldedit.world.DataException
 *  de.slikey.effectlib.EffectManager
 *  de.slikey.effectlib.effect.ConeEffect
 *  de.slikey.effectlib.effect.WarpEffect
 *  de.slikey.effectlib.util.ParticleEffect
 *  net.ess3.api.InvalidWorldException
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.SpawnerSpawnEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerAnimationEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerPortalEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerToggleSneakEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.Team
 *  pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI
 */
package main;

import Util.InventoryUtil;
import com.dsh105.echopet.api.EchoPetAPI;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.Warps;
import com.earth2me.essentials.commands.WarpNotFoundException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.world.DataException;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import main.RollbackAPI;
import main.dTools;
import net.ess3.api.InvalidWorldException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class NetherListener
implements Listener {
    public dTools plugin;
    public Boolean firemsg = true;

    public NetherListener(dTools dtools) {
        this.plugin = dtools;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player plr = event.getPlayer();
        Item itm = event.getItem();
        if (RollbackAPI.isWithinRegion(plr.getLocation(), "B_Arena_2", plr) || RollbackAPI.isWithinRegion(plr.getLocation(), "B_Arena", plr) || RollbackAPI.isWithinRegion(plr.getLocation(), "b_arena_4", plr)) {
            itm.remove();
            event.setCancelled(true);
        }
    }

    public Boolean isNumb(char character) {
        char[] nums = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int a = 0; a < nums.length; ++a) {
            if (nums[a] != character) continue;
            return true;
        }
        return false;
    }

    public Boolean isIP(String message) {
        int dotCount = 0;
        String rebuild = null;
        Boolean isIP = true;
        String[] ext = new String[]{"com", "net", "org", "us", "ca"};
        int firstDot = 0;
        int secondDot = 0;
        int thirdDot = 0;
        int fourthDot = 0;
        int numCount = 0;
        if (message.contains((CharSequence)"...")) {
            return false;
        }
        for (int i = 0; i < message.length(); ++i) {
            char chr;
            int j;
            char c = message.charAt(i);
            if (c == '.' || c == ',') {
                ++dotCount;
                if (firstDot == 0) {
                    firstDot = i;
                } else if (secondDot == 0) {
                    secondDot = i;
                } else if (thirdDot == 0) {
                    thirdDot = i;
                } else if (fourthDot == 0) {
                    fourthDot = i;
                }
            }
            if (dotCount == 3) {
                isIP = true;
                for (int j2 = thirdDot; j2 >= 0; --j2) {
                    char chr2 = message.charAt(j2);
                    if (this.isNumb(chr2).booleanValue()) {
                        rebuild = String.valueOf(rebuild) + chr2;
                        ++numCount;
                        continue;
                    }
                    if (chr2 == ' ') continue;
                    if (chr2 == '.') {
                        rebuild = String.valueOf(rebuild) + ".";
                        continue;
                    }
                    if (j2 <= firstDot) break;
                    isIP = false;
                    rebuild = null;
                    break;
                }
                if (rebuild != null) {
                    String reverse = null;
                    for (j = 0; j < rebuild.length(); ++j) {
                        reverse = String.valueOf(reverse) + rebuild.charAt(j);
                    }
                    rebuild = reverse;
                    if (numCount > 0) {
                        return true;
                    }
                    System.out.println("[dTools] Warning! Possible hostname found in chat.. " + rebuild);
                    for (int z = 0; z < ext.length; ++z) {
                        String testext = ext[z];
                        if (!message.contains((CharSequence)testext)) continue;
                        return true;
                    }
                    return false;
                }
            }
            if (dotCount == 2 && secondDot != 0 && i == secondDot) {
                String next3 = "";
                for (j = secondDot + 1; j < message.length(); ++j) {
                    chr = message.charAt(j);
                    if (this.isNumb(chr).booleanValue() || chr == ' ' || chr == '.' || chr == ',') break;
                    next3 = String.valueOf(next3) + chr;
                }
                for (j = 0; j < ext.length - 1; ++j) {
                    if (!ext[j].equalsIgnoreCase(next3)) continue;
                    return true;
                }
            }
            if (dotCount != 1 || firstDot == 0 || i != firstDot) continue;
            String next3 = "";
            for (j = firstDot + 1; j < message.length(); ++j) {
                chr = message.charAt(j);
                if (this.isNumb(chr).booleanValue() || chr == ' ' || chr == '.' || chr == ',') break;
                next3 = String.valueOf(next3) + chr;
            }
            for (j = 0; j < ext.length - 1; ++j) {
                if (!ext[j].equalsIgnoreCase(next3)) continue;
                return true;
            }
        }
        return false;
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        boolean matches;
        Player sender = e.getPlayer();
        Set r = e.getRecipients();
        Boolean isDovi = false;
        Boolean isRed = false;
        Boolean dropMsg = false;
        ArrayList<UUID> ignoreme = new ArrayList<UUID>();
        UUID red_shades = UUID.fromString("8dd72c0d-7096-4f38-9d56-c346968e1c87");
        UUID dovi = UUID.fromString("619d3a2b-6ffa-43dc-ab59-b17e64b1d0ab");
        UUID dovi2 = UUID.fromString("caf1f6f5-cd88-46f7-887b-02afa7ba83f6");
        if (sender.hasPermission("dtools.dropchat") && !sender.isOp()) {
            for (Player plr : Bukkit.getOnlinePlayers()) {
                if (plr.hasPermission("dtools.dropchat")) continue;
                r.remove((Object)plr);
            }
        }
        if (!(!sender.hasPermission("dtools.hebrew") || sender.isOp() || (matches = e.getMessage().toUpperCase().matches(".*[A-Z].*")))) {
            for (final Player plr2 : Bukkit.getOnlinePlayers()) {
                if (!plr2.hasPermission("dtools.hebrew")) {
                    r.remove(plr2);
                }
                r.remove(plr2);
            }
        }
        if (!(!this.isIP(e.getMessage()).booleanValue() || e.getMessage().toLowerCase().contains((CharSequence)"megacrafting.com") || sender.hasPermission("dtools.urls"))) {
            System.out.println("[dTools] WARNING: User tried to post an IP or link in chat!  Blocking.!! " + sender.getName() + " " + e.getMessage());
            dropMsg = true;
        }
        if (e.getMessage().contains((CharSequence)"http") && !sender.hasPermission("dtools.urls")) {
            System.out.println("[dTools] WARNING: User tried to post a URL in chat!! Blocking!!! " + sender.getName() + " " + e.getMessage());
            dropMsg = true;
        }
        if (sender.getUniqueId().equals(red_shades)) {
            ignoreme.add(dovi);
            ignoreme.add(dovi2);
        }
        if (sender.getUniqueId().equals(dovi) || sender.getUniqueId().equals(dovi2)) {
            ignoreme.add(red_shades);
        }
        if (ignoreme != null) {
            for (Player plr : Bukkit.getServer().getOnlinePlayers()) {
                if (dropMsg.booleanValue()) {
                    if (plr.hasPermission("dtools.urls")) {
                        plr.sendMessage((Object)ChatColor.RED + "WARNING: " + sender.getName() + " Tried to post a link in chat! " + e.getMessage());
                    }
                    if (plr != sender) {
                        r.remove((Object)plr);
                    }
                }
                if (plr.getUniqueId() == sender.getUniqueId()) continue;
                for (int i = 0; i < ignoreme.size(); ++i) {
                    UUID ignoree = (UUID)ignoreme.get(i);
                    if (!plr.getUniqueId().equals(ignoree)) continue;
                    r.remove((Object)plr);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        Player player = event.getPlayer();
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        String netherWorld = config.getString("General.NetherWorld");
        String spawnWorld = config.getString("General.SpawnWorldName");
        int maxY = config.getInt("General.MaxLegalY");
        ArrayList<String> ignoreRegion = new ArrayList<String>();
        ignoreRegion.add("b_arena_area");
        ignoreRegion.add("b_arena_2_area");
        ignoreRegion.add("b_arena_4_area");
        ignoreRegion.add("not_chosen_area");
        Location loc = event.getBlock().getLocation();
        Boolean isArena = false;
        World wld = loc.getWorld();
        if ((wld.getName().equalsIgnoreCase(spawnWorld) || wld.getName().equalsIgnoreCase(netherWorld) && loc.getY() >= (double)maxY) && !player.isOp()) {
            for (String rname : ignoreRegion) {
                if (!RollbackAPI.isWithinRegion(loc, rname, player)) continue;
                isArena = true;
            }
            if (!isArena.booleanValue()) {
                player.sendMessage("You can not break blocks here!!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        Player player = event.getPlayer();
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        String netherWorld = config.getString("General.NetherWorld");
        String spawnWorld = config.getString("General.SpawnWorldName");
        int maxY = config.getInt("General.MaxLegalY");
        Location loc = event.getBlock().getLocation();
        World wld = loc.getWorld();
        if ((wld.getName().equalsIgnoreCase(spawnWorld) || wld.getName().equalsIgnoreCase(netherWorld) && loc.getY() >= (double)maxY) && !player.isOp()) {
            player.sendMessage("You can not place blocks here!!");
            event.setCancelled(true);
            return;
        }
        if (player.hasPermission("dtools.chests")) {
            return;
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void disguisedDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (dTools.DC == null) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (dTools.DC.isDisguised(player)) {
            dTools.DC.undisguisePlayer(player);
            player.sendMessage("Taking damage has caused your disguise to be removed!");
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerSwing(PlayerAnimationEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }
        if (dTools.DC == null) {
            return;
        }
        Player player = event.getPlayer();
        if (dTools.DC.isDisguised(player)) {
            dTools.DC.undisguisePlayer(player);
            player.sendMessage("Attacking has caused your disguise to be removed!");
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }
        if (dTools.DC == null) {
            return;
        }
        Player player = event.getPlayer();
        if (dTools.DC.isDisguised(player)) {
            dTools.DC.undisguisePlayer(player);
            player.sendMessage("Sneaking has caused your disguise to be removed!");
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().toLowerCase();
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        String netherWorld = config.getString("General.NetherWorld");
        String spawnWorld = config.getString("General.SpawnWorldName");
        int maxY = config.getInt("General.MaxLegalY");
        if ((cmd.startsWith("/sethome") || cmd.startsWith("/essentials:sethome")) && dTools.restrictedWorlds.contains(player.getWorld().getName())) {
            if (player.getWorld().getName().equalsIgnoreCase(netherWorld) && player.getLocation().getY() < (double)maxY) {
                return;
            }
            player.sendMessage("You can not set homes in this world!");
            event.setCancelled(true);
            return;
        }
        if (cmd.startsWith("/home") || cmd.startsWith("/essentials:home")) {
            Essentials ES = dTools.getES();
            User user = ES.getUser(player);
            List homes = user.getHomes();
            for (int i = 0; i < homes.size(); ++i) {
                String name = (String)homes.get(i);
                Location loc = null;
                try {
                    loc = user.getHome(name);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (loc.equals((Object)null) && loc == null || !dTools.restrictedWorlds.contains(loc.getWorld().getName())) continue;
                if (loc.getWorld().getName().equalsIgnoreCase(netherWorld) && loc.getY() < (double)maxY) {
                    return;
                }
                player.sendMessage("Your home: " + name + " was set in a restricted world.  It has been removed.");
                try {
                    user.delHome(name);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                event.setCancelled(true);
                return;
            }
        }
        if (dTools.DC != null && (cmd.startsWith("/d ") || cmd.startsWith("/disguise ") || cmd.startsWith("/bd "))) {
            if (dTools.DC.isDisguised(player)) {
                return;
            }
            if (dTools.cooldowns.containsKey((Object)player)) {
                Long cooldownadded = System.currentTimeMillis();
                Long currenttime = System.currentTimeMillis();
                if (dTools.cooldowns.containsKey((Object)player)) {
                    cooldownadded = dTools.cooldowns.get((Object)player);
                }
                long seconds = 0;
                if (currenttime != cooldownadded) {
                    seconds = (currenttime - cooldownadded) / 1000;
                }
                if (seconds < 300) {
                    Long left = 300 - seconds;
                    Long mins = left / 60;
                    if (mins >= 1) {
                        left = left - mins * 60;
                    }
                    String unit = "minutes";
                    String unit2 = "seconds";
                    if (mins >= 1) {
                        if (mins == 1) {
                            unit = "minute";
                        }
                        if (seconds == 1) {
                            unit2 = "second";
                        }
                        player.sendMessage("You may not disguise for another: " + mins + " " + unit + " and " + left + " " + unit2 + ".");
                    } else {
                        if (seconds == 1) {
                            unit = "second";
                        }
                        player.sendMessage("You may not disguise for another: " + left + " " + unit2 + ".");
                    }
                    event.setCancelled(true);
                    return;
                }
                dTools.cooldowns.remove((Object)player);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        Entity spawned = event.getEntity();
        int count = 0;
        ArrayList<EntityType> monsters = new ArrayList<EntityType>();
        monsters.add(EntityType.BLAZE);
        monsters.add(EntityType.CAVE_SPIDER);
        monsters.add(EntityType.CREEPER);
        monsters.add(EntityType.ENDERMAN);
        monsters.add(EntityType.ENDERMITE);
        monsters.add(EntityType.IRON_GOLEM);
        monsters.add(EntityType.MAGMA_CUBE);
        monsters.add(EntityType.PIG_ZOMBIE);
        monsters.add(EntityType.SILVERFISH);
        monsters.add(EntityType.SKELETON);
        monsters.add(EntityType.SLIME);
        monsters.add(EntityType.SPIDER);
        monsters.add(EntityType.WITCH);
        monsters.add(EntityType.ZOMBIE);
        if (monsters.contains((Object)spawned.getType())) {
            Entity[] existing = spawned.getWorld().getChunkAt(spawned.getLocation()).getEntities();
            for (int i = 0; i < existing.length; ++i) {
                Entity checkme = existing[i];
                if (monsters.contains((Object)checkme.getType())) {
                    ++count;
                }
                if (count < 60) continue;
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player plr = event.getPlayer();
        if ((plr.getInventory().getItemInHand().getType() == Material.MONSTER_EGG || plr.getInventory().getItemInHand().getType() == Material.MONSTER_EGGS) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block blk = event.getClickedBlock();
            if (!(blk.getType() != Material.MOB_SPAWNER || event.getPlayer().isOp())) {
                plr.sendMessage((Object)ChatColor.RED + "Sorry you can not use spawn eggs to modify spawner types!");
                event.setCancelled(true);
            } else {
                System.out.println("Player " + event.getPlayer().getName() + " Changed a spawner at: " + plr.getLocation().getX() + " x, " + plr.getLocation().getY() + " y," + plr.getLocation().getZ() + " z");
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerMove(PlayerMoveEvent event) {
        String closedworld;
        Location ploc;
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        Player player = event.getPlayer();
        String aPortal = config.getString("General.ArenaPortalRegion");
        String aWarp = config.getString("General.ArenaWarpName");
        String spawnWorld = config.getString("General.SpawnWorldName");
        if (aPortal != null && aWarp != null && player.getWorld().getName().equalsIgnoreCase(spawnWorld) && RollbackAPI.isWithinRegion(player.getLocation(), aPortal, player)) {
            Warps warps = dTools.getES().getWarps();
            try {
                Location arena = warps.getWarp(aWarp);
                player.teleport(arena);
                player.sendMessage("Teleporting you to the Bending Arena");
            }
            catch (WarpNotFoundException | InvalidWorldException e) {
                System.out.println("Could not locate a warp called: " + aWarp);
                e.printStackTrace();
            }
        }
        if (config.getString("General.BendingArena") == "true" && player.getWorld() == Bukkit.getWorld((String)"spawn")) {
            ploc = player.getLocation();
            if (player.getActivePotionEffects() != null && !player.getActivePotionEffects().isEmpty() && RollbackAPI.isWithinRegion(ploc, "Pyramid", player)) {
                for (PotionEffect pot : player.getActivePotionEffects()) {
                    player.removePotionEffect(pot.getType());
                }
            }
            dTools.isNearArena = RollbackAPI.isWithinRegion(ploc, "b_arena", player);
            dTools.isNearArena2 = RollbackAPI.isWithinRegion(ploc, "B_Arena_2", player);
            dTools.isNearArena4 = RollbackAPI.isWithinRegion(ploc, "b_arena_4", player);
            if ((RollbackAPI.isWithinRegion(ploc, "b_arena_area", player) || RollbackAPI.isWithinRegion(ploc, "B_Arena_2_Area", player) || RollbackAPI.isWithinRegion(ploc, "b_arena_area_4", player)) && !player.isOp()) {
                InventoryUtil.CheckInventoryItems(player);
            }
            dTools.isArenaOccupied = false;
            dTools.isArenaOccupied2 = false;
            dTools.isArenaOccupied4 = false;
            for (Player plr : Bukkit.getOnlinePlayers()) {
                if (RollbackAPI.isWithinRegion(plr.getLocation(), "b_arena_area", plr)) {
                    dTools.isArenaOccupied = true;
                }
                if (RollbackAPI.isWithinRegion(plr.getLocation(), "B_Arena_2_Area", plr)) {
                    dTools.isArenaOccupied2 = true;
                }
                if (!RollbackAPI.isWithinRegion(ploc, "B_Arena_4_Area", plr)) continue;
                dTools.isArenaOccupied4 = true;
            }
            if (dTools.isNearArena && !dTools.isArenaOccupied) {
                if (!dTools.isArenaReset) {
                    dTools.isArenaReset = true;
                    dTools.ArenaTimer = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)dTools.plugin, new Runnable(){

                        @Override
                        public void run() {
                            System.out.println("INFO: Auto-Resetting the bending Arena");
                            dTools.isArenaReset = true;
                            try {
                                RollbackAPI.ReloadRegion(null);
                            }
                            catch (MaxChangedBlocksException e) {
                                e.printStackTrace();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            catch (DataException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else if (dTools.isArenaOccupied && dTools.isNearArena && dTools.isArenaReset) {
                    dTools.isArenaReset = false;
                }
            }
            if (dTools.isNearArena2 && !dTools.isArenaOccupied2) {
                if (!dTools.isArenaReset2) {
                    dTools.isArenaReset2 = true;
                    dTools.ArenaTimer2 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)dTools.plugin, new Runnable(){

                        @Override
                        public void run() {
                            System.out.println("INFO: Auto-Resetting the bending Arena 2 ");
                            dTools.isArenaReset2 = true;
                            try {
                                RollbackAPI.ReloadRegion(null);
                            }
                            catch (MaxChangedBlocksException e) {
                                e.printStackTrace();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            catch (DataException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else if (dTools.isArenaOccupied2 && dTools.isNearArena2 && dTools.isArenaReset2) {
                    dTools.isArenaReset2 = false;
                }
            }
            if (dTools.isNearArena4 && !dTools.isArenaOccupied4) {
                if (!dTools.isArenaReset4) {
                    dTools.isArenaReset4 = true;
                    dTools.ArenaTimer4 = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)dTools.plugin, new Runnable(){

                        @Override
                        public void run() {
                            System.out.println("INFO: Auto-Resetting the Practice Arena ");
                            dTools.isArenaReset4 = true;
                            try {
                                RollbackAPI.ReloadPractice(null);
                            }
                            catch (DataException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else if (dTools.isArenaOccupied4 && dTools.isNearArena4 && dTools.isArenaReset4) {
                    dTools.isArenaReset4 = false;
                }
            }
        }
        if (config.getString("General.BendingArena") == "true" && player.getWorld() == Bukkit.getWorld((String)"spawn")) {
            ploc = player.getLocation();
            dTools.isNearNotChosen = RollbackAPI.isWithinRegion(ploc, "not_chosen", player);
            RollbackAPI.isWithinRegion(ploc, "not_chosen_area", player);
            dTools.isNotChosenOccupied = false;
            for (Player plr : Bukkit.getOnlinePlayers()) {
                if (!RollbackAPI.isWithinRegion(plr.getLocation(), "not_chosen_area", plr)) continue;
                dTools.isNotChosenOccupied = true;
            }
            if (dTools.isNearNotChosen && !dTools.isNotChosenOccupied) {
                if (!dTools.isNotChosenReset) {
                    dTools.isNotChosenReset = true;
                    dTools.NotChosenTimer = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)dTools.plugin, new Runnable(){

                        @Override
                        public void run() {
                            System.out.println("INFO: Auto-Resetting the NotChosen Area");
                            dTools.isNotChosenReset = true;
                            try {
                                RollbackAPI.ReloadRegion(null);
                            }
                            catch (MaxChangedBlocksException e) {
                                e.printStackTrace();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            catch (DataException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } else if (dTools.isNotChosenOccupied && dTools.isNearNotChosen && dTools.isNotChosenReset) {
                dTools.isNotChosenReset = false;
            }
        }
        if (player.getWorld() == Bukkit.getWorld((String)"spawn") && RollbackAPI.isWithinRegion(player.getLocation(), "probending_floor", player) && ++dTools.Probending >= 120) {
            try {
                RollbackAPI.ReloadRegion(null);
            }
            catch (MaxChangedBlocksException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (DataException e) {
                e.printStackTrace();
            }
            dTools.Probending = 0;
        }
        if ((closedworld = config.getString("General.ClosedWorldName")) != null && player != null && player.getWorld().getName().equalsIgnoreCase(closedworld) && !player.isOp()) {
            player.sendMessage("WARNING!  This world is now CLOSED  sending you to spawn!");
            World spwn = Bukkit.getWorld((String)config.getString("General.NewWorldName"));
            Location spwnPt = spwn.getSpawnLocation();
            player.teleport(spwnPt);
        }
        if (player != null && player.getWorld() == Bukkit.getWorld((String)config.getString("General.SpawnWorldName")) && player.hasPermission("dtools.spawnportal") && config.getBoolean("General.EnableRandomSpawn")) {
            World wld = player.getWorld();
            String mainworld = config.getString("General.SpawnWorldName");
            if (wld.getName().equalsIgnoreCase(mainworld)) {
                String trigger = config.getString("General.PortalRegion");
                if (RollbackAPI.isWithinRegion(player.getLocation(), trigger, player)) {
                    Random rnd = new Random();
                    int posneg = rnd.nextInt(2) + 1;
                    int X = 0;
                    int Y = 250;
                    int Z = 0;
                    X = posneg > 1 ? 0 - rnd.nextInt(config.getInt("General.MaxX")) : rnd.nextInt(config.getInt("General.MaxX"));
                    posneg = rnd.nextInt(2) + 1;
                    Z = posneg > 1 ? 0 - rnd.nextInt(config.getInt("General.MaxZ")) : rnd.nextInt(config.getInt("General.MaxZ"));
                    World towld = Bukkit.getWorld((String)config.getString("General.MainWorldName"));
                    if (towld != null) {
                        boolean isOnLand = false;
                        Location teleportLocation = null;
                        while (!isOnLand) {
                            teleportLocation = new Location(towld, (double)X, (double)Y, (double)Z);
                            if (!teleportLocation.getBlock().getType().equals((Object)Material.AIR)) {
                                isOnLand = true;
                                if (!teleportLocation.getBlock().getType().equals((Object)Material.LAVA)) continue;
                                return;
                            }
                            --Y;
                        }
                        player.teleport(new Location(towld, teleportLocation.getX(), teleportLocation.getY() + 1.0, teleportLocation.getZ()));
                        player.sendMessage((Object)ChatColor.GOLD + "Entering the world... " + (Object)ChatColor.AQUA + " may your stay here be... Interesting...");
                    } else {
                        player.sendMessage("Sorry Teleportaion Failed!  Please Contact A Staff Member!");
                        System.out.println("[dTools] - Random portal was unable to find the Main World!  Check your config!");
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player plr = event.getPlayer();
        if (plr.hasPermission("MegaCraft.Admin") && !dTools.illegalBases.isEmpty()) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)dTools.plugin, new Runnable(){

                @Override
                public void run() {
                    plr.sendMessage("Warning!  an illegal Nether Glitch was detected!  Please check the following coords in the nether:");
                    for (String pname : dTools.illegalBases.keySet()) {
                        Location loc = dTools.illegalBases.get(pname);
                        int x = (int)loc.getX();
                        int y = (int)loc.getY();
                        int z = (int)loc.getZ();
                        int count = 1;
                        plr.sendMessage(String.valueOf(count) + ") " + pname + " at " + x + ", " + y + ", " + z);
                    }
                    plr.sendMessage("Use /dtools check - to view this list again.");
                    plr.sendMessage("Use /dtools check <number> - to teleport to the offending location");
                    plr.sendMessage("Use /dtools remove <number> - to remove the offense.");
                }
            }, 480);
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPortalCreate(PlayerPortalEvent event) {
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        Player player = event.getPlayer();
        World world = event.getTo().getWorld();
        Block blk = event.getTo().getBlock();
        String mainWorld = "world";
        if (config.getString("General.MainWorldName") != null) {
            mainWorld = config.getString("General.MainWorldName");
        }
        world.getName().equalsIgnoreCase(mainWorld);
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        Player player = event.getPlayer();
        if (config.getString("General.BendingArena") == "true") {
            if (event.getTo().getWorld().getName().equalsIgnoreCase("spawn") && RollbackAPI.isWithinRegion(event.getTo(), "Avatar_Spawn_1", player) && !player.isOp()) {
                event.setCancelled(true);
                player.sendMessage("You Can't Teleport There!");
            }
            if (Bukkit.getWorld((String)"spawn") == player.getWorld() && (RollbackAPI.isWithinRegion(event.getTo(), "b_arena_area", player) || RollbackAPI.isWithinRegion(event.getTo(), "B_Arena_2_Area", player) || RollbackAPI.isWithinRegion(event.getTo(), "b_arena_4_area", player))) {
                if (RollbackAPI.isWithinRegion(event.getTo(), "B_Arena_2_Area", player)) {
                    dTools.isArenaReset2 = false;
                }
                if (RollbackAPI.isWithinRegion(event.getTo(), "b_arena_area", player)) {
                    dTools.isArenaReset = false;
                }
                if (RollbackAPI.isWithinRegion(event.getTo(), "b_arena_4_area", player)) {
                    dTools.isArenaReset4 = false;
                }
                if (dTools.EP != null && dTools.EP.hasPet(player)) {
                    dTools.EP.removePet(player, true, false);
                    player.sendMessage("Your pet has been removed upon entering the bending arena.");
                }
                if (dTools.DC != null && dTools.DC.isDisguised(player)) {
                    dTools.DC.undisguisePlayer(player);
                    player.sendMessage("Your disguise has been removed upon entering the bending arena");
                }
            }
        }
        if (dTools.getEM() != null && player.hasPermission("dTools.warpeffect")) {
            ConeEffect effectfrom = new ConeEffect(dTools.getEM());
            effectfrom.setLocation(event.getFrom().add(0.0, 2.0, 0.0));
            effectfrom.lengthGrow = 0.0f;
            effectfrom.particle = ParticleEffect.SPELL_WITCH;
            effectfrom.iterations = 25;
            effectfrom.randomize = false;
            effectfrom.start();
            WarpEffect effect = new WarpEffect(dTools.getEM());
            effect.setEntity((Entity)player);
            effect.start();
        }
        String value = config.getString("General.NetherWorld");
        int maxY = config.getInt("General.MaxLegalY");
        if (value != null && event.getTo().getWorld().getName().equalsIgnoreCase(value) && event.getTo().getY() >= (double)maxY) {
            int x = (int)event.getTo().getX();
            int y = (int)event.getTo().getY();
            int z = (int)event.getTo().getZ();
            String coords = String.valueOf(x) + ", " + y + ", " + z;
            System.out.println("[dTools] WARNING!   Player: " + player.getName() + " teleported on top of the nether! Coords:" + coords);
            if (player.hasPermission("MegaCraft.Admin")) {
                player.sendMessage((Object)ChatColor.RED + "WARNING!  You have glitched on top of the nether.  Leave NOW!");
                dTools.illegalBases.put(player.getName(), event.getTo());
            }
            for (Player ops : Bukkit.getOnlinePlayers()) {
                if (!ops.isOp()) continue;
                ops.sendMessage("[dTools] Warning!:  Player " + player.getName() + " teleported on top of the nether! Coords:" + coords);
            }
        }
        value = config.getString("General.UseScoreboard");
        if (RollbackAPI.isWithinRegion(event.getTo(), "b_arena", player) && value.equalsIgnoreCase("true")) {
            Objective objective;
            System.out.println("Player in arena check 4 team");
            if (!dTools.team.getPlayers().contains((Object)player)) {
                player.sendMessage("DEBUG: adding you to the arena!");
                dTools.team.addPlayer((OfflinePlayer)player);
            }
            if ((objective = dTools.board.getObjective("showhealth")) == null) {
                objective = dTools.board.registerNewObjective("showhealth", "health");
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                objective.setDisplayName("/ 20");
            }
            for (OfflinePlayer p : dTools.team.getPlayers()) {
                p.getPlayer().sendMessage(String.valueOf(player.getName()) + "Has entered the Arena!");
            }
        } else if (dTools.team.getPlayers().contains((Object)player)) {
            for (OfflinePlayer p : dTools.team.getPlayers()) {
                p.getPlayer().sendMessage(String.valueOf(player.getName()) + "Has left the Arena!");
                player.sendMessage("DEBUG: removing you from the arena!");
            }
            dTools.team.removePlayer((OfflinePlayer)player);
        }
    }

}

