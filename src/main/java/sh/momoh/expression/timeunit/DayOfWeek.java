package sh.momoh.expression.timeunit;

import sh.momoh.expression.IllegalCronExpressionException;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum DayOfWeek {
    mon("^1$|^mon$"),
    tue("^2$|^tue$"),
    wed("^3$|^wed$"),
    thu("^4$|^thu$"),
    fri("^5$|^fri$"),
    sat("^6$|^sat$"),
    sun("^7$|^0$|^sun$");

    private final Pattern regex;

    DayOfWeek(String day) {
        this.regex = Pattern.compile(day, Pattern.CASE_INSENSITIVE);
    }

    public static DayOfWeek from(String value) {
        return Arrays.stream(values())
                .filter(day -> day.matches(value))
                .findFirst()
                .orElseThrow(() -> new IllegalCronExpressionException("The value \"" + value + "\" does not match any" +
                        " allowed values of week (0-7 or mon-sun)."));
    }

    private boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public static Integer getDayNumber(DayOfWeek day, String value) {
        if (day == sun) return 7;
        return day.ordinal() + 1;
    }
}
