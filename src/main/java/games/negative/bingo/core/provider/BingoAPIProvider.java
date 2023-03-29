package games.negative.bingo.core.provider;

import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BingoAPIProvider extends BingoAPI {

    private final BingoTeamManager teamManager;
    private final BingoGoalManager goalManager;

    public BingoAPIProvider(JavaPlugin plugin, FileConfiguration config) {
        this.teamManager = new BingoTeamManagerProvider(plugin, config);
        this.goalManager = new BingoGoalManagerProvider(plugin, config);
    }

    @Override
    public BingoTeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public BingoGoalManager getGoalManager() {
        return goalManager;
    }
}
