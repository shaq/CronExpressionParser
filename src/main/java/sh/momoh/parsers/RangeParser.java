package sh.momoh.parsers;

import sh.momoh.expression.CronField;
import sh.momoh.expression.IllegalCronExpressionException;
import sh.momoh.expression.timeunit.DayOfWeek;
import sh.momoh.expression.timeunit.Month;
import sh.momoh.expression.timeunit.TimeBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;
import static sh.momoh.expression.CronField.FIELD_TIME_BOUNDS;
import static sh.momoh.expression.CronFieldType.SINGLE;
import static sh.momoh.expression.timeunit.DayOfWeek.getDayNumber;
import static sh.momoh.expression.timeunit.Month.getMonthNumber;
import static sh.momoh.expression.timeunit.TimeBounds.isWithinBounds;

public class RangeParser implements ICronFieldParser {

    public List<Integer> parse(CronField cronField) {
        String fieldName = cronField.getFieldName();
        String value = cronField.getFieldValue();
        List<String> valuesList = List.of(value.split("-"));

        ICronFieldParser singleParser = ParserFactory.getParser(SINGLE);
        // Validate both parts of the range. If any part is invalid, an IllegalCronExpressionException error will be
        // thrown within SingleParser.
        valuesList.stream().map(v -> new CronField(fieldName, v)).forEach(singleParser::parse);

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
            start = ("day of week".equals(fieldName) && start == 0) ? 7 : start;
            if (start < end) {
                return IntStream
                        .range(start, end + 1)
                        .boxed()
                        .collect(Collectors.toList());
            } else if (start > end) {
                TimeBounds bounds = FIELD_TIME_BOUNDS.get(fieldName);
                int lower = bounds.getLowerBound(), upper = bounds.getUpperBound();
                // sunday can be 7 or 0, so set lower bound to 1 and keep sunday as 7
                lower = "day of week".equals(fieldName) ? lower + 1 : lower;

                List<Integer> reversed = new ArrayList<>();
                for (int i = start; i < upper + 1; i++) reversed.add(i);
                for (int i = lower; i < end + 1; i++) reversed.add(i);
                return reversed;
            }
        }
        throw new IllegalCronExpressionException(String.format("Incorrect range %s-%s for field: <%s>", start, end, fieldName));
    }

}
