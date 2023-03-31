package games.negative.bingo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.commands.main.CommandBingo;
import games.negative.bingo.core.Locale;
import games.negative.bingo.core.provider.BingoAPIProvider;
import games.negative.bingo.listener.BingoCardListener;
import games.negative.bingo.listener.BingoTeamListener;
import games.negative.bingo.state.GameState;
import games.negative.framework.BasePlugin;
import games.negative.framework.json.JSONConfigManager;
import org.bukkit.scoreboard.Team;

import java.util.Collection;

public class BingoPlugin extends BasePlugin {

    private BingoAPI api;
    private GameState state;

    @Override
    public void onEnable() {
        super.onEnable();

        Locale.init(this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        JSONConfigManager json = getJSONConfigManager();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        this.state = json.loadOrCreate(getDataFolder().getPath(), "state.json", GameState.class, new GameState(), gson);

        this.api = new BingoAPIProvider(this, getConfig());

        BingoTeamManager teamManager = api.getTeamManager();
        BingoGoalManager goalManager = api.getGoalManager();

        registerListeners(
                new BingoTeamListener(teamManager, goalManager),
                new BingoCardListener(this, teamManager, goalManager)
        );

        registerCommands(
                new CommandBingo(teamManager, goalManager)
        );
    }

    @Override
    public void onDisable() {
        super.onDisable();
        saveState();

        Collection<BingoTeam> teams = api.getTeamManager().getTeams();
        for (BingoTeam team : teams) {
            Team minecraftTeam = team.getMinecraftTeam();
            if (minecraftTeam == null)
                continue;

            try {
                minecraftTeam.unregister();
            } catch (IllegalStateException e) {
                // Ignore
                getLogger().warning("Team for BingoTeam " + team.getBingoColor().toString() + " was already unregistered!");
                return;
            }
        }

        api.getTeamManager().onDisable();
    }

    public GameState getState() {
        return state;
    }

    public void saveState() {
        JSONConfigManager json = getJSONConfigManager();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        json.save(getDataFolder().getPath(), "state.json", state, gson);
    }
}
