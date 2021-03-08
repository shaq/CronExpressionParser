package sh.momoh.expression;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CronFieldType {

    STAR("^\\*$"),
    SINGLE("^\\w{1,4}$"),
    RANGE("^\\w{1,4}-\\w{1,4}$"),
    INTERVAL("^(\\w{1,4}-\\w{1,4}|\\*)/(\\d+)$"),
    LIST("^\\w{1,4}(,\\w{1,4})+$");

    private final Pattern regex;

    CronFieldType(String regex) {
        this.regex = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public static CronField from(String value, String fieldName) {
        CronFieldType cronFieldType = Arrays.stream(values())
                .filter(type -> type.matches(value))
                .findFirst()
                .orElseThrow(() -> new IllegalCronExpressionException("The argument \"" + value + "\" at position <"
                        + fieldName + "> doesn't match any valid cron format."));
        return new CronField(fieldName, cronFieldType, value);
    }

    public static CronFieldType from(String value) {
        return Arrays.stream(values())
                .filter(type -> type.matches(value))
                .findFirst()
                .orElseThrow(() -> new IllegalCronExpressionException("The argument \"" + value + "\" doesn't match any" +
                        " valid cron format."));
    }

    private boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
