package games.negative.bingo.api.event.game;

import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BingoGameEndEvent extends BingoGameEvent {

    private final Cause cause;
    private BingoTeam winner;
    private CommandSender canceler;

    public BingoGameEndEvent(BingoGame game) {
        super(game);
        this.cause = Cause.UNKNOWN;
    }

    public BingoGameEndEvent(BingoGame game, Cause cause) {
        super(game);
        this.cause = cause;
    }

    public BingoGameEndEvent(BingoGame game, BingoTeam winner) {
        super(game);
        this.cause = Cause.WIN;
        this.winner = winner;
    }

    public BingoGameEndEvent(BingoGame game, CommandSender canceler) {
        super(game);
        this.canceler = canceler;
        this.cause = Cause.CANCELLED;
    }

    /**
     * Get the {@link Cause} of the game ending
     *
     * @return The cause
     */
    @NotNull
    public Cause getCause() {
        return cause;
    }

    /**
     * Get the winning team, if the game was won
     *
     * @return The winning team, or null if the game was not won
     * @throws NullPointerException if the game was not won
     */
    @Nullable
    public BingoTeam getWinner() {
        return winner;
    }

    /**
     * Get the {@link CommandSender} that cancelled the game, if the game was cancelled
     *
     * @return The canceler, or null if the game was not cancelled
     * @throws NullPointerException if the game was not cancelled
     */
    @Nullable
    public CommandSender getCanceler() {
        return canceler;
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
