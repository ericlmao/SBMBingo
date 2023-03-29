package games.negative.bingo.api.model;

import games.negative.framework.key.Keyd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.management.PlatformLoggingMXBean;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public interface BingoTeam extends Keyd<BingoColor> {

    Collection<UUID> getMembers();

    default Collection<Player> getOnlinePlayers() {
        return getMembers().stream().filter(uuid -> Bukkit.getPlayer(uuid) != null).map(Bukkit::getPlayer).collect(Collectors.toList());
    }

    BingoColor getBingoColor();

}
