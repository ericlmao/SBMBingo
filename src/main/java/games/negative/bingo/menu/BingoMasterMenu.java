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
        super("Bingo Master Menu", 5);

        List<Integer> fillers = List.of(
                1, 3, 5, 7,
                9, 10, 11, 12, 13, 14, 15, 16, 17,
                19, 21, 23, 25,
                27, 28, 29, 30, 31, 32, 33, 34, 35,
                37, 39, 41, 43
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
