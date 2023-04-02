package games.negative.bingo.core.provider;

import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.event.game.BingoGameEndEvent;
import games.negative.bingo.api.event.game.BingoGameStartEvent;
import games.negative.bingo.api.exception.BingoGameNotRunningException;
import games.negative.bingo.api.exception.BingoGameRunningException;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.core.structure.BingoGameImpl;
import games.negative.framework.event.Events;
import org.jetbrains.annotations.Nullable;

public class BingoGameManagerProvider implements BingoGameManager {

    private BingoGame game;


    @Override
    public @Nullable BingoGame getActiveGame() {
        return game;
    }

    @Override
    public void start(long duration) {
        if (game != null) {
            // Game is currently active, throw exception
            throw new BingoGameRunningException();
        }

        this.game = new BingoGameImpl(System.currentTimeMillis(), duration);

        BingoGameStartEvent event = new BingoGameStartEvent(game);
        Events.call(event);
    }

    @Override
    public void stop() {
        if (game == null) {
            // No game is currently active, throw exception
            throw new BingoGameNotRunningException();
        }

        BingoGameEndEvent event = new BingoGameEndEvent(game);
        Events.call(event);

        game.getTask().cancel();
        game = null;
    }
}
