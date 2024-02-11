package com.minerforstone.miniworlds.world.structures;

import com.minerforstone.miniworlds.Miniworlds;
import com.minerforstone.miniworlds.logic.Feedback;
import com.minerforstone.miniworlds.registry.Registry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.*;

public abstract class Structure {
    private BlockFace direction;
    private int structureId;
    private int teamId;
    private Location location;
    private ItemDisplay display;
    protected String name;

    public static final Registry<Structure> REGISTRY = new Registry<>();

    private static final Map<Location, Structure> structures = new HashMap<>();


    public void place(Player player, Location location) {
        place(player, location, Sound.BLOCK_ANVIL_USE, 2);
    }

    public void place(Player player, Location location, @Nullable Sound sound, float pitch) {
        if (!player.getPersistentDataContainer().has(Miniworlds.Key.TEAM)) {
            Feedback.send(Feedback.Error.VAGABOND, player);
            return;
        }

        location = location.toCenterLocation();

        if (!location.clone().add(new Vector(0,-1,0)).getBlock().getType().isSolid() || location.getBlock().getType().isSolid()) {
            Feedback.send(Feedback.Error.CANNOT_PLACE, player);
            return;
        }

        if (Structure.existsAt(location)) {
            Feedback.send(Feedback.Error.CANNOT_PLACE, player);
            return;
        }

        structureId = REGISTRY.getId(getClass());
        display = player.getWorld().spawn(location, ItemDisplay.class);
        teamId = player.getPersistentDataContainer().get(Miniworlds.Key.TEAM, PersistentDataType.INTEGER);
        this.location = location;
        direction = player.getFacing();

        ItemStack displayModel = new ItemStack(Material.OAK_PLANKS);
        displayModel.editMeta(meta -> meta.setCustomModelData(structureId));

        display.setItemStack(displayModel);
        display.setRotation(
                switch (direction) {
                    case EAST -> 90.0F;
                    case SOUTH -> 180.0F;
                    case WEST -> 270.0F;
                    default -> 0.0F;
                },
                0);

        if (sound != null)
            player.getWorld().playSound(location, sound, 16, pitch);
        player.getInventory().getItem(EquipmentSlot.HAND).subtract();

        structures.put(location, this);
    }

    public void remove(Player player) {
        remove(player, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1);
    }

    public void remove(Player player, @Nullable Sound sound, float pitch) {
        if (!Objects.equals(teamId, player.getPersistentDataContainer().get(Miniworlds.Key.TEAM, PersistentDataType.INTEGER))) {
            Feedback.send(Feedback.Info.NOT_OWNER, player);
            return; // Can't remove if not owner
        }

        display.remove();
        giveItem(player, this.getClass());
        if (sound != null)
            player.getWorld().playSound(location, sound, 16, pitch);

        structures.remove(location);
    }

    public void delete() { // Force-deletes a structure
        display.remove();
        structures.remove(location);
    }

    public static boolean existsAt(Location location) {
        return structures.containsKey(location.toCenterLocation());
    }

    public static Structure get(Location location) {
        return structures.get(location.toCenterLocation());
    }

    public static void giveItem(Player player, Class<? extends Structure> structure) {
        ItemStack itemStack = new ItemStack(Material.OAK_PLANKS);
        itemStack.editMeta(meta -> {
            int structureId = REGISTRY.getId(structure);
            meta.setCustomModelData(structureId);
            meta.displayName(Component.text(REGISTRY.getName(structure)).decoration(TextDecoration.ITALIC, false));
        });
        player.getInventory().addItem(itemStack);
    }



    public BlockFace getDirection() {
        return direction;
    }

    public int getStructureId() {
        return structureId;
    }

    public int getTeamId() {
        return teamId;
    }

    public Location getLocation() {
        return location;
    }

    public static Collection<Structure> getStructureList() {
        return structures.values();
    }

}
