package games.negative.bingo.listener;

import games.negative.bingo.api.event.biome.BiomeChangeEvent;
import games.negative.framework.event.Events;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BiomeListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null) return;

        // Player has not moved blocks
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) return;

        World fromWorld = from.getWorld();
        if (fromWorld == null) return;

        World toWorld = to.getWorld();
        if (toWorld == null) return;

        Biome fromBiome = fromWorld.getBiome(from);
        Biome toBiome = toWorld.getBiome(to);
        if (fromBiome == toBiome)
            return;

        BiomeChangeEvent changeEvent = new BiomeChangeEvent(event.getPlayer(), fromBiome, toBiome);
        Events.call(changeEvent);
    }
}
