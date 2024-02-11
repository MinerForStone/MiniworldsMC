package com.minerforstone.miniworlds.commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RemoveTeam implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player playerSender)) return false;
        Set<NamespacedKey> keys = playerSender.getPersistentDataContainer().getKeys();
        for (NamespacedKey key : keys) {
            playerSender.getPersistentDataContainer().remove(key);
        }
        return true;
    }
}
