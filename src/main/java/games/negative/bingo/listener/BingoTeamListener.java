package games.negative.bingo.listener;

import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.BingoTeamJoinEvent;
import games.negative.bingo.api.event.BingoTeamQuitEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.scoreboard.Team;

public class BingoTeamListener implements Listener {

    private final BingoTeamManager manager;

    public BingoTeamListener(BingoTeamManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onTeamJoin(BingoTeamJoinEvent event) {
        Player player = event.getPlayer();
        BingoTeam team = event.getTeam();
        ChatColor color = team.getBingoColor().getColor();

        Team minecraftTeam = team.getMinecraftTeam();
        minecraftTeam.addEntry(player.getName());

        player.setGlowing(true);
    }

    @EventHandler
    public void onTeamQuit(BingoTeamQuitEvent event) {
        Player player = event.getPlayer();
        BingoTeam team = event.getTeam();
        ChatColor color = team.getBingoColor().getColor();

        Team minecraftTeam = team.getMinecraftTeam();
        minecraftTeam.removeEntry(player.getName());

        player.setGlowing(false);
    }

    @EventHandler
    public void onCompleteGoal(BingoTeamCompleteGoalEvent event) {
        BingoTeam team = event.getTeam();
        BingoGoal goal = event.getGoal();


    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;


    }
}
