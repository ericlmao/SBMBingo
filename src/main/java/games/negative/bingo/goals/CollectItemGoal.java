package games.negative.bingo.goals;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.event.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.goal.BingoGoalType;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.Events;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CollectItemGoal extends BingoGoal {

    private final Material material;
    private final NamespacedKey counted;
    public CollectItemGoal(String key, int amount, Material material) {
        super(key, BingoGoalType.COLLECT, amount);
        this.material = material;
        this.counted = new NamespacedKey(BingoPlugin.getInst(), "counted");
    }

    public CollectItemGoal(ConfigurationSection section) {
        super(section);
        this.material = Material.valueOf(section.getString("goal-material", "STONE"));
        this.counted = new NamespacedKey(BingoPlugin.getInst(), "counted");
    }

    @Override
    public void onPlayerPickup(BingoTeam team, EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Material type = item.getType();
        if (type != material)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(counted, PersistentDataType.BYTE))
            return;

        int amount = item.getAmount();
        team.addProgress(this, amount);

        container.set(counted, PersistentDataType.BYTE, (byte) 1);

        int progress = team.getProgress(this);
        if (progress < getAmount())
            return;

        // Team has completed the goal, do something
        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }

    @Override
    public void onCraft(BingoTeam team, CraftItemEvent event) {
    }

    @Override
    public void onInventoryClick(BingoTeam team, InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null)
            return;

        Material type = item.getType();
        if (type != material)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(counted, PersistentDataType.BYTE))
            return;

        int amount = item.getAmount();
        team.addProgress(this, amount);

        container.set(counted, PersistentDataType.BYTE, (byte) 1);

        int progress = team.getProgress(this);
        if (progress < getAmount())
            return;

        // Team has completed the goal, do something
        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }
}
