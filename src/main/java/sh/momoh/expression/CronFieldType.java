package sh.momoh.expression;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CronFieldType {

    STAR("^\\*$"),
    SINGLE("^\\w{1,4}$"),
    RANGE("^\\w{1,3}-\\w{1,3}$"),
    INTERVAL("^(\\w{1,3}-\\w{1,3}|\\*)/(2|3|4|5|6|12|15|20|30)$"),
    LIST("^\\w{1,3}(,\\w{1,3})+$");

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
                .get();
    }

    private boolean matches(String text) {
        return regex.matcher(text).find();
    }
}
