package games.negative.bingo.api.model.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum BingoColor {

    // List of colors
    RED(ChatColor.RED, Material.RED_WOOL, "Red"),
    BLUE(ChatColor.BLUE, Material.BLUE_WOOL, "Blue"),
    GREEN(ChatColor.GREEN, Material.GREEN_WOOL, "Lime"),
    YELLOW(ChatColor.YELLOW, Material.YELLOW_WOOL, "Yellow"),
    WHITE(ChatColor.WHITE, Material.WHITE_WOOL, "White"),
    BLACK(ChatColor.BLACK, Material.BLACK_WOOL, "Black"),
    AQUA(ChatColor.AQUA, Material.LIGHT_BLUE_WOOL, "Aqua"),
    DARK_AQUA(ChatColor.DARK_AQUA, Material.CYAN_WOOL, "Cyan"),
    DARK_BLUE(ChatColor.DARK_BLUE, Material.BLUE_WOOL, "Dark Blue"),
    DARK_GRAY(ChatColor.DARK_GRAY, Material.GRAY_WOOL, "Dark Gray"),
    DARK_GREEN(ChatColor.DARK_GREEN, Material.GREEN_WOOL, "Dark Green"),
    DARK_PURPLE(ChatColor.DARK_PURPLE, Material.PURPLE_WOOL, "Purple"),
    DARK_RED(ChatColor.DARK_RED, Material.RED_WOOL, "Dark Red"),
    GOLD(ChatColor.GOLD, Material.ORANGE_WOOL, "Gold"),
    GRAY(ChatColor.GRAY, Material.LIGHT_GRAY_WOOL, "Gray"),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL, "Pink"),
    ;
    private final ChatColor color;
    private final Material material;
    private final String realPeopleWord;
}
