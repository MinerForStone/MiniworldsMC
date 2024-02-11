package com.minerforstone.miniworlds.world.structures;

import com.minerforstone.miniworlds.Miniworlds;
import com.minerforstone.miniworlds.logic.Feedback;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Capitol extends Structure {
    public Capitol() {
        this.name = "Capitol";
    }
    private static final ArrayList<Capitol> capitolList = new ArrayList<>();

    public static Capitol getCapitolByTeam(int id) {
        for (Capitol capitol : capitolList) {
            if (capitol.getTeamId() == id) return capitol;
        }
        return null;
    }

    public void place(Player player, Location location) {

        if (getCapitolByTeam(player.getPersistentDataContainer().get(Miniworlds.Key.TEAM, PersistentDataType.INTEGER)) != null) {
            Feedback.send(Feedback.Error.CAPITOL_EXISTS, player);
            player.getInventory().getItem(EquipmentSlot.HAND).subtract();
            return;
        }

        capitolList.add(this);

        super.place(player, location);
    }

    public void remove(Player player) {
        Feedback.send(Feedback.Info.CAPITOL_REMOVE, player);
    }

    public void delete() {
        capitolList.remove(this);
        super.delete();
    }
}
