package games.negative.bingo.commands.main;

import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.menu.BingoTeamMenu;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "team",
        description = "Open the team menu",
        playerOnly = true
)
public class CmdTeam extends SubCommand {

    private final BingoTeamManager manager;
    public CmdTeam(BingoTeamManager manager) {
        this.manager = manager;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new BingoTeamMenu(manager).open((Player) sender);
    }
}
