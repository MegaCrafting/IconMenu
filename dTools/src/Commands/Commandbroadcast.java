/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  com.earth2me.essentials.commands.EssentialsCommand
 *  com.earth2me.essentials.commands.NotEnoughArgumentsException
 *  com.earth2me.essentials.utils.FormatUtil
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 */
package Commands;

import Util.Tools;
import com.earth2me.essentials.User;
import com.earth2me.essentials.commands.EssentialsCommand;
import com.earth2me.essentials.commands.NotEnoughArgumentsException;
import com.earth2me.essentials.utils.FormatUtil;
import java.util.Collection;
import java.util.UUID;
import main.dToolsPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Commandbroadcast
extends EssentialsCommand {
    public Commandbroadcast() {
        super("broadcast");
    }

    public void run(Server server, User user, String commandLabel, String[] args) throws Exception {
        Player plr = user.getBase();
        if (!plr.hasPermission("dTools.broadcastcommand")) {
            user.sendMessage("That command is reserved for donating members only!");
            return;
        }
        if (args.length < 1) {
            String min_time = "five";
            user.sendMessage((Object)ChatColor.AQUA + "Broadcasts your message to the entire server!");
            user.sendMessage("/broadcast <message you want to broadcst>");
            if (plr.hasPermission("dtools.cooldown4")) {
                min_time = "four";
            }
            if (plr.hasPermission("dtools.cooldown3")) {
                min_time = "three";
            }
            user.sendMessage("-This ability has a " + min_time + " minute cooldown!");
            return;
        }
        dToolsPlayer dt = dToolsPlayer.findPlayer(user);
        double cooldown = 0.0;
        if (dt != null) {
            cooldown = dt.getTimeIn();
        } else {
            dt = new dToolsPlayer(Tools.ConvUtoP(user));
            dt.setPlayer(Tools.ConvUtoP(user));
            dt.setUuid(dt.getPlayer().getUniqueId());
            dt.setTimeIn(System.currentTimeMillis());
            cooldown = dt.getTimeIn();
        }
        if (cooldown == 0.0) {
            dt.setTimeIn(System.currentTimeMillis());
        }
        double timediff = ((double)System.currentTimeMillis() - cooldown) * 0.001;
        int mintime = 300;
        if (plr.hasPermission("dtools.cooldown4")) {
            mintime = 240;
        }
        if (plr.hasPermission("dtools.cooldown3")) {
            mintime = 180;
        }
        if (timediff <= (double)mintime) {
            int togo = (int)((double)mintime - timediff);
            String mins = "five";
            if (mintime == 240) {
                mins = "four";
            }
            if (mintime == 180) {
                mins = "three";
            }
            user.sendMessage((Object)ChatColor.RED + "Sorry!  You can not use the broadcast system more than once every " + mins + " minutes! You have: " + togo + " seconds to go!");
            return;
        }
        dt.setTimeIn(System.currentTimeMillis());
        for (Player p : server.getOnlinePlayers()) {
            String message = FormatUtil.replaceFormat((String)Commandbroadcast.getFinalArg((String[])args, (int)0)).replace((CharSequence)"\\n", (CharSequence)"\n");
            p.sendMessage((Object)ChatColor.RED + "[" + (Object)ChatColor.YELLOW + "Donor Message" + (Object)ChatColor.RED + "]" + (Object)ChatColor.AQUA + message);
        }
    }

    private void sendBroadcast(String name, String[] args) throws NotEnoughArgumentsException {
        if (args.length < 1) {
            throw new NotEnoughArgumentsException();
        }
    }
}

