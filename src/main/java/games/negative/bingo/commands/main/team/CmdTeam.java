package games.negative.bingo.commands.main.team;

import games.negative.framework.command.SubCommand;
import org.bukkit.command.CommandSender;

public class CmdTeam extends SubCommand {

    public CmdTeam() {
        addSubCommands(
                new CmdJoin(),
                new CmdLeave()
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
