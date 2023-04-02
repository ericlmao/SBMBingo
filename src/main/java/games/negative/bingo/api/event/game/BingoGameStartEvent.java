package games.negative.bingo.api.event.game;

import games.negative.bingo.api.model.BingoGame;

public class BingoGameStartEvent extends BingoGameEvent {

    public BingoGameStartEvent(BingoGame game) {
        super(game);
    }
}
