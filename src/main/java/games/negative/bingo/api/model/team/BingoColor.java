package games.negative.bingo.api.model.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum BingoColor {

    // List of colors
    RED(ChatColor.RED, Material.RED_WOOL),
    BLUE(ChatColor.BLUE, Material.BLUE_WOOL),
    GREEN(ChatColor.GREEN, Material.GREEN_WOOL),
    YELLOW(ChatColor.YELLOW, Material.YELLOW_WOOL),
    WHITE(ChatColor.WHITE, Material.WHITE_WOOL),
    BLACK(ChatColor.BLACK, Material.BLACK_WOOL),
    AQUA(ChatColor.AQUA, Material.LIGHT_BLUE_WOOL),
    DARK_AQUA(ChatColor.DARK_AQUA, Material.CYAN_WOOL),
    DARK_BLUE(ChatColor.DARK_BLUE, Material.BLUE_WOOL),
    DARK_GRAY(ChatColor.DARK_GRAY, Material.GRAY_WOOL),
    DARK_GREEN(ChatColor.DARK_GREEN, Material.GREEN_WOOL),
    DARK_PURPLE(ChatColor.DARK_PURPLE, Material.PURPLE_WOOL),
    DARK_RED(ChatColor.DARK_RED, Material.RED_WOOL),
    GOLD(ChatColor.GOLD, Material.ORANGE_WOOL),
    GRAY(ChatColor.GRAY, Material.LIGHT_GRAY_WOOL),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL),
    ;
    private final ChatColor color;
    private final Material material;

}
