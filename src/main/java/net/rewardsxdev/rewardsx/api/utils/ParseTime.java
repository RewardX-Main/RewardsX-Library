package net.rewardsxdev.rewardsx.api.utils;

public class ParseTime {
    // Converte "1m", "2h" ecc. in millisecondi
    public static long parse(String input) {
        try {
            input = input.toLowerCase().trim();
            long multiplier = 1000;
            if (input.endsWith("s")) multiplier = 1000;
            else if (input.endsWith("m")) multiplier = 60_000;
            else if (input.endsWith("h")) multiplier = 3_600_000;
            else if (input.endsWith("d")) multiplier = 86_400_000;

            long value = Long.parseLong(input.replaceAll("[^0-9]", ""));
            return value * multiplier;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatDuration(long millis) {
        long seconds = Math.abs(millis) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 24;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m ");
        if (seconds > 0 || sb.length() == 0) sb.append(seconds).append("s ");

        return sb.toString().trim();
    }

}
