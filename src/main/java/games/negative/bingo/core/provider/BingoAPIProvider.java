package games.negative.bingo.core.provider;

import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.BingoTeamManager;
import org.bukkit.configuration.file.FileConfiguration;

public class BingoAPIProvider extends BingoAPI {

    private final BingoTeamManager teamManager;

    public BingoAPIProvider(FileConfiguration config) {
        teamManager = new BingoTeamManagerProvider(config);
    }

    @Override
    public BingoTeamManager getTeamManager() {
        return teamManager;
    }
}
