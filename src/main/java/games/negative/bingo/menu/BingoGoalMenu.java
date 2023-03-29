package games.negative.bingo.menu;

import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.gui.DropperGUI;
import games.negative.framework.util.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BingoGoalMenu extends DropperGUI {
    public BingoGoalMenu(BingoTeam team, BingoGoalManager manager) {
        super("Bingo Card");

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
