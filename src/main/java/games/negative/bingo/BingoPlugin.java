package games.negative.bingo;

import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.provider.BingoAPIProvider;
import games.negative.framework.BasePlugin;
import org.bukkit.scoreboard.Team;

import java.util.Collection;

public class BingoPlugin extends BasePlugin {

    private BingoAPI api;

    @Override
    public void onEnable() {
        super.onEnable();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        this.api = new BingoAPIProvider(this, getConfig());
    }

    @Override
    public void onDisable() {
        super.onDisable();

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
}
