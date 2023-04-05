package games.negative.bingo.menu;

import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BingoGoalMenu extends GUI {
    public BingoGoalMenu(BingoTeam team, BingoGoalManager manager) {
        super("Bingo Card", 6);

        List<Integer> fillers = List.of(0, 1, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 43, 44, 45, 46, 52, 53);

        ItemStack filler = ItemBuilder.newItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build();
        fillers.forEach(index -> setItem(index, player -> filler));

        for (BingoGoal goal : manager.getBingoGoals()) {
            int progress = team.getProgress(goal);
            int amount = goal.getAmount();

            String displayName = goal.getDisplay();
            Material displayMaterial = goal.getDisplayMaterial();

            ItemStack icon = ItemBuilder.newItemBuilder(displayMaterial).setName(displayName)
                    .setLore(
                            "&8&m------------------------",
                            "&7Progress: &e" + Utils.decimalFormat(progress) + "&6/&e" + Utils.decimalFormat(amount),
                            "&8&m------------------------"
                    ).build();

            addItem(player -> icon);
        }

    }
}
