package games.negative.bingo.api;

import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface BingoTeamManager {

    Collection<BingoTeam> getTeams();

    void addTeam(BingoTeam team);

    void removeTeam(BingoTeam team);

    default BingoTeam getTeam(BingoColor color) {
        return getTeams().stream().filter(team -> team.getKey().equals(color)).findFirst().orElse(null);
    }

    Map<UUID, BingoTeam> getUserTeams();

    void addUserTeam(UUID uuid, BingoTeam team);

    void removeUserTeam(UUID uuid);

    default BingoTeam getUserTeam(UUID uuid) {
        return getUserTeams().get(uuid);
    }

    int getTeamSize();

    void onDisable();

    void onReload(FileConfiguration config);

}
