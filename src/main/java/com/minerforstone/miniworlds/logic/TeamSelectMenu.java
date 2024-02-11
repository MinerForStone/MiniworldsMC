package com.minerforstone.miniworlds.logic;

import com.minerforstone.miniworlds.Miniworlds;
import com.minerforstone.miniworlds.world.structures.Capitol;
import com.minerforstone.miniworlds.world.structures.Structure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class TeamSelectMenu implements InventoryHolder{
    private final Inventory inventory;
    private final Player owner;

    private static class Colors {
        static TextColor RED = NamedTextColor.DARK_RED;
        static TextColor ORANGE = TextColor.color(0xF08000);
        static TextColor YELLOW = NamedTextColor.YELLOW;
        static TextColor GREEN = NamedTextColor.GREEN;
        static TextColor CYAN = NamedTextColor.AQUA;
        static TextColor BLUE = NamedTextColor.BLUE;
        static TextColor PURPLE = NamedTextColor.LIGHT_PURPLE;
        static TextColor WHITE = NamedTextColor.WHITE;
        static TextColor BROWN = TextColor.color(0x562C14);
    }

    public TeamSelectMenu(Miniworlds plugin, Player owner) {
        this.inventory = plugin.getServer().createInventory(this, 18, Component.text("Select Nation"));
        inventory.setContents(new ItemStack[] {
                new ItemStack(Material.RED_DYE) {{ editMeta(meta -> meta.displayName(Component.text("Red Nation", Colors.RED).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD))); }},
                new ItemStack(Material.ORANGE_DYE) {{ editMeta(meta -> meta.displayName(Component.text("Orange Nation", Colors.ORANGE).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD))); }}
        });

        this.owner = owner;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void activate(int slot) {
        if(slot < 9) {
            owner.getPersistentDataContainer().set(Miniworlds.Key.TEAM, PersistentDataType.INTEGER, slot);
            if (Capitol.getCapitolByTeam(slot) == null)
                Structure.giveItem(owner, Capitol.class);
            inventory.close();
        } else {
            Capitol capitol = Capitol.getCapitolByTeam(slot - 9);
            if (capitol != null) owner.teleport(capitol.getLocation().add(new Vector(0,5,0)));
        }
    }
}


