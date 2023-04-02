package games.negative.bingo.task;

import games.negative.bingo.api.event.game.BingoGameEndEvent;
import games.negative.bingo.api.model.BingoGame;
import games.negative.framework.event.Events;
import org.bukkit.scheduler.BukkitRunnable;

public class BingoGameTask extends BukkitRunnable {

    private final BingoGame game;
    public BingoGameTask(BingoGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (System.currentTimeMillis() < (game.getStarted() + game.getTimeLimit()))
            return;

        BingoGameEndEvent event = new BingoGameEndEvent(game, BingoGameEndEvent.Cause.TIMEOUT);
        Events.call(event);

        cancel();
    }
}
