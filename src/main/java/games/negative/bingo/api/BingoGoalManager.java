package games.negative.bingo.api;

import games.negative.bingo.api.model.goal.BingoGoal;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;

public interface BingoGoalManager {

    Collection<BingoGoal> getBingoGoals();

    void addBingoGoal(BingoGoal goal);

    void removeBingoGoal(BingoGoal goal);

    void shuffleGoals();

    void onReload(FileConfiguration config);

}
