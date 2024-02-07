package com.minerforstone.miniworlds.logic;

import com.minerforstone.miniworlds.Miniworlds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class TeamSelectMenu implements InventoryHolder{
    private final Inventory inventory;
    private final Player owner;

    public TeamSelectMenu(Miniworlds plugin, Player owner) {
        this.inventory = plugin.getServer().createInventory(this, 18);
        inventory.setContents(new ItemStack[] {
                new ItemStack(Material.RED_DYE) {{ editMeta(meta -> meta.displayName(Component.text("Red Nation").decoration(TextDecoration.ITALIC, false))); }},
                new ItemStack(Material.ORANGE_DYE) {{ editMeta(meta -> meta.displayName(Component.text("Orange Nation").decoration(TextDecoration.ITALIC, false))); }}
        });

        this.owner = owner;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void activate(int slot) {
        Bukkit.broadcast(Component.text(owner.getName() + " has clicked on slot " + slot));
    }

    private static final String[] teams = new String[] {
            "red",
            "orange",
    };
}


