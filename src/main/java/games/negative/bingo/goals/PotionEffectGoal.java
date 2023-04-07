package games.negative.bingo.goals;

import games.negative.bingo.api.event.team.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.team.BingoTeamGoalProgressEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.Events;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectGoal extends BingoGoal {

    private final PotionEffectType type;

    public PotionEffectGoal(ConfigurationSection section) {
        super(section);
        this.type = PotionEffectType.getByKey(NamespacedKey.minecraft(section.getString("goal-potion-effect", "speed")));
    }

    public PotionEffectGoal(String key, BingoGoalType type, String displayName, Material displayMaterial, String potionEffect) {
        super(key, type, 1, displayName, displayMaterial);
        this.type = PotionEffectType.getByKey(NamespacedKey.minecraft(potionEffect));
    }

    @Override
    public void onPotionEffect(BingoTeam team, EntityPotionEffectEvent event) {
        PotionEffect newEffect = event.getNewEffect();
        if (newEffect == null)
            return;

        PotionEffectType type = newEffect.getType();
        if (this.type != type)
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
