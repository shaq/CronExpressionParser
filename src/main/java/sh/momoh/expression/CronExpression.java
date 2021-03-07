package sh.momoh.expression;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CronExpression {

    CRON(Pattern.compile("^((\\*|\\?|\\w+((\\/|\\-)?(\\w+))*)\\s*){5}"));

    private final Pattern regex;

    CronExpression(Pattern regex) {
        this.regex = regex;
    }

    public static CronExpression from(String value) {
        return Arrays.stream(values())
                .filter(element -> element.matches(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The argument \"" + value + "\" doesn't match any " +
                        "valid cron format."));
    }

    private boolean matches(String text) {
        return regex.matcher(text).find();
    }

}
