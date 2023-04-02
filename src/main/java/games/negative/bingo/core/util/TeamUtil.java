package games.negative.bingo.core.util;

import com.google.common.collect.Lists;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class TeamUtil {

    public static Collection<Player> getAllPlayers(Collection<BingoTeam> teams) {
        List<Player> players = Lists.newArrayList();
        for (BingoTeam team : teams) {
            players.addAll(team.getOnlinePlayers());
        }
        return players;
    }
}
