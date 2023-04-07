package games.negative.bingo.api.event.team;

import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import org.jetbrains.annotations.NotNull;

public class BingoTeamGoalProgressEvent extends BingoTeamGoalEvent {

    private final int oldProgress;
    private final int newProgress;

    public BingoTeamGoalProgressEvent(@NotNull BingoTeam team, @NotNull BingoGoal goal, int oldProgress, int newProgress) {
        super(team, goal);
        this.oldProgress = oldProgress;
        this.newProgress = newProgress;
    }

    public int getOldProgress() {
        return oldProgress;
    }

    public int getNewProgress() {
        return newProgress;
    }
}
