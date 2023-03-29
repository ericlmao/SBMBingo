package games.negative.bingo.goals;

import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CollectItemGoal extends BingoGoal {

    private final Material material;
    public CollectItemGoal(String key, int amount, Material material) {
        super(key, BingoGoalType.COLLECT, amount);
        this.material = material;
    }

    public CollectItemGoal(ConfigurationSection section) {
        super(section);
        this.material = Material.valueOf(section.getString("goal-material", "STONE"));
    }

    @Override
    public void onPlayerPickup(BingoTeam team, EntityPickupItemEvent event) {
        super.onPlayerPickup(team, event);
    }

    @Override
    public void onCraft(BingoTeam team, CraftItemEvent event) {
        super.onCraft(team, event);
    }

    @Override
    public void onInventoryClick(BingoTeam team, InventoryCloseEvent event) {
        super.onInventoryClick(team, event);
    }
}
