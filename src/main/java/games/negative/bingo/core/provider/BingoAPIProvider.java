package games.negative.bingo.core.provider;

import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.BingoTeamManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BingoAPIProvider extends BingoAPI {

    private final BingoTeamManager teamManager;

    public BingoAPIProvider(JavaPlugin plugin, FileConfiguration config) {
        teamManager = new BingoTeamManagerProvider(plugin, config);
    }

    @Override
    public BingoTeamManager getTeamManager() {
        return teamManager;
    }
}
