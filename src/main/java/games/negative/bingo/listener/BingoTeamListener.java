package games.negative.bingo.listener;

import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.BingoConfigReloadEvent;
import games.negative.bingo.api.event.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.BingoTeamJoinEvent;
import games.negative.bingo.api.event.BingoTeamQuitEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.scoreboard.Team;

public class BingoTeamListener implements Listener {

    private final BingoTeamManager manager;
    private final BingoGoalManager goalManager;

    public BingoTeamListener(BingoTeamManager manager, BingoGoalManager goalManager) {
        this.manager = manager;
        this.goalManager = goalManager;
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

        for (BingoGoal goal : goalManager.getBingoGoals()) {
            goal.onPlayerPickup(team, event);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;

        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;

        for (BingoGoal goal : goalManager.getBingoGoals()) {
            goal.onCraft(team, event);
        }
    }

    @EventHandler
    public void onInventoryClick(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;

        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;

        for (BingoGoal goal : goalManager.getBingoGoals()) {
            goal.onInventoryClick(team, event);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;

        for (BingoGoal goal : goalManager.getBingoGoals()) {
            goal.onDeath(team, event);
        }
    }

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;

        for (BingoGoal goal : goalManager.getBingoGoals()) {
            goal.onPotionEffect(team, event);
        }
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;

        for (BingoGoal goal : goalManager.getBingoGoals()) {
            goal.onAdvancement(team, event);
        }
    }

    @EventHandler
    public void onReload(BingoConfigReloadEvent event) {
        FileConfiguration config = event.getConfig();
        goalManager.onReload(config);
        manager.onReload(config);
    }
}
