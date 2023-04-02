package games.negative.bingo.menu;

import com.google.common.collect.Lists;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.team.BingoTeamJoinEvent;
import games.negative.bingo.api.event.team.BingoTeamQuitEvent;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.Locale;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.event.Events;
import games.negative.framework.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class BingoTeamMenu extends GUI {
    public BingoTeamMenu(BingoTeamManager manager) {
        super("Bingo Teams", 3);

        Collection<BingoTeam> teams = manager.getTeams();
        for (BingoTeam team : teams) {
            BingoColor color = team.getBingoColor();
            Material material = color.getMaterial();

            Collection<UUID> members = team.getMembers();
            List<String> lore = Lists.newArrayList();

            for (UUID member : members) {
                lore.add("&8 - &e" + Bukkit.getOfflinePlayer(member).getName());
            }

            ItemBuilder builder = ItemBuilder.newItemBuilder(material).setName(color.getColor() + color.getRealPeopleWord()).setLore(lore);
            addItemClickEvent(player -> {
                BingoTeam userTeam = manager.getUserTeam(player.getUniqueId());
                if (userTeam == null || userTeam.getBingoColor() != color) {
                    return builder.build();
                } else {
                    return builder.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
                            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                            .build();
                }
            }, (player, event) -> {
                UUID uuid = player.getUniqueId();
                BingoTeam userTeam = manager.getUserTeam(uuid);
                if (userTeam != null) {
                    // Leave Team
                    BingoColor bingoColor = userTeam.getBingoColor();
                    userTeam.removeMember(uuid);
                    manager.removeUserTeam(uuid);

                    Locale.USER_LEFT_TEAM.replace("%player%", player.getName())
                            .replace("%team%", bingoColor.getColor() + bingoColor.getRealPeopleWord()).broadcast();

                    BingoTeamQuitEvent quit = new BingoTeamQuitEvent(team, player);
                    Events.call(quit);
                } else {
                    // Join Team
                    team.addMember(uuid);
                    manager.addUserTeam(uuid, team);

                    Locale.USER_JOINED_TEAM.replace("%player%", player.getName())
                            .replace("%team%", color.getColor() + color.getRealPeopleWord()).broadcast();

                    BingoTeamJoinEvent join = new BingoTeamJoinEvent(team, player);
                    Events.call(join);
                }
                player.closeInventory();
            });
        }
    }
}
