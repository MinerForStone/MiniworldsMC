package com.minerforstone.miniworlds.events;

import com.minerforstone.miniworlds.logic.Structures;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEventListener implements Listener {
    // Player events
    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        Location interactionPoint = event.getInteractionPoint();
        if (interactionPoint == null) return;

        if (itemStack == null) {
            Structures.remove(interactionPoint, event.getPlayer());
            return;
        }

        if (event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getType().equals(Material.OAK_PLANKS)) {
            event.setCancelled(true);
            Structures.build(interactionPoint, event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        for (ItemStack itemStack : event.getBlock().getDrops())
            event.getPlayer().getInventory().addItem(itemStack);
    }
}
