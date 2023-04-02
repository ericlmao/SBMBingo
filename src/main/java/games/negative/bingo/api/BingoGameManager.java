package games.negative.bingo.api;

import games.negative.bingo.api.model.BingoGame;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public interface BingoGameManager {

    @Nullable
    BingoGame getActiveGame();

    void start(long duration);

    void stop();

    void stop(CommandSender canceler);


}
