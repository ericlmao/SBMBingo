package games.negative.bingo.goals;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.event.team.BingoTeamCompleteGoalEvent;
import games.negative.bingo.api.event.team.BingoTeamGoalProgressEvent;
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

    public CollectItemGoal(String key, int amount, Material material, String displayName, Material displayMaterial) {
        super(key, BingoGoalType.COLLECT, amount, displayName, displayMaterial);
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
        attemptComplete(item, team);
    }

    @Override
    public void onCraft(BingoTeam team, CraftItemEvent event) {
    }

    @Override
    public void onInventoryClick(BingoTeam team, InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null)
            return;

        attemptComplete(item, team);
    }

    public void attemptComplete(ItemStack item, BingoTeam team) {
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
        int preProgress = team.getProgress(this);
        if (preProgress >= getAmount())
            return;

        if ((preProgress + amount) > getAmount())
            amount = getAmount();

        team.addProgress(this, amount);

        container.set(counted, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);

        int postProgress = team.getProgress(this);

        BingoTeamGoalProgressEvent progressEvent = new BingoTeamGoalProgressEvent(team, this, preProgress, postProgress);
        Events.call(progressEvent);

        if (postProgress < getAmount())
            return;

        // Team has completed the goal
        BingoTeamCompleteGoalEvent complete = new BingoTeamCompleteGoalEvent(team, this);
        Events.call(complete);
    }
}
