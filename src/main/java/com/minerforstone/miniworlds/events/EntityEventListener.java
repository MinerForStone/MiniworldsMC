package com.minerforstone.miniworlds.events;

import com.destroystokyo.paper.ParticleBuilder;
import com.minerforstone.miniworlds.Miniworlds;
import com.minerforstone.miniworlds.visuals.ParticleGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.util.Vector;

public class EntityEventListener implements Listener {
    // Spawn events
    @EventHandler
    public void onArmorStandSpawned(EntitySpawnEvent event) {
        if (event.getEntity() instanceof ArmorStand armorStand) {
            armorStand.setArms(true);
            armorStand.setSmall(true);

            Miniworlds.getPlugin().getServer().getScheduler().runTaskTimer(Miniworlds.getPlugin(), (task) -> {
                if (Bukkit.getServer().getEntity(event.getEntity().getUniqueId()) == null) {
                    task.cancel();
                    return;
                }
                ParticleGenerator.displayRing(event.getLocation(), new ParticleBuilder(Particle.REDSTONE).color(0, 255, 0).allPlayers(), 5);
            }, 0, 2);
        }
    }

    @EventHandler
    public void onZombieSpawned(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            zombie.setBaby();
            zombie.setShouldBurnInDay(false);
            zombie.setSilent(true);
            zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

            zombie.getPathfinder().moveTo(zombie.getLocation().add(new Vector(3,0,0)));
        }
    }
}
