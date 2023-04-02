package games.negative.bingo.listener;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.BingoConfigReloadEvent;
import games.negative.bingo.api.event.game.BingoGameEndEvent;
import games.negative.bingo.api.event.game.BingoGameStartEvent;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.util.TeamUtil;
import games.negative.framework.util.TimeUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;

public class BingoGameListener implements Listener {

    private final BingoPlugin plugin;
    private final BingoGameManager gameManager;
    private final BingoTeamManager teamManager;
    private final BingoGoalManager goalManager;

    public BingoGameListener(BingoPlugin plugin, BingoGameManager gameManager, BingoTeamManager teamManager, BingoGoalManager goalManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.teamManager = teamManager;
        this.goalManager = goalManager;
    }

    @EventHandler
    public void onStart(BingoGameStartEvent event) {
        Collection<BingoTeam> teams = teamManager.getTeams();
        Collection<Player> all = TeamUtil.getAllPlayers(teams);
        for (Player player : all) {
            player.sendMessage("Game started");
        }

    }

    @EventHandler
    public void onWin(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.WIN)
            return;

        BingoTeam winner = event.getWinner();
        // This is the winner team, do something with it
    }

    @EventHandler
    public void onCancel(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.CANCELLED)
            return;

        CommandSender canceler = event.getCanceler();
        // This is the canceler, do something with it
    }

    @EventHandler
    public void onTimeout(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.TIMEOUT)
            return;

        // This is a timeout, do something with it
    }

    @EventHandler
    public void onEnd(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.UNKNOWN)
            return;

        // Bingo game ended for an unknown reason, do something with it

    }

    @EventHandler
    public void onReload(BingoConfigReloadEvent event) {
        FileConfiguration config = event.getConfig();

        String formatted = config.getString("max-game-duration", "1h");
        long duration = TimeUtil.longFromString(formatted);
        this.plugin.setGameDuration(duration);
    }
}
