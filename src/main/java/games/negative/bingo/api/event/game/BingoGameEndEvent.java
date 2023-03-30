package games.negative.bingo.api.event.game;

import games.negative.framework.event.PluginEvent;
import org.jetbrains.annotations.NotNull;

public class BingoGameEndEvent extends PluginEvent {

    private final Cause cause;

    public BingoGameEndEvent(@NotNull Cause cause) {
        this.cause = cause;
    }

    @NotNull
    public Cause getCause() {
        return cause;
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
