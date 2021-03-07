package sh.momoh.parsers;

import org.junit.Test;
import sh.momoh.expression.timeunit.TimeBounds;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static sh.momoh.expression.CronField.FIELD_TIME_BOUNDS;
import static sh.momoh.parsers.StarParser.parse;

public class StarParserTest {

    @Test
    public void testMinutes() {
        String minutes = "minutes";
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(minutes);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = parse(minutes);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testHours() {
        String hours = "hours";
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(hours);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = parse(hours);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testDayOfWeek() {
        String day = "day of week";
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(day);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = parse(day);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testMonth() {
        String month = "month";
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(month);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = parse(month);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testDayOfMonth() {
        String day = "day of month";
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(day);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = parse(day);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFieldName() {
        String dayOfYear = "day of year";
        List<Integer> actual = parse(dayOfYear);
    }
}
