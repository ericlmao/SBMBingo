package games.negative.bingo.menu;

import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BingoGoalMenu extends GUI {
    public BingoGoalMenu(BingoTeam team, BingoGoalManager manager) {
        super("Bingo Card", 5);

        List<Integer> fillers = List.of(0, 1, 7, 8, 9, 10, 16, 17, 18, 19, 25, 26, 27, 28, 34, 35, 36, 37, 43, 44);

        ItemStack filler = ItemBuilder.newItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build();
        fillers.forEach(index -> setItem(index, player -> filler));

        for (BingoGoal goal : manager.getBingoGoals()) {
            int progress = team.getProgress(goal);
            int amount = goal.getAmount();

            String percent = Utils.decimalFormat((double) progress / amount * 100);

            boolean completed = progress >= amount;

            String displayName = goal.getDisplay();
            Material displayMaterial = goal.getDisplayMaterial();

            StringBuilder titleBuilder = new StringBuilder(displayName);
            if (completed)
                titleBuilder.append(" &7- &2&lCOMPLETED");

            ItemBuilder builder = ItemBuilder.newItemBuilder(displayMaterial)
                    .setName(titleBuilder.toString())
                    .setLore(
                            "&8&m------------------------",
                            "&7Progress (&6" + percent + "%&7): &e" + Utils.decimalFormat(progress) + "&6/&e" + Utils.decimalFormat(amount),
                            "&8&m------------------------"
                    );

            // Add glowing effect if completed
            if (completed) {
                builder.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                builder.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            ItemStack icon = builder.build();
            addItem(player -> icon);
        }

    }
}
