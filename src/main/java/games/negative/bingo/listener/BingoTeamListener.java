package games.negative.bingo.listener;

import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.BingoConfigReloadEvent;
import games.negative.bingo.api.event.team.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.team.BingoTeamGoalProgressEvent;
import games.negative.bingo.api.event.team.BingoTeamJoinEvent;
import games.negative.bingo.api.event.team.BingoTeamQuitEvent;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.Locale;
import games.negative.bingo.core.util.ActionBar;
import games.negative.bingo.core.util.TextUtil;
import games.negative.framework.util.Utils;
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
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BingoTeamListener implements Listener {

    private final BingoTeamManager manager;
    private final BingoGoalManager goalManager;
    private final BingoGameManager gameManager;

    public BingoTeamListener(BingoTeamManager manager, BingoGoalManager goalManager, BingoGameManager gameManager) {
        this.manager = manager;
        this.goalManager = goalManager;
        this.gameManager = gameManager;
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
    public void onProgressGoal(BingoTeamGoalProgressEvent event) {
        BingoTeam team = event.getTeam();
        BingoGoal goal = event.getGoal();

        int progress = event.getNewProgress();
        int max = goal.getAmount();

        String name = TextUtil.stripColor(goal.getDisplay()).toUpperCase();

        for (Player player : team.getOnlinePlayers()) {

            String percent = Utils.decimalFormat((double) progress / max * 100);
            String text = "&a&l" + name + " &7&l| &e" + progress + " &8/ &e" + max + " &6&l" + percent + "%";

            ActionBar.send(player, text);
        }
    }

    @EventHandler
    public void onCompleteGoal(BingoTeamCompleteGoalEvent event) {
        BingoTeam team = event.getTeam();
        BingoGoal goal = event.getGoal();

        int required = goalManager.getBingoGoals().size();
        int completed = getCompletedGoals(team);


        BingoColor bingoColor = team.getBingoColor();
        String goalName = TextUtil.stripColor(goal.getDisplay()).toUpperCase();

        Locale.BINGO_GOAL_COMPLETED.replace("%team%", bingoColor.getColor() + bingoColor.getRealPeopleWord())
                        .replace("%goal%", goalName)
                .replace("%completed%", Utils.decimalFormat(completed))
                .replace("%total%", Utils.decimalFormat(required))
                .broadcast();

        // Check if team has all goals completed, if so then they win
        if (completed < required)
            return;

        // Team has won
        gameManager.stop(team);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        BingoTeam team = manager.getUserTeam(player.getUniqueId());
        if (team == null)
            return;

        BingoGame game = gameManager.getActiveGame();
        if (game == null)
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

        BingoGame game = gameManager.getActiveGame();
        if (game == null)
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

        BingoGame game = gameManager.getActiveGame();
        if (game == null)
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

        BingoGame game = gameManager.getActiveGame();
        if (game == null)
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

        BingoGame game = gameManager.getActiveGame();
        if (game == null)
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

        BingoGame game = gameManager.getActiveGame();
        if (game == null)
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

    public int getCompletedGoals(@NotNull BingoTeam team) {
        Map<BingoGoal, Integer> progresses = team.getProgresses();
        int completed = 0;

        for (Map.Entry<BingoGoal, Integer> entry : progresses.entrySet()) {
            BingoGoal key = entry.getKey();
            Integer value = entry.getValue();

            if (value >= key.getAmount()) {
                completed++;
            }
        }
        return completed;
    }
}
