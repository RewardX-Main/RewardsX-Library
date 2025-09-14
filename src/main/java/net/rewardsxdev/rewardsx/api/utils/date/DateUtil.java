package net.rewardsxdev.rewardsx.api.utils.date;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateUtil {
    private static final Pattern timePattern = Pattern.compile(
            "(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?" +       // year(s)
                    "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?" +       // month(s)
                    "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?" +        // week(s)
                    "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?" +        // day(s)
                    "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?" +        // hour(s)
                    "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?" +        // minute(s)
                    "(?:([0-9]+)\\s*(?:s[a-z]*)?)?",            // second(s)
            Pattern.CASE_INSENSITIVE
    );
    private static final int maxYears = 100_000;

    private DateUtil() { }

    /** Rimuove il pattern orario dalla stringa */
    public static String removeTimePattern(final String input) {
        return timePattern.matcher(input).replaceFirst("").trim();
    }

    /**
     * Parsea la stringa espressa come tempo e ritorna il corrispondente timestamp in millisecondi
     * rispetto ad ora o epoch zero (se emptyEpoch è true), verso futuro o passato.
     */
    public static long parseDateDiff(String time, boolean future) throws IllegalArgumentException {
        return parseDateDiff(time, future, false);
    }

    public static long parseDateDiff(String time, boolean future, boolean emptyEpoch) throws IllegalArgumentException {
        final Matcher m = timePattern.matcher(time);
        int years = 0, months = 0, weeks = 0, days = 0, hours = 0, minutes = 0, seconds = 0;
        boolean found = false;

        while (m.find()) {
            if (m.group() == null || m.group().isEmpty()) continue;
            for (int i = 1; i <= m.groupCount(); i++) {
                if (m.group(i) != null && !m.group(i).isEmpty()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                if (m.group(1) != null) years = Integer.parseInt(m.group(1));
                if (m.group(2) != null) months = Integer.parseInt(m.group(2));
                if (m.group(3) != null) weeks = Integer.parseInt(m.group(3));
                if (m.group(4) != null) days = Integer.parseInt(m.group(4));
                if (m.group(5) != null) hours = Integer.parseInt(m.group(5));
                if (m.group(6) != null) minutes = Integer.parseInt(m.group(6));
                if (m.group(7) != null) seconds = Integer.parseInt(m.group(7));
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Illegal date/time string: " + time);
        }

        final Calendar calendar = new GregorianCalendar();

        if (emptyEpoch) {
            calendar.setTimeInMillis(0);
        }

        if (years > maxYears) years = maxYears;
        calendar.add(Calendar.YEAR, years * (future ? 1 : -1));
        calendar.add(Calendar.MONTH, months * (future ? 1 : -1));
        calendar.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
        calendar.add(Calendar.DAY_OF_MONTH, days * (future ? 1 : -1));
        calendar.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
        calendar.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
        calendar.add(Calendar.SECOND, seconds * (future ? 1 : -1));

        // Non superare 10 anni in futuro (regola di sicurezza)
        final Calendar max = new GregorianCalendar();
        max.add(Calendar.YEAR, 10);
        if (calendar.after(max)) {
            return max.getTimeInMillis();
        }
        return calendar.getTimeInMillis();
    }

    /** Calcola la differenza tra due date moltiplicando per il campo Calendar (es. YEAR, MONTH...) */
    static int dateDiff(final int type, final Calendar fromDate, final Calendar toDate, final boolean future) {
        final int year = Calendar.YEAR;
        final int fromYear = fromDate.get(year);
        final int toYear = toDate.get(year);

        if (Math.abs(fromYear - toYear) > maxYears) {
            toDate.set(year, fromYear + (future ? maxYears : -maxYears));
        }

        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();

        while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            diff++;
        }
        diff--;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    /**
     * Ritorna una stringa leggibile della differenza tra ora e date (long millis, o Calendar)
     * Formato: "X years Y months Z days ..."
     */
    public static String formatDateDiff(final long date) {
        final Calendar target = new GregorianCalendar();
        target.setTimeInMillis(date);
        final Calendar now = new GregorianCalendar();
        return formatDateDiff(now, target);
    }

    public static String formatDateDiff(final Calendar fromDate, final Calendar toDate) {
        if (toDate.equals(fromDate)) {
            return "now";
        }
        boolean future = toDate.after(fromDate);
        // Buffer per evitare troncamenti dovuti al ritardo di esecuzione
        toDate.add(Calendar.MILLISECOND, future ? 50 : -50);

        final StringBuilder sb = new StringBuilder();
        final int[] fields = new int[] {
                Calendar.YEAR,
                Calendar.MONTH,
                Calendar.DAY_OF_MONTH,
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                Calendar.SECOND
        };
        final String[] singular = { "year", "month", "day", "hour", "minute", "second" };
        final String[] plural = { "years", "months", "days", "hours", "minutes", "seconds" };

        int accuracy = 0;
        for (int i = 0; i < fields.length && accuracy < 3; i++) {
            int diff = dateDiff(fields[i], fromDate, toDate, future);
            if (diff > 0) {
                accuracy++;
                sb.append(' ').append(diff).append(' ').append(diff > 1 ? plural[i] : singular[i]);
            }
        }
        // rimuovo il buffer temporaneo
        toDate.add(Calendar.MILLISECOND, future ? -50 : 50);

        String result = sb.toString().trim();
        return result.isEmpty() ? "now" : result;
    }
}
