package games.negative.bingo.api.event.game;

import games.negative.bingo.api.BingoGameManager;
import games.negative.framework.event.PluginEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BingoGameEvent extends PluginEvent {

    private final BingoGameManager gameManager;

    public BingoGameEvent(@NotNull BingoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @NotNull
    public BingoGameManager getGameManager() {
        return gameManager;
    }
}
