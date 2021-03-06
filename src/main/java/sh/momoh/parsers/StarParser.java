package sh.momoh.parsers;

import sh.momoh.expression.CronField;
import sh.momoh.expression.timeunit.TimeBounds;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static sh.momoh.expression.CronField.FIELD_TIME_BOUNDS;

public class StarParser implements ICronFieldParser {

    public List<Integer> parse(CronField cronField) {
        String fieldName = cronField.getFieldName();
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(fieldName);
        if (bounds != null) {
            return IntStream
                    .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                    .boxed()
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException(String.format("'%s' is not a valid cron field.", fieldName));
    }

}
