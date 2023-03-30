package games.negative.bingo.task;

import games.negative.bingo.api.event.game.BingoGameEndEvent;
import games.negative.framework.event.Events;
import org.bukkit.scheduler.BukkitRunnable;

public class BingoGameTask extends BukkitRunnable {

    private final long limit;
    private final long started;

    public BingoGameTask(long limit) {
        this.limit = limit;
        this.started = System.currentTimeMillis();
    }

    @Override
    public void run() {
        if (System.currentTimeMillis() < (started + limit))
            return;

        BingoGameEndEvent event = new BingoGameEndEvent(BingoGameEndEvent.Cause.TIMEOUT);
        Events.call(event);

        cancel();
    }
}
