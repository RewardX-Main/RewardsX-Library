package net.rewardsxdev.rewardsx.api.utils.color;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Color {
    public static String safeTranslate(String msg) {
        if (msg == null) return null;

        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(msg);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group(1);
            // Espandi &#RGB a &#RRGGBB
            if (hex.length() == 3) {
                hex = "" + hex.charAt(0) + hex.charAt(0)
                        + hex.charAt(1) + hex.charAt(1)
                        + hex.charAt(2) + hex.charAt(2);
            }

            // Costruisci il formato legacy con §x§R§R§G§G§B§B
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                replacement.append('§').append(Character.toLowerCase(c));
            }

            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement.toString()));
        }
        matcher.appendTail(sb);

        // Converte anche &a, &l ecc.
        return sb.toString().replace('&', '§');
    }

    public static List<String> safeTranslate(List<String> lines) {
        if (lines == null) return null;

        return lines.stream()
                .map(Color::safeTranslate)
                .collect(Collectors.toList());
    }
}
