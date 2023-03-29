package games.negative.bingo.api.model.goal;

import games.negative.framework.key.Keyd;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public abstract class BingoGoal implements Keyd<String> {

    private final String key;
    private final BingoGoalType type;
    private final int amount;

    public BingoGoal(ConfigurationSection section) {
        this.key = section.getName();
        this.type = BingoGoalType.valueOf(section.getString("type"));
        this.amount = section.getInt("amount");
    }

    public BingoGoal(String key, BingoGoalType type, int amount) {
        this.key = key;
        this.type = type;
        this.amount = amount;
    }

    @NotNull
    @Override
    public String getKey() {
        return key;
    }

    public BingoGoalType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void setKey(@NotNull String s) {
        throw new UnsupportedOperationException("Cannot set key of BingoGoal");
    }
}
