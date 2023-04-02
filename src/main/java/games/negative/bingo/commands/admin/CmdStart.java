package games.negative.bingo.commands.admin;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.core.Locale;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "start"
)
public class CmdStart extends SubCommand {

    private final BingoPlugin plugin;
    private final BingoGameManager manager;

    public CmdStart(BingoPlugin plugin, BingoGameManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        BingoGame game = manager.getActiveGame();
        if (game != null) {
            // Game is already running
            Locale.BINGO_GAME_ALREADY_RUNNING.send(sender);
            return;
        }

        manager.start(plugin.getGameDuration());
    }
}
