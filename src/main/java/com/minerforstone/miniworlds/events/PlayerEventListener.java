package com.minerforstone.miniworlds.events;

import com.minerforstone.miniworlds.logic.Feedback;
import com.minerforstone.miniworlds.world.structures.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;

public class PlayerEventListener implements Listener {
    // Player events
    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        Location interactionPoint = event.getInteractionPoint();
        if (interactionPoint == null) return;

        if (itemStack == null) {
            Structure structure = Structure.get(interactionPoint);
            if (structure != null)
                structure.remove(event.getPlayer());
            return;
        }

        if (itemStack.getItemMeta().hasCustomModelData() && itemStack.getType().equals(Material.OAK_PLANKS)) {
            event.setCancelled(true);

            try {
                Constructor<? extends Structure> constructor = Structure.REGISTRY.get(itemStack.getItemMeta().getCustomModelData()).getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance().place(event.getPlayer(), interactionPoint);

            } catch (Exception e) {
                Feedback.send(Feedback.Error.INVALID_STRUCTURE, event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        for (ItemStack itemStack : event.getBlock().getDrops())
            event.getPlayer().getInventory().addItem(itemStack);
    }
}
