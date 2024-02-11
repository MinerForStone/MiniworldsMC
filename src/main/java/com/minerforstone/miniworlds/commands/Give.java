package com.minerforstone.miniworlds.commands;

import com.minerforstone.miniworlds.world.structures.Structure;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Give implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            return false;
        }

        int type;
        try {
            type = Integer.parseInt(args[0]);
        } catch (Exception e) {
            return false;
        }

        int count;
        try {
            count = Integer.parseInt(args[1]);
        } catch (Exception e) {
            count = 1;
        }

        for (int i = 0; i < count; i++) {
            try {
                Structure.giveItem(player, Structure.REGISTRY.get(type));
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
