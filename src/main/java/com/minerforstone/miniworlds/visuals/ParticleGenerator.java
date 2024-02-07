package com.minerforstone.miniworlds.visuals;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleGenerator {
    public static void displayRing(Location location, ParticleBuilder particleBuilder, int radius) {
        for (int deg = 0; deg < 359; deg += 5) {
            Location particleLocation = location.clone().add(new Vector(radius * Math.cos(Math.toRadians(deg)), 0, radius * Math.sin(Math.toRadians(deg))));
            particleBuilder.location(particleLocation).spawn();
        }
    }
}
