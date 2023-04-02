package games.negative.bingo.core.provider;

import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BingoAPIProvider extends BingoAPI {

    private final BingoTeamManager teamManager;
    private final BingoGoalManager goalManager;
    private final BingoGameManager gameManager;

    public BingoAPIProvider(JavaPlugin plugin, FileConfiguration config) {
        this.teamManager = new BingoTeamManagerProvider(plugin, config);
        this.goalManager = new BingoGoalManagerProvider(plugin, config);
        this.gameManager = new BingoGameManagerProvider();
    }

    @Override
    public BingoTeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public BingoGoalManager getGoalManager() {
        return goalManager;
    }

    @Override
    public BingoGameManager getGameManager() {
        return gameManager;
    }
}
