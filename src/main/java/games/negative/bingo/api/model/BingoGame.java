package games.negative.bingo.api.model;

import games.negative.bingo.task.BingoGameTask;

public interface BingoGame {

    long getStarted();

    long getTimeLimit();

    BingoGameTask getTask();

}
