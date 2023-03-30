package games.negative.bingo.api.event.team;

import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import org.jetbrains.annotations.NotNull;

public class BingoTeamCompleteGoalEvent extends BingoTeamEvent {

    private final BingoGoal goal;
    public BingoTeamCompleteGoalEvent(@NotNull BingoTeam team, @NotNull BingoGoal goal) {
        super(team);
        this.goal = goal;
    }

    @NotNull
    public BingoGoal getGoal() {
        return goal;
    }
}
