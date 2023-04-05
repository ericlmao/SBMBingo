package games.negative.bingo.core;

import games.negative.framework.message.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Locale {

    NOT_ON_TEAM("not-on-team", List.of(
            "&cYou are not on a team!")
    ),

    USER_LEFT_TEAM("user-left-team", List.of(
            "&8[&6Teams&8] &6%player% &ehas left %team%&e!"
    )),

    USER_JOINED_TEAM("user-joined-team", List.of(
            "&8[&6Teams&8] &6%player% &ehas joined %team%&e!"
    )),

    STATE_UPDATED("state-updated", List.of(
            "&8[&6Bingo&8] &eThe game state has been changed to %state%&e!"
    )),

    CONFIG_RELOADED("config-reloaded", List.of(
            "&8[&6Bingo&8] &eThe configuration files have been reloaded."
    )),

    BINGO_GAME_ALREADY_RUNNING("bingo-game-already-running", List.of(
            "&8[&6Bingo&8] &cA game is already running!"
    )),

    BINGO_GAME_NOT_RUNNING("bingo-game-not-running", List.of(
            "&8[&6Bingo&8] &cThere is no game running!"
    )),

    BINGO_GOAL_COMPLETED("bingo-goal-completed", List.of(
            "&8&m--------------------------------------",
            "&eTeam %team% &ehas completed &6%goal%",
            "&8&m--------------------------------------"
    ))
    ;
    private final String id;
    private final List<String> defaultMessage;
    private Message message;

    @SneakyThrows
    public static void init(JavaPlugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "messages.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            Arrays.stream(values()).forEach(locale -> {
                String id = locale.getId();
                List<String> defaultMessage = locale.getDefaultMessage();

                config.set(id, defaultMessage);
            });

        } else {
            Arrays.stream(values()).filter(locale -> {
                String id = locale.getId();
                return (config.get(id, null) == null);
            }).forEach(locale -> config.set(locale.getId(), locale.getDefaultMessage()));

        }
        config.save(configFile);

        // Creates the message objects
        Arrays.stream(values()).forEach(locale ->
                locale.message = new Message(config.getStringList(locale.getId())
                        .toArray(new String[0])));
    }

    public void send(CommandSender sender) {
        message.send(sender);
    }

    public void send(Iterable<CommandSender> players) {
        message.send(players);
    }

    public void broadcast() {
        message.broadcast();
    }

    public Message replace(String placeholder, String replacement) {
        return message.replace(placeholder, replacement);
    }

}
