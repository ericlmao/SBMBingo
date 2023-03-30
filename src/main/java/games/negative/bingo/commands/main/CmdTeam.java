package games.negative.bingo.commands.main;

import games.negative.bingo.api.BingoTeamManager;
import games.negative.framework.command.SubCommand;
import org.bukkit.command.CommandSender;

public class CmdTeam extends SubCommand {

    private final BingoTeamManager manager;
    public CmdTeam(BingoTeamManager manager) {
        this.manager = manager;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
