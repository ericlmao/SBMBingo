package games.negative.bingo.menu;

import com.google.common.collect.Lists;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.team.BingoTeamJoinEvent;
import games.negative.bingo.api.event.team.BingoTeamQuitEvent;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.util.ActionBar;
import games.negative.framework.base.itembuilder.ItemBuilder;
import games.negative.framework.event.Events;
import games.negative.framework.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// todo: Redesign / cleanup
public class BingoTeamMenu extends GUI {
    public BingoTeamMenu(BingoTeamManager manager) {
        super("Bingo Teams", 3);

        Collection<BingoTeam> teams = manager.getTeams();
        for (BingoTeam team : teams) {
            BingoColor color = team.getBingoColor();
            Material material = color.getMaterial();

            List<String> names = team.getMembers().stream().map(uuid -> Bukkit.getOfflinePlayer(uuid).getName()).collect(Collectors.toList());
            List<String> lore = Lists.newArrayList();

            names.forEach(value -> lore.add("&8 - &e" + value));

            ItemBuilder builder = ItemBuilder.newItemBuilder(material).setName(color.getColor() + color.getRealPeopleWord()).setLore(lore);
            addItemClickEvent(player -> {
                BingoTeam userTeam = manager.getUserTeam(player.getUniqueId());
                if (userTeam != null && userTeam.getBingoColor() == color)
                    builder.addUnsafeEnchantment(Enchantment.DURABILITY, 10).addItemFlags(ItemFlag.HIDE_ENCHANTS);

                return builder.build();
            }, (player, event) -> {
                UUID uuid = player.getUniqueId();
                BingoTeam userTeam = manager.getUserTeam(uuid);
                if (userTeam != null) {
                    // Leave Team
                    BingoColor bingoColor = userTeam.getBingoColor();
                    userTeam.removeMember(uuid);
                    manager.removeUserTeam(uuid);
                    if (bingoColor != color) {
                        // Not the same team, so leave current team and join new team
                        team.addMember(uuid);
                        manager.addUserTeam(uuid, team);

                        String text = "&6" + player.getName() + "&ehas left "
                                + bingoColor.getColor() + bingoColor.getRealPeopleWord() + " &eand joined "
                                + color.getColor() + color.getRealPeopleWord() + "&e!";

                        ActionBar.broadcast(text);

                        Bukkit.getOnlinePlayers().forEach(online -> online.playSound(online.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1));

                        BingoTeamQuitEvent quit = new BingoTeamQuitEvent(userTeam, player);
                        Events.call(quit);

                        BingoTeamJoinEvent join = new BingoTeamJoinEvent(team, player);
                        Events.call(join);

                        player.closeInventory();
                        return;
                    }

                    String text = "&6" + player.getName() + " &ehas left " + bingoColor.getColor() + "&l" + bingoColor.getRealPeopleWord() + "&e!";
                    ActionBar.broadcast(text);

                    Bukkit.getOnlinePlayers().forEach(online -> online.playSound(online.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1));

                    BingoTeamQuitEvent quit = new BingoTeamQuitEvent(team, player);
                    Events.call(quit);
                } else {
                    // Join Team
                    team.addMember(uuid);
                    manager.addUserTeam(uuid, team);

                    String text = "&6" + player.getName() + " &ehas joined " + color.getColor() + "&l" + color.getRealPeopleWord() + "&e!";
                    ActionBar.broadcast(text);

                    Bukkit.getOnlinePlayers().forEach(online -> online.playSound(online.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1));

                    BingoTeamJoinEvent join = new BingoTeamJoinEvent(team, player);
                    Events.call(join);
                }
                player.closeInventory();
            });
        }
    }
}
