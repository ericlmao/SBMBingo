package games.negative.bingo.api.event.game;

import games.negative.bingo.api.model.BingoGame;
import games.negative.framework.event.PluginEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BingoGameEvent extends PluginEvent {

    private final BingoGame game;

    public BingoGameEvent(BingoGame game) {
        this.game = game;
    }

    @NotNull
    public BingoGame getGame() {
        return game;
    }
}
