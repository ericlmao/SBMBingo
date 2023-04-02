package games.negative.bingo.commands.admin;

import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.core.Locale;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "stop"
)
public class CmdStop extends SubCommand {

    private final BingoGameManager manager;

    public CmdStop(BingoGameManager manager) {
        this.manager = manager;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        BingoGame game = manager.getActiveGame();
        if (game == null) {
            // Game is not running
            Locale.BINGO_GAME_NOT_RUNNING.send(sender);
            return;
        }

        manager.stop();
    }
}
