package sh.momoh.parsers;

import sh.momoh.expression.CronField;
import sh.momoh.expression.IllegalCronExpressionException;
import sh.momoh.expression.timeunit.DayOfWeek;
import sh.momoh.expression.timeunit.Month;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;
import static sh.momoh.expression.timeunit.DayOfWeek.getDayNumber;
import static sh.momoh.expression.timeunit.Month.getMonthNumber;
import static sh.momoh.expression.timeunit.TimeBounds.isWithinBounds;

public class SingleParser {

    public static List<Integer> parse(String fieldName, CronField cronField) {
        String value = cronField.getFieldValue();
        switch (fieldName) {
            case "month" -> {
                Month month = Month.from(value);
                return List.of(getMonthNumber(month));
            }
            case "day of week" -> {
                DayOfWeek day = DayOfWeek.from(value);
                return List.of(getDayNumber(day, value));
            }
            default -> {
                if (isParsable(value)) {
                    int valueInt = Integer.parseInt(value);
                    if (isWithinBounds(fieldName, valueInt)) {
                        return List.of(valueInt);
                    }
                }
            }
        }
        throw new IllegalCronExpressionException(value + " is not a valid value for " + "<" + fieldName + "> field.");
    }

}
