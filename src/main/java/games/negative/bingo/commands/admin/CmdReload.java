package games.negative.bingo.commands.admin;

import games.negative.bingo.BingoPlugin;
import games.negative.bingo.core.Locale;
import games.negative.framework.command.SubCommand;
import games.negative.framework.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "reload"
)
public class CmdReload extends SubCommand {

    private final BingoPlugin plugin;

    public CmdReload(BingoPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        Locale.init(plugin);

        Locale.CONFIG_RELOADED.send(sender);
    }
}
