package sh.momoh.parsers;

import sh.momoh.expression.CronField;
import sh.momoh.expression.IllegalCronExpressionException;
import sh.momoh.expression.timeunit.DayOfWeek;
import sh.momoh.expression.timeunit.Month;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;
import static sh.momoh.expression.timeunit.DayOfWeek.getDayNumber;
import static sh.momoh.expression.timeunit.Month.getMonthNumber;
import static sh.momoh.expression.timeunit.TimeBounds.isWithinBounds;

public class RangeParser {

    public static List<Integer> parse(String fieldName, CronField cronField) throws IllegalCronExpressionException {
        String value = cronField.getFieldValue();
        List<String> valuesList = List.of(value.split("-"));

        // Validate both parts of the range. If any part is invalid, an IllegalCronExpressionException error will be
        // thrown within SingleParser.
        valuesList.forEach(v -> SingleParser.parse(fieldName, new CronField(fieldName, v)));

        List<Integer> timeList = null;
        String start = valuesList.get(0), end = valuesList.get(1);
        if (isParsable(start) && isParsable(end)) {
            timeList = getRange(fieldName, Integer.parseInt(start), Integer.parseInt(end));
        } else {
            switch (fieldName) {
                case "month" -> {
                    Month startMonth = Month.from(start);
                    Month endMonth = Month.from(end);
                    timeList = getRange(fieldName, getMonthNumber(startMonth), getMonthNumber(endMonth));
                }
                case "day of week" -> {
                    DayOfWeek startDay = DayOfWeek.from(start);
                    DayOfWeek endDay = DayOfWeek.from(end);
                    timeList = getRange(fieldName, getDayNumber(startDay, start), getDayNumber(endDay, end));
                }
            }
        }
        return timeList;
    }

    private static List<Integer> getRange(String fieldName, Integer start, Integer end) {
        if (isWithinBounds(fieldName, start) && isWithinBounds(fieldName, end)) {
            if (start < end) {
                return IntStream
                        .range(start, end + 1)
                        .boxed()
                        .collect(Collectors.toList());
            }
        }
        throw new IllegalCronExpressionException(String.format("Incorrect range %s-%s for field: <%s>", start, end, fieldName));
    }

}
