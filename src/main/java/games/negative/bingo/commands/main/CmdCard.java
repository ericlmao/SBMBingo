package games.negative.bingo.commands.main;

import games.negative.bingo.api.BingoGoalManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.Locale;
import games.negative.bingo.menu.BingoGoalMenu;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "card",
        playerOnly = true,
        shortCommands = {"card"}
)
public class CmdCard extends SubCommand {

    private final BingoGoalManager goalManager;
    private final BingoTeamManager teamManager;

    public CmdCard(BingoGoalManager goalManager, BingoTeamManager teamManager) {
        this.goalManager = goalManager;
        this.teamManager = teamManager;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        BingoTeam team = teamManager.getUserTeam(player.getUniqueId());
        if (team == null) {
            Locale.NOT_ON_TEAM.send(player);
            return;
        }

        new BingoGoalMenu(team, goalManager).open(player);
    }
}
