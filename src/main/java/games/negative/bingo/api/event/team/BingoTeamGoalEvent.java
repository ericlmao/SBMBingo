package games.negative.bingo.api.event.team;

import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import org.jetbrains.annotations.NotNull;

public abstract class BingoTeamGoalEvent extends BingoTeamEvent {

    private final BingoGoal goal;

    public BingoTeamGoalEvent(@NotNull BingoTeam team, @NotNull BingoGoal goal) {
        super(team);
        this.goal = goal;
    }

    public BingoGoal getGoal() {
        return goal;
    }
}
