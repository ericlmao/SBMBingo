package games.negative.bingo.task;

import games.negative.bingo.api.BingoAPI;
import games.negative.bingo.api.model.BingoGame;
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

        BingoAPI.getInstance().getGameManager().stop();
    }
}
