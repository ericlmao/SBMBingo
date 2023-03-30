package games.negative.bingo.api;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

public interface BingoGameManager {

    long getTimeStarted();

    long getTimeLimit();

    @Nullable
    BukkitTask getGameTask();

    void start();

    void end();
}
