package games.negative.bingo.api.model.goal;

import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.key.Keyd;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BingoGoal implements Keyd<String> {

    private final String key;
    private final BingoGoalType type;
    private final int amount;

    public BingoGoal(ConfigurationSection section) {
        this.key = section.getName();
        this.type = BingoGoalType.valueOf(section.getString("goal-type"));
        this.amount = section.getInt("goal-amount");
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

    public void onPlayerPickup(BingoTeam team, EntityPickupItemEvent event) {
        // Override to add functionality
    }

    public void onCraft(BingoTeam team, CraftItemEvent event) {
        // Override to add functionality
    }

    public void onInventoryClick(BingoTeam team, InventoryClickEvent event) {
        // Override to add functionality
    }

    public void onDeath(BingoTeam team, EntityDeathEvent event) {
        // Override to add functionality
    }

    public void onPotionEffect(BingoTeam team, EntityPotionEffectEvent event) {
        // Override to add functionality
    }

    public void onAdvancement(BingoTeam team, PlayerAdvancementDoneEvent event) {
        // Override to add functionality
    }


}
