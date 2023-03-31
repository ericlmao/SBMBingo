package games.negative.bingo.commands.admin;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.core.Locale;
import games.negative.bingo.state.GameState;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "toggle"
)
public class CmdToggle extends SubCommand {

    private final BingoPlugin plugin;

    public CmdToggle(BingoPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GameState state = plugin.getState();
        state.setEnabled(!state.isEnabled());

        boolean enabled = state.isEnabled();
        String fancy = (enabled ? "&aenabled" : "&cdisabled");

        Locale.STATE_UPDATED.replace("%state%", fancy).send(sender);
    }
}
