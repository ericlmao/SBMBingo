package games.negative.bingo.api.event.game;

import games.negative.bingo.api.BingoGameManager;
import org.jetbrains.annotations.NotNull;

public class BingoGameStartEvent extends BingoGameEvent {
    public BingoGameStartEvent(@NotNull BingoGameManager gameManager) {
        super(gameManager);
    }
}
