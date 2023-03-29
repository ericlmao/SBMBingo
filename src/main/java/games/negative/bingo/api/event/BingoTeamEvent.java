package games.negative.bingo.api.event;

import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.framework.event.PluginEvent;
import org.jetbrains.annotations.NotNull;

public abstract class BingoTeamEvent extends PluginEvent {

    private final BingoTeam team;

    public BingoTeamEvent(@NotNull BingoTeam team) {
        this.team = team;
    }

    @NotNull
    public BingoTeam getTeam() {
        return team;
    }
}
