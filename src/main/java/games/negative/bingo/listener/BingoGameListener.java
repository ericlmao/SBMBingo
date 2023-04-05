package games.negative.bingo.listener;

import com.google.common.collect.Lists;
import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.BingoConfigReloadEvent;
import games.negative.bingo.api.event.game.BingoGameEndEvent;
import games.negative.bingo.api.event.game.BingoGameStartEvent;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.util.ActionBar;
import games.negative.bingo.core.util.TeamUtil;
import games.negative.framework.util.TimeUtil;
import games.negative.framework.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
            ActionBar.send(player, "&a&lGAME STARTED");
            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        }
    }

    @EventHandler
    public void onWin(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.WIN)
            return;

        BingoGame game = event.getGame();

        long started = game.getStarted();
        long now = System.currentTimeMillis();

        String formatted = TimeUtil.format(now, started);

        BingoTeam winner = event.getWinner();
        // This is the winner team, do something with it
        assert winner != null;

        Utils.broadcast("&aWinner: &f" + winner.getBingoColor().getRealPeopleWord());
        Utils.broadcast("&aGame lasted: &f" + formatted);

        clearTeams();
    }

    private void clearTeams() {
        for (BingoTeam team : teamManager.getTeams()) {
            List<UUID> toRemove = Lists.newArrayList(team.getMembers());

            Team minecraftTeam = team.getMinecraftTeam();

            for (UUID uuid : toRemove) {
                team.removeMember(uuid);
                teamManager.removeUserTeam(uuid);

                OfflinePlayer offline = Bukkit.getOfflinePlayer(uuid);
                if (offline instanceof Player online)
                    online.setGlowing(false);

                String name = offline.getName();
                if (name == null)
                    continue;

                minecraftTeam.removeEntry(name);
            }

            team.clearProgress();
        }
    }

    @EventHandler
    public void onCancel(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.CANCELLED)
            return;

        CommandSender canceler = event.getCanceler();
        // This is the canceler, do something with it

        clearTeams();
    }

    @EventHandler
    public void onTimeout(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.TIMEOUT)
            return;

        // This is a timeout, do something with it
        clearTeams();
    }

    @EventHandler
    public void onEnd(BingoGameEndEvent event) {
        if (event.getCause() != BingoGameEndEvent.Cause.UNKNOWN)
            return;

        // Bingo game ended for an unknown reason, do something with it
        clearTeams();
    }

    @EventHandler
    public void onReload(BingoConfigReloadEvent event) {
        FileConfiguration config = event.getConfig();

        String formatted = config.getString("max-game-duration", "1h");
        long duration = TimeUtil.longFromString(formatted);
        this.plugin.setGameDuration(duration);
    }
}
