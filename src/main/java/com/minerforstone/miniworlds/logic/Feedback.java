package com.minerforstone.miniworlds.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Feedback {
    public enum Error {
        CANNOT_PLACE,
        CANNOT_DESTROY,
        OUTSIDE_TERRITORY,
        ENEMY_TERRITORY,
        INSUFFICIENT_POPULATION,
        INSUFFICIENT_RESOURCES
    }

    public enum Info {
        STRUCTURES_REMOVED
    }

    public static void send(Error error, Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 16, 0);
        player.sendActionBar(Component.text(
                switch (error) {
                    case CANNOT_PLACE -> "Cannot place here";
                    case CANNOT_DESTROY -> "Cannot destroy this structure";
                    case OUTSIDE_TERRITORY -> "Can only be placed inside your own territory";
                    case ENEMY_TERRITORY -> "Cannot place in enemy territory";
                    case INSUFFICIENT_POPULATION -> "Not enough population";
                    case INSUFFICIENT_RESOURCES -> "Not enough resources";
                }
        ).color(NamedTextColor.DARK_RED));
    }

    public static void send(Info info, Player player) {
        player.sendActionBar(Component.text(
                switch (info) {
                    case STRUCTURES_REMOVED -> "Some structures were removed";
                }
        ).color(NamedTextColor.GOLD));
    }
}
