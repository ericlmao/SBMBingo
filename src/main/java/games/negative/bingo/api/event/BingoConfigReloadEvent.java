package games.negative.bingo.api.event;

import games.negative.framework.event.PluginEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class BingoConfigReloadEvent extends PluginEvent {

    private final FileConfiguration config;

    public BingoConfigReloadEvent(@NotNull FileConfiguration config) {
        this.config = config;
    }

    @NotNull
    public FileConfiguration getConfig() {
        return config;
    }
}
