package games.negative.bingo.goals;

import games.negative.bingo.api.event.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.Events;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillGoal extends BingoGoal {

    private final EntityType type;
    public KillGoal(ConfigurationSection section) {
        super(section);
        this.type = EntityType.valueOf(section.getString("goal-entity", "ZOMBIE"));
    }

    public KillGoal(String key, BingoGoalType type, int amount, EntityType entityType) {
        super(key, type, amount);
        this.type = entityType;
    }

    @Override
    public void onDeath(BingoTeam team, EntityDeathEvent event) {
        EntityType type = event.getEntity().getType();
        if (this.type != type)
            return;

        team.addProgress(this, 1);

        int progress = team.getProgress(this);
        if (progress < getAmount())
            return;

        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }
}
