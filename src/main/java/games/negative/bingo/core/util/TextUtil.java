package games.negative.bingo.core.util;

public class TextUtil {

    public static String capitalize(String input) {
        String[] split = input.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : split) {
            builder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase()).append(" ");
        }
        return builder.toString().trim();
    }

}
