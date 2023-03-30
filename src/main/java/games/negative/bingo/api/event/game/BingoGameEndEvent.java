package games.negative.bingo.api.event.game;

import games.negative.bingo.api.BingoGameManager;
import org.jetbrains.annotations.NotNull;

public class BingoGameEndEvent extends BingoGameEvent {

    public BingoGameEndEvent(@NotNull BingoGameManager gameManager) {
        super(gameManager);
    }
}
