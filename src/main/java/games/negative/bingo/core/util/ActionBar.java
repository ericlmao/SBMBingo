package games.negative.bingo.core.util;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ActionBar {

    public void send(@NotNull Player player, @NotNull String message) {
        TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }

    public void broadcast(@NotNull String message) {
        Bukkit.getOnlinePlayers().forEach(player -> send(player, message));
    }

}
