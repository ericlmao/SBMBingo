package games.negative.bingo.core.structure;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.task.BingoGameTask;

public class BingoGameImpl implements BingoGame {

    private final long started;
    private final long timeLimit;
    private final BingoGameTask task;

    public BingoGameImpl(long started, long timeLimit) {
        this.started = started;
        this.timeLimit = timeLimit;
        this.task = new BingoGameTask(this);
        this.task.runTaskTimer(BingoPlugin.getInst(), 0, 20);
    }

    @Override
    public long getStarted() {
        return started;
    }

    @Override
    public long getTimeLimit() {
        return timeLimit;
    }

    @Override
    public BingoGameTask getTask() {
        return task;
    }
}
