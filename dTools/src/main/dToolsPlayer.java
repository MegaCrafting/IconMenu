/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  org.bukkit.entity.Player
 */
package main;

import Util.Tools;
import com.earth2me.essentials.User;
import java.util.ArrayList;
import java.util.UUID;
import main.dTools;
import org.bukkit.entity.Player;

public class dToolsPlayer {
    private Player player;
    private UUID uuid;
    private User user;
    private double timeIn;

    public dToolsPlayer(Player p) {
    }

    public static dToolsPlayer findPlayer(User u) {
        dToolsPlayer dt3;
        if (dTools.getCooldown_list().isEmpty()) {
            dToolsPlayer dt = new dToolsPlayer(Tools.ConvUtoP(u));
            dt.player = Tools.ConvUtoP(u);
            dt.uuid = dt.getPlayer().getUniqueId();
            dt.user = u;
            dTools.setCooldown_list(dt);
            return dt;
        }
        for (dToolsPlayer dt : dTools.getCooldown_list()) {
            if (dt.user != u) continue;
            return dt;
        }
        dt3 = new dToolsPlayer(Tools.ConvUtoP(u));
        dt3.player = Tools.ConvUtoP(u);
        dt3.uuid = dt3.getPlayer().getUniqueId();
        dt3.user = u;
        dTools.cooldown_list.add(dt3);
        return dt3;
    }

    public double getTimeIn() {
        return this.timeIn;
    }

    public void setTimeIn(double timeIn) {
        this.timeIn = timeIn;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

