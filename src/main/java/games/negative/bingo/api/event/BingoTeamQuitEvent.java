package games.negative.bingo.api.event;

import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BingoTeamQuitEvent extends BingoTeamEvent {

    private final Player player;
    public BingoTeamQuitEvent(@NotNull BingoTeam team, @NotNull Player player) {
        super(team);
        this.player = player;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }
}
