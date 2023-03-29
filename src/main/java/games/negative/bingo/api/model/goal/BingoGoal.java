package games.negative.bingo.api.model.goal;

import games.negative.framework.key.Keyd;

public interface BingoGoal extends Keyd<String> {

    BingoGoalType getType();

    int getAmount();
    
}
