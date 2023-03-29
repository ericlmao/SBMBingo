package games.negative.bingo.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class BingoAPI {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static BingoAPI instance;

    public abstract BingoTeamManager getTeamManager();

    public abstract BingoGoalManager getGoalManager();
}
