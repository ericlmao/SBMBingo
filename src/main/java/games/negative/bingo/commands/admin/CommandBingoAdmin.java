package games.negative.bingo.commands.admin;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "bingoadmin",
        aliases = {"ba"},
        permission = "bingo.admin"
)
public class CommandBingoAdmin extends Command {

    public CommandBingoAdmin(BingoPlugin plugin, BingoGameManager gameManager, BingoTeamManager teamManager) {
        addSubCommands(
                new CmdToggle(plugin),
                new CmdReload(plugin),
                new CmdStart(plugin, gameManager),
                new CmdStop(gameManager),
                new CmdTeams(teamManager)
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
