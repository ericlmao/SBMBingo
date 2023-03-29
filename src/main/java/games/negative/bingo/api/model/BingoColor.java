package games.negative.bingo.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

@Getter
@RequiredArgsConstructor
public enum BingoColor {

    // List of colors
    RED(ChatColor.RED),
    BLUE(ChatColor.BLUE),
    GREEN(ChatColor.GREEN),
    YELLOW(ChatColor.YELLOW),
    WHITE(ChatColor.WHITE),
    BLACK(ChatColor.BLACK),
    AQUA(ChatColor.AQUA),
    DARK_AQUA(ChatColor.DARK_AQUA),
    DARK_BLUE(ChatColor.DARK_BLUE),
    DARK_GRAY(ChatColor.DARK_GRAY),
    DARK_GREEN(ChatColor.DARK_GREEN),
    DARK_PURPLE(ChatColor.DARK_PURPLE),
    DARK_RED(ChatColor.DARK_RED),
    GOLD(ChatColor.GOLD),
    GRAY(ChatColor.GRAY),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE)
    ;
    private final ChatColor color;

}
