package com.minerforstone.miniworlds.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Feedback {
    public enum Error {
        INVALID_STRUCTURE("There was an error placing this structure"),
        CANNOT_PLACE("Cannot place here"),
        CANNOT_DESTROY("Cannot destroy this structure"),
        OUTSIDE_TERRITORY("This can only be placed inside your own territory"),
        ENEMY_TERRITORY("Cannot place in enemy territory"),
        INSUFFICIENT_POPULATION("Not enough population"),
        INSUFFICIENT_RESOURCES("Not enough resources"),
        CAPITOL_EXISTS("This nation already has a capitol"),
        VAGABOND("You aren't part of a nation yet!");

        private String message;

        Error(String message) {
            this.message = message;
        }
    }

    public enum Info {
        STRUCTURES_REMOVED("Some structures were removed"),
        NOT_OWNER("This structure is owned by another nation"),
        CAPITOL_REMOVE("Capitols cannot be removed unless the nation is disbanded");

        private String message;

        Info(String message) {
            this.message = message;
        }
    }

    public static void send(Error error, Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 16, 0);
        player.sendActionBar(Component.text(error.message).color(NamedTextColor.DARK_RED));
    }

    public static void send(Info info, Player player) {
        player.sendActionBar(Component.text(info.message).color(NamedTextColor.GOLD));
    }
}
