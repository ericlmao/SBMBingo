package games.negative.bingo.commands.main;

import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "bingo",
        description = "Main command for Bingo",
        aliases = {"b"}
)
public class CommandBingo extends Command {

    public CommandBingo(BingoTeamManager teamManager, BingoGoalManager goalManager) {
        addSubCommands(
                new CmdCard(goalManager, teamManager),
                new CmdTeam(teamManager)
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
