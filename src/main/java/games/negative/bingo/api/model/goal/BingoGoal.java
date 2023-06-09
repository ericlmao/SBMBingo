package games.negative.bingo.api.model.goal;

import games.negative.bingo.api.event.biome.BiomeChangeEvent;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.key.Keyd;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BingoGoal implements Keyd<String> {

    private final String key;
    private final BingoGoalType type;
    private final int amount;
    private final String display;
    private final Material displayMaterial;

    public BingoGoal(ConfigurationSection section) {
        this.key = section.getName();
        this.type = BingoGoalType.valueOf(section.getString("goal-type"));
        this.amount = section.getInt("goal-amount", 1);

        this.display = section.getString("name");
        this.displayMaterial = Material.valueOf(section.getString("material"));
    }

    public BingoGoal(String key, BingoGoalType type, int amount, String displayName, Material displayMaterial) {
        this.key = key;
        this.type = type;
        this.amount = amount;
        this.display = displayName;
        this.displayMaterial = displayMaterial;
    }

    @NotNull
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(@NotNull String s) {
        throw new UnsupportedOperationException("Cannot set key of BingoGoal");
    }

    public BingoGoalType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public Material getDisplayMaterial() {
        return displayMaterial;
    }

    public String getDisplay() {
        return display;
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

    public void onPlayerDeath(BingoTeam team, PlayerDeathEvent event) {
        // Override to add functionality
    }

    public void onBiomeChange(BingoTeam team, BiomeChangeEvent event) {
        // Override to add functionality
    }

}
