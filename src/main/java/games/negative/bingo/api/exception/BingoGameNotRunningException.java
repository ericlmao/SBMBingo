package games.negative.bingo.api.exception;

public class BingoGameNotRunningException extends RuntimeException {

    public BingoGameNotRunningException() {
        super("No game is currently running");
    }
}
