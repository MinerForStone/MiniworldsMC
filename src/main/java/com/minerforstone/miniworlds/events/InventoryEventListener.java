package com.minerforstone.miniworlds.events;

import com.minerforstone.miniworlds.logic.TeamSelectMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryEventListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getHolder(false) instanceof TeamSelectMenu menu) {
            menu.activate(event.getSlot());
            event.setCancelled(true);
        }
    }
}
