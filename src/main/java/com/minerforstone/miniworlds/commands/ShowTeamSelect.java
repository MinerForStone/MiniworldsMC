package com.minerforstone.miniworlds.commands;

import com.minerforstone.miniworlds.Miniworlds;
import com.minerforstone.miniworlds.logic.TeamSelectMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowTeamSelect implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (((Player) sender).getPersistentDataContainer().has(Miniworlds.Key.TEAM)) {
            sender.sendMessage(Component.text("You can't switch teams right now!", NamedTextColor.GOLD));
            return false;
        }

        player.openInventory(new TeamSelectMenu(Miniworlds.getPlugin(), player).getInventory());
        return true;
    }
}
