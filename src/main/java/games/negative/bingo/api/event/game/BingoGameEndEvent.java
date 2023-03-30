package games.negative.bingo.api.event.game;

import games.negative.bingo.api.BingoGameManager;
import org.jetbrains.annotations.NotNull;

public class BingoGameEndEvent extends BingoGameEvent {

    private Cause cause;
    public BingoGameEndEvent(@NotNull BingoGameManager gameManager, @NotNull Cause cause) {
        super(gameManager);
        this.cause = cause;
    }

    @NotNull
    public Cause getCause() {
        return cause;
    }

    public void setCause(@NotNull Cause cause) {
        this.cause = cause;
    }

    public enum Cause {
        /**
         * The game was won by a team
         */
        WIN,

        /**
         * The game has run out of time
         */
        TIMEOUT,

        /**
         * The game was cancelled by some other means
         */
        CANCELLED,

        /**
         * The game was ended by a plugin
         */
        UNKNOWN,
    }
}
