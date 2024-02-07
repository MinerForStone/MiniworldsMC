package com.minerforstone.miniworlds.commands;

import com.minerforstone.miniworlds.Miniworlds;
import com.minerforstone.miniworlds.logic.TeamSelectMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowTeamSelect implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        player.openInventory(new TeamSelectMenu(Miniworlds.getPlugin(), player).getInventory());
        return true;
    }
}
