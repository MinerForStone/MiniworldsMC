package com.minerforstone.miniworlds.commands;

import com.minerforstone.miniworlds.world.structures.Capitol;
import com.minerforstone.miniworlds.world.structures.Structure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeleteNation implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        if (args.length == 0) {
            return false;
        }

        int nationId;
        try {
            nationId = Integer.parseInt(args[0]);
        } catch (Exception e) {
            return false;
        }

        if (Capitol.getCapitolByTeam(nationId) != null) {
            int count = 0;
            for (Structure structure : Structure.getStructureList()) {
                if (structure.getTeamId() == nationId) {
                    structure.delete();
                    count++;
                }
            }
            sender.sendMessage(Component.text("Successfully removed " + count + " structures from nation " + nationId));
            return true;
        }

        sender.sendMessage(Component.text("That nation does not exist", NamedTextColor.RED));
        return true;
    }
}
