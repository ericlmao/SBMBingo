package games.negative.bingo.core.provider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.core.util.TextUtil;
import games.negative.bingo.goals.BiomeGoal;
import games.negative.bingo.goals.CollectItemGoal;
import games.negative.bingo.goals.KillGoal;
import games.negative.bingo.goals.PotionEffectGoal;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BingoGoalManagerProvider implements BingoGoalManager {

    private final JavaPlugin plugin;
    private final List<BingoGoal> goals;

    public BingoGoalManagerProvider(JavaPlugin plugin, FileConfiguration config) {
        this.goals = Lists.newArrayList();
        this.plugin = plugin;
        onReload(config);
    }

    @Override
    public Collection<BingoGoal> getBingoGoals() {
        return goals;
    }

    @Override
    public void addBingoGoal(BingoGoal goal) {
        goals.add(goal);
    }

    @Override
    public void removeBingoGoal(BingoGoal goal) {
        goals.remove(goal);
    }

    @Override
    public void onReload(FileConfiguration config) {
        this.goals.clear();

        ConfigurationSection goals = config.getConfigurationSection("goals");
        Preconditions.checkNotNull(goals, "goals section is null");

        for (String key : goals.getKeys(false)) {
            ConfigurationSection section = goals.getConfigurationSection(key);
            Preconditions.checkNotNull(section, "goal section " + key + " is null");

            BingoGoalType type = BingoGoalType.valueOf(section.getString("goal-type"));
            BingoGoal goal;
            switch (type) {
                case COLLECT -> goal = new CollectItemGoal(section);
                case KILL -> goal = new KillGoal(section);
                case POTION_EFFECT -> goal = new PotionEffectGoal(section);
                case FIND_BIOME -> goal = new BiomeGoal(section);

                default -> throw new IllegalStateException("Unexpected value: " + type);
            }

            this.goals.add(goal);

            plugin.getLogger().info("Loaded goal " + TextUtil.stripColor(goal.getDisplay()));
        }

        Collections.shuffle(this.goals);
    }
}
