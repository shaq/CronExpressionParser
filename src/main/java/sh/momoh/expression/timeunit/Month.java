package sh.momoh.expression.timeunit;

import sh.momoh.expression.IllegalCronExpressionException;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum Month {
    jan("^1$|^jan$"),
    feb("^2$|^feb$"),
    mar("^3$|^mar$"),
    apr("^4$|^apr$"),
    may("^5$|^may$"),
    jun("^6$|^jun$"),
    jul("^7$|^jul$"),
    aug("^8$|^aug$"),
    sep("^9$|^sep$"),
    oct("^10$|^oct$"),
    nov("^11$|^nov$"),
    dec("^12$|^dec$");

    private final Pattern regex;

    Month(String month) {
        this.regex = Pattern.compile(month, Pattern.CASE_INSENSITIVE);
    }

    public static Month from(String value) {
        return Arrays.stream(values())
                .filter(day -> day.matches(value))
                .findFirst()
                .orElseThrow(() -> new IllegalCronExpressionException("The value \"" + value + "\" does not match any" +
                        " allowed values of month (1-12 or jan-dec)."));
    }

    private boolean matches(String text) {
        return regex.matcher(text).find();
    }

    public static Integer getMonthNumber(Month month) {
        return month.ordinal() + 1;
    }
}
