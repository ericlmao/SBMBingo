package games.negative.bingo.goals;

import games.negative.bingo.api.event.team.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.team.BingoTeamGoalProgressEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.Events;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillGoal extends BingoGoal {

    private final EntityType type;

    public KillGoal(ConfigurationSection section) {
        super(section);
        this.type = EntityType.valueOf(section.getString("goal-entity", "ZOMBIE"));
    }

    public KillGoal(String key, BingoGoalType type, int amount, EntityType entityType, String displayName, Material displayMaterial) {
        super(key, type, amount, displayName, displayMaterial);
        this.type = entityType;
    }

    @Override
    public void onDeath(BingoTeam team, EntityDeathEvent event) {
        EntityType type = event.getEntity().getType();
        if (this.type != type)
            return;

        int preProgress = team.getProgress(this);
        if (preProgress >= getAmount())
            return;

        team.addProgress(this, 1);

        int progress = team.getProgress(this);

        BingoTeamGoalProgressEvent progressEvent = new BingoTeamGoalProgressEvent(team, this, preProgress, progress);
        Events.call(progressEvent);

        if (progress < getAmount())
            return;

        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }
}
