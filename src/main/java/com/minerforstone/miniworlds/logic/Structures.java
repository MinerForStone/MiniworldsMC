package com.minerforstone.miniworlds.logic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class Structures {
    public final static ArrayList<String> structureIds = new ArrayList<>() {{
        add("none"); // CustomModelData is 1-indexed, so this just makes things easier
        add("slum");
        add("house");
        add("apartment");
    }};

    public static boolean existsAt(Location location) {
        Collection<ItemDisplay> nearby = location.toCenterLocation().getNearbyEntitiesByType(ItemDisplay.class, 0.0001);
        if (!nearby.isEmpty()) {
            for (ItemDisplay display : nearby) if (display.getScoreboardTags().contains("structure")) return true;
        }
        return false;
    }

    public static ItemDisplay get(Location location) {
        Collection<ItemDisplay> nearby = location.toCenterLocation().getNearbyEntitiesByType(ItemDisplay.class, 0.0001);
        if (!nearby.isEmpty()) {
            for (ItemDisplay display : nearby) if (display.getScoreboardTags().contains("structure")) return display;
        }
        return null;
    }

    public static void remove(Location location, Player player) {
        ItemDisplay structure = Structures.get(location);
        if (structure != null) {
            player.getInventory().addItem(structure.getItemStack());
            structure.remove();
            player.getWorld().playSound(location, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 16, 1);
        }
    }

    public static void build(Location location, Player player) {
        location = location.toCenterLocation();
        if (!location.clone().add(new Vector(0,-1,0)).getBlock().getType().isSolid() || location.getBlock().getType().isSolid()) {
            Feedback.send(Feedback.Error.CANNOT_PLACE, player);
            return;
        }

        if (Structures.existsAt(location)) {
            Feedback.send(Feedback.Error.CANNOT_PLACE, player);
            return;
        }

        int structureType = player.getInventory().getItem(EquipmentSlot.HAND).getItemMeta().getCustomModelData();

        ItemDisplay display = player.getWorld().spawn(location, ItemDisplay.class);
        ItemStack stack = new ItemStack(Material.OAK_PLANKS);

        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(structureType);
        stack.setItemMeta(meta);

        display.setItemStack(stack);

        display.setRotation(
                switch (player.getFacing()) {
                    case NORTH -> 0.0F;
                    case EAST -> 90.0F;
                    case SOUTH -> 180.0F;
                    case WEST -> 270.0F;
                    default -> 0.0F;
                },
                0);

        display.addScoreboardTag("structure");
        display.addScoreboardTag(Structures.structureIds.get(structureType));

        player.getWorld().playSound(location, Sound.BLOCK_ANVIL_USE, 16, 2);
        player.getInventory().getItem(EquipmentSlot.HAND).subtract();
    }
}
