package games.negative.bingo.api.event.biome;

import games.negative.framework.event.PluginEvent;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BiomeChangeEvent extends PluginEvent {

    private final Player player;
    private final Biome from;
    private final Biome to;

    public BiomeChangeEvent(@NotNull Player player, @NotNull Biome from, @NotNull Biome to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @NotNull
    public Biome getFrom() {
        return from;
    }

    @NotNull
    public Biome getTo() {
        return to;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }
}
