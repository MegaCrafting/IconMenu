/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.dsh105.echopet.api.EchoPetAPI
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  de.slikey.effectlib.EffectLib
 *  de.slikey.effectlib.EffectManager
 *  de.slikey.effectlib.effect.ConeEffect
 *  de.slikey.effectlib.util.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.MemorySection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.ScoreboardManager
 *  org.bukkit.scoreboard.Team
 *  pgDev.bukkit.DisguiseCraft.DisguiseCraft
 *  pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI
 */
package main;

import Commands.BlockCommand;
import Commands.Commandbroadcast;
import Commands.Commandpetcannon;
import Commands.FirstJoin;
import Commands.ListCommand;
import Commands.RandomDropsForPlayers;
import Util.Tools;
import com.dsh105.echopet.api.EchoPetAPI;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import main.Arena;
import main.NetherListener;
import main.dToolsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class dTools
extends JavaPlugin
implements Listener {
    public static HashMap<String, Location> illegalBases = new HashMap();
    public static HashMap<Player, Long> cooldowns = new HashMap();
    public static Boolean illegalReview = false;
    public static long time_step = 1;
    public static Logger log = Logger.getLogger("dTools");
    public static Map<String, String> Arenas = new HashMap<String, String>();
    public static Map<String, String> NetherExploit = new HashMap<String, String>();
    public static dTools plugin;
    public static int ArenaTimer;
    public static int Probending;
    public static boolean isNearArena;
    public static boolean isArenaReset;
    public static boolean isArenaOccupied;
    public static int ArenaTimer2;
    public static boolean isNearArena2;
    public static boolean isArenaReset2;
    public static boolean isArenaOccupied2;
    public static int ArenaTimer4;
    public static boolean isNearArena4;
    public static boolean isArenaReset4;
    public static boolean isArenaOccupied4;
    public static int NotChosenTimer;
    public static boolean isNearNotChosen;
    public static boolean isNotChosenReset;
    public static boolean isNotChosenOccupied;
    static ArrayList<dToolsPlayer> cooldown_list;
    static List<Material> containers;
    static List<EntityType> containers2;
    public static List<String> restrictedWorlds;
    public static boolean RegionFeatures;
    public final NetherListener listener = new NetherListener(plugin);
    private static EffectManager EM;
    public static String TriggerRegion;
    public static String ActiveRegion;
    public static String Name;
    public static String Schematic;
    private static ArrayList<Arena> arenas;
    static Map<String, String> arenaList;
    static List<String> DP_List;
    private static List<String> Lotto_List;
    static Boolean BL_Mode;
    static Boolean Samelist;
    static ScoreboardManager SB;
    static Scoreboard board;
    static Team team;
    static DisguiseCraftAPI DC;
    static Plugin FC;
    static Plugin MC;
    static EchoPetAPI EP;
    private static Essentials ES;
    static Map<String, String> commands;

    static {
        ArenaTimer = 0;
        Probending = 0;
        isNearArena = false;
        isArenaReset = false;
        isArenaOccupied = false;
        ArenaTimer2 = 0;
        isNearArena2 = false;
        isArenaReset2 = false;
        isArenaOccupied2 = false;
        ArenaTimer4 = 0;
        isNearArena4 = false;
        isArenaReset4 = false;
        isArenaOccupied4 = false;
        NotChosenTimer = 0;
        isNearNotChosen = false;
        isNotChosenReset = false;
        isNotChosenOccupied = false;
        cooldown_list = new ArrayList();
        containers = new ArrayList<Material>();
        containers2 = new ArrayList<EntityType>();
        restrictedWorlds = new ArrayList<String>();
        RegionFeatures = false;
        TriggerRegion = "";
        ActiveRegion = "";
        Name = "";
        Schematic = "";
        arenas = new ArrayList();
        arenaList = new HashMap<String, String>();
        DP_List = null;
        Lotto_List = null;
        BL_Mode = false;
        Samelist = false;
        SB = null;
        board = null;
        team = null;
        DC = null;
        FC = null;
        MC = null;
        EP = null;
        ES = null;
        commands = new HashMap<String, String>();
    }

    public void onDisable() {
        if (dTools.getEM() != null) {
            dTools.getEM().dispose();
        }
        this.getServer().getScheduler().cancelTasks((Plugin)plugin);
    }

    public EchoPetAPI getEchoPetAPI() {
        return EchoPetAPI.getAPI();
    }

    public void setupDisguiseCraft() {
        DC = DisguiseCraft.getAPI();
    }

    public void onEnable() {
        plugin = this;
        this.loadConfig();
        FC = this.getServer().getPluginManager().getPlugin("Factions");
        MC = this.getServer().getPluginManager().getPlugin("MassiveCore");
        Plugin WG = this.getServer().getPluginManager().getPlugin("WorldEdit");
        Plugin WE = this.getServer().getPluginManager().getPlugin("WorldGuard");
        dTools.setES((Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials"));
        Plugin DcP = this.getServer().getPluginManager().getPlugin("DisguiseCraft");
        Plugin EcP = this.getServer().getPluginManager().getPlugin("EchoPet");
        if (EcP != null) {
            EP = this.getEchoPetAPI();
        }
        if (DcP != null) {
            DC = DisguiseCraft.getAPI();
        }
        SB = Bukkit.getScoreboardManager();
        board = SB.getNewScoreboard();
        team = board.registerNewTeam("FREE FOR ALL");
        EffectLib lib = EffectLib.instance();
        if (lib != null) {
            System.out.println("Custom Particle Effects are diabled for this build!");
        }
        log.info("Enabling dNiym's Tools Plugin");
        if (dTools.getES() != null) {
            System.out.println("[dTools]: Essentials FOUND enabling player broadcast messages.");
        }
        if (WG != null && WE != null) {
            System.out.println("[dTools] Worldguard & Worldedit Found! Enabling Region Features.");
            RegionFeatures = true;
        }
        if (DC != null) {
            System.out.println("[dTools] DisguiseCraft Found!   Enabling disguise API Features.");
        }
        if (EP != null) {
            System.out.println("[dTools] EchoPet Found!  Enabling special pet Features.");
        }
        this.getServer().getPluginManager().registerEvents((Listener)this.listener, (Plugin)this);
        containers.add(Material.CHEST);
        containers.add(Material.TRAPPED_CHEST);
        containers.add(Material.FURNACE);
        containers.add(Material.ITEM_FRAME);
        containers.add(Material.BREWING_STAND);
        containers.add(Material.BREWING_STAND_ITEM);
        containers.add(Material.ARMOR_STAND);
        containers.add(Material.DISPENSER);
        containers.add(Material.DROPPER);
        containers.add(Material.ENDER_CHEST);
        containers.add(Material.BURNING_FURNACE);
        containers.add(Material.CAULDRON);
        containers.add(Material.CAULDRON_ITEM);
        containers.add(Material.RAILS);
        containers.add(Material.DETECTOR_RAIL);
        containers.add(Material.ACTIVATOR_RAIL);
        containers.add(Material.MINECART);
        containers.add(Material.POWERED_MINECART);
        containers.add(Material.POWERED_RAIL);
        containers.add(Material.STORAGE_MINECART);
        containers2.add(EntityType.ARMOR_STAND);
        containers2.add(EntityType.MINECART_CHEST);
        containers2.add(EntityType.ITEM_FRAME);
        containers2.add(EntityType.MINECART_HOPPER);
        containers2.add(EntityType.MINECART_FURNACE);
        this.registerCommands();
    }

    public void loadConfig() {
        File conf = new File("plugins/dTools/config.yml");
        if (!conf.exists()) {
            System.out.println("dTools:  Warning Configuration File Not Found! /plugins/dTools/config.yml");
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
            Arenas.clear();
            NetherExploit.clear();
            if (config.getConfigurationSection("General") != null) {
                System.out.println("Loading General Plugin Settings");
                final Set<String> keys = (Set<String>)config.getKeys(true);
                for (String key : keys) {
                    final Map<String, Object> val = (Map<String, Object>)config.getValues(true);
                    String value = val.get(key).toString();
                    if (key.contains((CharSequence)"NetherWorld")) {
                        NetherExploit.put(key, value);
                        System.out.println("Nether Base Detection Enabled for World: " + value);
                        restrictedWorlds.add(value);
                        System.out.println("Blocking /home /sethome above the nether for world: " + value);
                    }
                    if (key.contains((CharSequence)"SpawnWorldName")) {
                        restrictedWorlds.add(value);
                        System.out.println("Blocking /home /sethome in spawn world: " + value);
                    }
                    if (key.contains((CharSequence)"MaxLegalY")) {
                        NetherExploit.put(key, value);
                        System.out.println("Nether World Max Legal Y value: " + value);
                    }
                    if (!key.contains((CharSequence)"UseScoreboard")) continue;
                    System.out.println("dTools will keep track of scores in the arena: " + value);
                }
            }
            if (config.getConfigurationSection("Rewards") != null) {
                Boolean sameList = config.getBoolean("Rewards.UseSameList");
                final Boolean BL_Mode = config.getBoolean("Rewards.BlackListMode");
                if (BL_Mode) {
                    System.out.println("[dTools]: - BLACKLIST Mode enabled, items on the DP/Lottery list will NEVER drop.");
                } else {
                    System.out.println("[dTools]: - WHITELIST Mode enabled, only items on the DP/Lottery list will be used.");
                }
                if (sameList.booleanValue()) {
                    System.out.println("[dTools]: - Rewards will use the same list for Drop Parties & Lotteries");
                } else {
                    System.out.println("[dTools]: - Rewards will use separate lists for Drop Parties & Lotteries");
                }
                for (final String a : config.getConfigurationSection("Rewards").getKeys(false)) {
                    final Set<String> keys2 = (Set<String>)config.getKeys(true);
                    if (dTools.DP_List != null) {
                        dTools.DP_List.clear();
                    }
                    if (dTools.Lotto_List != null) {
                        getLotto_List().clear();
                    }
                    for (final String key2 : keys2) {
                        final Map<String, Object> val2 = (Map<String, Object>)config.getValues(true);
                        final String value2 = val2.get(key2).toString();
                        if (val2.get(key2) instanceof MemorySection) {
                            if (key2.equalsIgnoreCase("Drop_Party_List")) {
                                dTools.DP_List.add(value2);
                                System.out.println("Adding Item To DP_List: " + value2);
                                if (sameList) {
                                    dTools.Lotto_List.add(value2);
                                }
                            }
                            if (!key2.equalsIgnoreCase("Lottery_List") || sameList) {
                                continue;
                            }
                            dTools.Lotto_List.add(val2.toString());
                        }
                    }
                }
            }
            if (config.getConfigurationSection("Arenas") != null) {
                for (String a : config.getConfigurationSection("Arenas").getKeys(false)) {
                    Arena arena = new Arena(a);
                    final Set<String> keys = (Set<String>)config.getKeys(true);
                    arenaList.clear();
                    for (String key : keys) {
                        Map val = config.getValues(true);
                        String value = val.get(key).toString();
                        if (val.get(key) instanceof MemorySection) {
                            if (key.equalsIgnoreCase("Arenas")) continue;
                            arenaList.put("ArenaName." + key, key);
                            continue;
                        }
                        arenaList.put(key, value);
                    }
                    System.out.println("arena: " + arena + " keys " + keys);
                    System.out.println("Loaded Auto-Resetting Arena: " + arena.getName());
                }
            }
        }
    }

    public void createConfig() {
        File con = new File("plugins/dTools");
        File conf = new File("plugins/dTools/config.yml");
        if (!con.exists()) {
            YamlConfiguration config;
            con.mkdir();
            if (!conf.exists()) {
                try {
                    conf.createNewFile();
                    config = YamlConfiguration.loadConfiguration((File)conf);
                    config.options().header("dNiym's Toolkit");
                    config.save(conf);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!conf.exists()) {
                try {
                    conf.createNewFile();
                    config = YamlConfiguration.loadConfiguration((File)conf);
                    config.options().header("dNiym's Toolkit");
                    config.save(conf);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void reloadConfiguration() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    private void registerCommands() {
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled= true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        System.out.println("Fired Teleport event for player " + (Object)event.getPlayer());
        File conf = new File("plugins/dTools/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)conf);
        Player player = event.getPlayer();
        String value = config.getString("General.NetherWorld");
        int maxY = config.getInt("General.MaxLegalY");
        if (value != null && event.getTo().getWorld().getName().equalsIgnoreCase(value) && event.getTo().getY() >= (double)maxY) {
            int x = (int)player.getLocation().getX();
            int y = (int)player.getLocation().getY();
            int z = (int)player.getLocation().getZ();
            String coords = String.valueOf(x) + ", " + y + ", " + z;
            System.out.println("[dTools] WARNING!   Player: " + player.getName() + " teleported on top of the nether! Coords:" + coords);
            player.sendMessage((Object)ChatColor.RED + "WARNING!  You have glitched on top of the nether.  Leave NOW!");
            for (Player ops : Bukkit.getOnlinePlayers()) {
                if (!ops.isOp()) continue;
                ops.sendMessage("[dTools] Warning!:  Player " + player.getName() + " teleported on top of the nether! Coords:" + coords);
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer() != null) {
            if (!event.getPlayer().hasPermission("dtools.batswarm")) {
                return;
            }
            if (!(event.getFrom().getBlockY() <= event.getTo().getBlockY() || event.getTo().getBlock().getRelative(BlockFace.DOWN).isLiquid() || event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)) {
                double chance = Math.random() * 100.0;
                if (chance < 97.0) {
                    return;
                }
                event.getPlayer().sendMessage((Object)ChatColor.GRAY + "You are" + (Object)ChatColor.YELLOW + " The Batman!");
                for (int i = 1; i <= 10; ++i) {
                    World w = event.getPlayer().getLocation().getWorld();
                    BlockFace blkdir = Tools.getBlockFaceDirection(event.getPlayer());
                    Block Nextblk = event.getPlayer().getLocation().getBlock().getRelative(blkdir);
                    for (int n = 1; n <= 4; ++n) {
                        Nextblk = Nextblk.getRelative(blkdir);
                    }
                    w.spawnEntity(Nextblk.getLocation(), EntityType.BAT);
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        User user;
        Player player = null;
        if (sender instanceof Player) {
            player = (Player)sender;
        }
        if (cmd.getName().equalsIgnoreCase("broadcastcommand") && (user = Tools.ConvPtoU(player)) != null) {
            try {
                new Commandbroadcast().run(this.getServer(), user, label, args);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cmd.getName().equalsIgnoreCase("blockcommand") && player != null) {
            new Commands.BlockCommand();
            BlockCommand.execute((CommandSender)player, label, args);
        }
        if (cmd.getName().equalsIgnoreCase("listcommand") && player != null) {
            new ListCommand().execute((CommandSender)player, label, args);
        }
        if (cmd.getName().equalsIgnoreCase("dTools") && player.hasPermission("dTools.Admin")) {
            if (args.length < 1) {
                player.sendMessage("Available commands:");
                player.sendMessage("/dtools check - Check for saved nether glitch offenses.");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("check")) {
                if (illegalBases.isEmpty()) {
                    player.sendMessage("There have been no recent Nether Glitches detected!");
                    return true;
                }
                player.sendMessage("Warning!  Illegal Nether Glitch(es) have been detected!  Please check the following coords in the nether:");
                for (String pname : illegalBases.keySet()) {
                    Location loc = illegalBases.get(pname);
                    int x = (int)loc.getX();
                    int y = (int)loc.getY();
                    int z = (int)loc.getZ();
                    int count = 1;
                    player.sendMessage(String.valueOf(count) + ") " + pname + " at " + x + ", " + y + ", " + z);
                }
                player.sendMessage("Use /dtools check - to view this list again.");
                player.sendMessage("Use /dtools check <number> - to teleport to the offending location");
                player.sendMessage("Use /dtools remove <number> - to remove the offense.");
            }
            if (args.length == 2) {
                Location loc = this.getIllegalLocation(Integer.parseInt(args[1]));
                if (loc == null) {
                    player.sendMessage("Warning!  Could not find " + args[1] + " are you sure it was in the list!?");
                    return true;
                }
                illegalReview = true;
                player.teleport(loc);
                player.sendMessage("Teleporting you to the recorded offense for review.");
            }
        }
        if (cmd.getName().equalsIgnoreCase("effectscommand")) {
            if (EM != null) {
                ConeEffect effect = new ConeEffect(EM);
                effect.setLocation(player.getLocation().add(0.0, 0.0, 0.0));
                effect.period = 4;
                effect.iterations = 20;
                effect.particle = ParticleEffect.REDSTONE;
                effect.start();
            } else {
                player.sendMessage("Sorry EffectsLib api does not seem to be installed!  Command disabled!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("commandpetcannon") && player != null) {
            try {
                new Commandpetcannon().run(player);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cmd.getName().equalsIgnoreCase("joindate")) {
            FirstJoin.execute(sender, label, args);
        }
        if (cmd.getName().equalsIgnoreCase("donationreward") && player != null) {
            RandomDropsForPlayers.execute((CommandSender)player, label, args);
        }
        return true;
    }

    private Location getIllegalLocation(int number) {
        int count = 1;
        for (String pname : illegalBases.keySet()) {
            if (count == number) {
                return illegalBases.get(pname);
            }
            ++count;
        }
        return null;
    }

    public static List<String> getLotto_List() {
        return Lotto_List;
    }

    public static void setLotto_List(List<String> lotto_List) {
        Lotto_List = lotto_List;
    }

    public static List<dToolsPlayer> getCooldown_list() {
        return cooldown_list;
    }

    public static void setCooldown_list(dToolsPlayer dt) {
        cooldown_list.add(dt);
    }

    public static EffectManager getEM() {
        return EM;
    }

    public void setEM(EffectManager eM) {
        EM = eM;
    }

    public static ArrayList<Arena> getArenas() {
        return arenas;
    }

    public static void setArenas(ArrayList<Arena> arenas) {
        dTools.arenas = arenas;
    }

    public static Essentials getES() {
        return ES;
    }

    public static void setES(Essentials eS) {
        ES = eS;
    }
}

