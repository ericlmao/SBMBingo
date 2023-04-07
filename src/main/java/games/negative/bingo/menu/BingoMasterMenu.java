package games.negative.bingo.menu;

import com.google.common.collect.Lists;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.util.TextUtil;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.gui.GUI;
import games.negative.framework.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BingoMasterMenu extends GUI {
    public BingoMasterMenu(BingoTeamManager manager) {
        super("Bingo Master Menu", 6);

        List<Integer> fillers = List.of(
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 35,
                36, 37, 38, 39, 40, 41, 42, 43, 44,
                45, 46, 47, 49, 51, 52, 53
        );

        ItemStack filler = ItemBuilder.newItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build();
        fillers.forEach(index -> setItem(index, player -> filler));

        for (BingoTeam team : manager.getTeams()) {
            Set<String> members = team.getMembers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.toSet());
            Map<BingoGoal, Integer> progresses = team.getProgresses();

            BingoColor color = team.getBingoColor();

            Material wool = color.getMaterial();
            ChatColor chat = color.getColor();
            String name = color.getRealPeopleWord();

            List<String> lore = Lists.newArrayList();
            lore.add("&8&m-------------------------------");
            lore.add("&6&lPlayers:");
            members.forEach(user -> lore.add("&7 - &e" + user));
            lore.add("&8&m-------------------------------");
            lore.add("&6&lBingo Card:");

            for (Map.Entry<BingoGoal, Integer> entry : progresses.entrySet()) {
                BingoGoal goal = entry.getKey();
                int progress = entry.getValue();
                int max = goal.getAmount();
                boolean completed = progress >= max;

                String percent = Utils.decimalFormat((double) progress / max * 100);

                lore.add("&7 - &e" + TextUtil.stripColor(goal.getDisplay()) + " &7(&e" + progress + "&7/&e" + max + "&7) " + (completed ? "&2&lCOMPLETED" : "&e&l" + percent + "%"));
            }
            lore.add("&8&m-------------------------------");

            ItemStack item = ItemBuilder.newItemBuilder(wool).setName(chat + name).setLore(lore).build();
            addItem(player -> item);
        }
    }
}
