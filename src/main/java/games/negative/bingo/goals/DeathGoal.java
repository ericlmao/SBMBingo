package games.negative.bingo.goals;

import games.negative.bingo.api.event.team.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.team.BingoTeamGoalProgressEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.Events;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathGoal extends BingoGoal {

    private final EntityDamageEvent.DamageCause cause;

    public DeathGoal(ConfigurationSection section) {
        super(section);

        // If the goal-cause is ANY, then we don't care what the cause is.
        String raw = section.getString("goal-cause", "ANY");
        this.cause = (raw.equals("ANY")) ? null : EntityDamageEvent.DamageCause.valueOf(raw);
    }

    public DeathGoal(String key, BingoGoalType type, int amount, String displayName, Material displayMaterial, EntityDamageEvent.DamageCause cause) {
        super(key, type, amount, displayName, displayMaterial);
        this.cause = cause;
    }

    @Override
    public void onDeath(BingoTeam team, EntityDeathEvent event) {
        Player player = (Player) event.getEntity();
        if (cause == null) {
            increment(team);
            return;
        }

        EntityDamageEvent last = player.getLastDamageCause();
        if (last == null) return;

        if (last.getCause() != cause) return;

        increment(team);
    }

    private void increment(BingoTeam team) {
        int preProgress = team.getProgress(this);
        if (preProgress >= getAmount()) return;

        team.addProgress(this, 1);

        int progress = team.getProgress(this);

        BingoTeamGoalProgressEvent progressEvent = new BingoTeamGoalProgressEvent(team, this, preProgress, progress);
        Events.call(progressEvent);

        if (progress < getAmount()) return;

        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }
}
