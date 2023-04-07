package games.negative.bingo.api.event.team;

import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BingoTeamJoinEvent extends BingoTeamEvent {

    private final Player player;

    public BingoTeamJoinEvent(@NotNull BingoTeam team, @NotNull Player player) {
        super(team);
        this.player = player;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }
}
