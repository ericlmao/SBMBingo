package games.negative.bingo.commands.admin;

import games.negative.bingo.BingoPlugin;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "bingoadmin",
        aliases = {"ba"},
        permission = "bingo.admin"
)
public class CommandBingoAdmin extends Command {

    public CommandBingoAdmin(BingoPlugin plugin) {
        addSubCommands(
                new CmdToggle(plugin)
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
