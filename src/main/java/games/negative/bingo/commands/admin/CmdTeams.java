package games.negative.bingo.commands.admin;

import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.menu.BingoMasterMenu;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "teams",
        aliases = {"gui"},
        description = "View the teams in the game",
        permission = "bingo.admin",
        playerOnly = true
)
public class CmdTeams extends SubCommand {

    private final BingoTeamManager manager;

    public CmdTeams(BingoTeamManager manager) {
        this.manager = manager;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;

        new BingoMasterMenu(manager).open(player);
    }
}
