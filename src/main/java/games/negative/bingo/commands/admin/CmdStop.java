package games.negative.bingo.commands.admin;

import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "stop"
)
public class CmdStop extends SubCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
