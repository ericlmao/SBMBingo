package games.negative.bingo.api.exception;

public class BingoGameRunningException extends RuntimeException {

    public BingoGameRunningException() {
        super("A game is already running.");
    }
}
