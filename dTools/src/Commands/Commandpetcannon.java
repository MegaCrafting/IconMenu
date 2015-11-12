/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.dsh105.echopet.api.EchoPetAPI
 *  com.dsh105.echopet.compat.api.entity.IPet
 *  com.earth2me.essentials.User
 *  com.earth2me.essentials.commands.EssentialsCommand
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package Commands;

import Util.Tools;
import com.dsh105.echopet.api.EchoPetAPI;
import com.dsh105.echopet.compat.api.entity.IPet;
import com.earth2me.essentials.User;
import com.earth2me.essentials.commands.EssentialsCommand;
import java.util.Random;
import main.dTools;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Commandpetcannon
extends EssentialsCommand {
    private static final Random random = new Random();

    public Commandpetcannon() {
        super("kittycannon");
    }

    public EchoPetAPI getEchoPetAPI() {
        return EchoPetAPI.getAPI();
    }

    public void run(Player player) throws Exception {
        class KittyCannonExplodeTask
        implements Runnable {
            private final /* synthetic */ IPet val$pet;
            private final /* synthetic */ EchoPetAPI val$ep;
            private final /* synthetic */ Player val$player;

            KittyCannonExplodeTask(IPet iPet, EchoPetAPI echoPetAPI, Player player) {
                this.val$pet = iPet;
                this.val$ep = echoPetAPI;
                this.val$player = player;
            }

            @Override
            public void run() {
                Location loc = this.val$pet.getLocation();
                this.val$ep.removePet(this.val$player, false, false);
                this.val$player.sendMessage((Object)ChatColor.RED + "BOOM! " + (Object)ChatColor.AQUA + "Your pet just went nuclear!");
                loc.getWorld().createExplosion(loc, 0.0f);
            }
        }
        EchoPetAPI ep = this.getEchoPetAPI();
        if (ep == null) {
            player.sendMessage("Sorry!  This command launches a pet... pet's don't seem to be enabled yet!");
            return;
        }
        IPet pet = ep.getPet(player);
        if (pet == null) {
            player.sendMessage("You don't have a pet!! Spawn one first!! - use /pet for help!");
            return;
        }
        User user = Tools.ConvPtoU(player);
        pet.getCraftPet().setVelocity(user.getBase().getEyeLocation().getDirection().multiply(10));
        dTools.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)dTools.plugin, (Runnable)new KittyCannonExplodeTask(pet, ep, player), 10);
    }

}

