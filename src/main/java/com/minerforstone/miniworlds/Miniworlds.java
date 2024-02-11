package com.minerforstone.miniworlds;

import com.minerforstone.miniworlds.commands.*;
import com.minerforstone.miniworlds.events.EntityEventListener;
import com.minerforstone.miniworlds.events.InventoryEventListener;
import com.minerforstone.miniworlds.events.PlayerEventListener;
import com.minerforstone.miniworlds.logic.Help;
import com.minerforstone.miniworlds.registry.Registry;
import com.minerforstone.miniworlds.world.structures.Capitol;
import com.minerforstone.miniworlds.world.structures.Structure;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class Miniworlds extends JavaPlugin implements Listener {
    private static Miniworlds plugin;
    private World defaultWorld;
    private final BukkitScheduler scheduler = getServer().getScheduler();
    public static final String NAMESPACE = "miniworlds";
    private static PersistentDataContainer PDC;

    public static Miniworlds getPlugin() {
        return plugin;
    }
    public static PersistentDataContainer getPDC() {
        return PDC;
    }

    public static class Key {
        public static final NamespacedKey
            TEAM = new NamespacedKey(Miniworlds.NAMESPACE, "team");
    }

    // Setup
    @Override
    public void onEnable() {
        plugin = this;
        defaultWorld = getServer().getWorlds().get(0);
        PDC = defaultWorld.getPersistentDataContainer();
        Registry<Structure> structureRegistry = Structure.REGISTRY;

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);

        // Structure registry
        structureRegistry.register(Capitol.class, 1, "Capitol");

        getLogger().info("Successfully registered " + structureRegistry.getRegistrySize() + " structures");

        // Non-op commands
        Objects.requireNonNull(getCommand("craft")).setExecutor(new ShowCraftInterface());
        Objects.requireNonNull(getCommand("teams")).setExecutor(new ShowTeamSelect());

        // Op commands
        Objects.requireNonNull(getCommand("removeteam")).setExecutor(new RemoveTeam());
        Objects.requireNonNull(getCommand("give")).setExecutor(new Give());
        Objects.requireNonNull(getCommand("delete")).setExecutor(new DeleteNation());

        defaultWorld.setGameRule(GameRule.DO_TILE_DROPS, false);
        defaultWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        defaultWorld.setDifficulty(Difficulty.EASY);

        scheduler.runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, PotionEffect.INFINITE_DURATION, 0, true, false));
            }
        }, 0, 200);

        scheduler.runTaskTimer(this, Help::displayTip, 20 * 15, 20 * 600);
    }

    private void setupPlayer(PlayerEvent event) {
        event.getPlayer().setAllowFlight(true);
        event.getPlayer().setInvulnerable(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().getPersistentDataContainer().has(Miniworlds.Key.TEAM)) {
            Help.displayWelcome(event.getPlayer());
        }
        setupPlayer(event);
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        setupPlayer(event);
    }
}
