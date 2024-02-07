package com.minerforstone.miniworlds.logic;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Help {
    private static class Colors {
        public static NamedTextColor TITLE = NamedTextColor.AQUA;
        public static NamedTextColor MESSAGE = NamedTextColor.GOLD;
        public static NamedTextColor IDENTIFIER = NamedTextColor.WHITE;
        public static NamedTextColor WELCOME = NamedTextColor.GREEN;
    }
    private final static Component[] tips = new Component[] {
            Component.text("Click a structure with an empty hand to remove it"),
            Component.text("Some structures are stackable. Try placing a ").append(Component.text("House", Colors.IDENTIFIER)).append(Component.text(" on another one")),
            Component.text("Run ").append(Component.text("/craft", Colors.IDENTIFIER)).append(Component.text(" to open the crafting grid")),
    };

    public static void displayWelcome(Player player) {
        Bukkit.broadcast(
                Component.text("Welcome, " + player.getName() + "! Run ", Colors.WELCOME)
                        .append(Component.text("/teams", Colors.IDENTIFIER))
                        .append(Component.text(" to pick a team and start playing", Colors.WELCOME)));
    }

    public static void displayTip() {
        Bukkit.broadcast(
                Component.text("[Tip] ", Colors.TITLE, TextDecoration.BOLD)
                .append(tips[(int) Math.floor(Math.random() * tips.length)].color(Colors.MESSAGE).decoration(TextDecoration.BOLD, false))
        );
        Bukkit.getServer().playSound(Sound.sound(Key.key("entity.chicken.egg"), Sound.Source.AMBIENT, 16, 1));
    }


}
