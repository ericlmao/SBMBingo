package games.negative.bingo.goals;

import games.negative.bingo.api.event.biome.BiomeChangeEvent;
import games.negative.bingo.api.event.team.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.team.BingoTeamGoalProgressEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.Events;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;

public class BiomeGoal extends BingoGoal {
    private final Biome biome;

    public BiomeGoal(ConfigurationSection section) {
        super(section);
        this.biome = Biome.valueOf(section.getString("goal-biome"));
    }

    public BiomeGoal(String key, BingoGoalType type, String displayName, Material displayMaterial, Biome biome) {
        super(key, type, 1, displayName, displayMaterial);
        this.biome = biome;
    }

    @Override
    public void onBiomeChange(BingoTeam team, BiomeChangeEvent event) {
        Biome biome = event.getTo();
        if (biome != this.biome)
            return;

        int preProgress = team.getProgress(this);
        if (preProgress >= getAmount())
            return;

        team.addProgress(this, 1);

        int progress = team.getProgress(this);

        BingoTeamGoalProgressEvent progressEvent = new BingoTeamGoalProgressEvent(team, this, preProgress, progress);
        Events.call(progressEvent);

        if (progress < getAmount())
            return;

        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }
}
