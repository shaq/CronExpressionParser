package sh.momoh.parsers;

import org.junit.Before;
import org.junit.Test;
import sh.momoh.expression.CronField;
import sh.momoh.expression.CronFieldType;
import sh.momoh.expression.timeunit.TimeBounds;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static sh.momoh.expression.CronField.FIELD_TIME_BOUNDS;

public class StarParserTest {

    private final CronField cronField = new CronField();
    private ICronFieldParser starParser;

    @Before
    public void setUp() {
        CronFieldType fieldType = CronFieldType.STAR;
        cronField.setCronType(fieldType);
        starParser = ParserFactory.getParser(fieldType);
    }

    @Test
    public void testMinutes() {
        String minutes = "minutes";
        cronField.setFieldName(minutes);
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(minutes);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = starParser.parse(cronField);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testHours() {
        String hours = "hours";
        cronField.setFieldName(hours);
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(hours);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = starParser.parse(cronField);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testDayOfWeek() {
        String day = "day of week";
        cronField.setFieldName(day);
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(day);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = starParser.parse(cronField);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testMonth() {
        String month = "month";
        cronField.setFieldName(month);
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(month);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = starParser.parse(cronField);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testDayOfMonth() {
        String day = "day of month";
        cronField.setFieldName(day);
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(day);
        List<Integer> expected = IntStream
                .range(bounds.getLowerBound(), bounds.getUpperBound() + 1)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> actual = starParser.parse(cronField);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFieldName() {
        String dayOfYear = "day of year";
        cronField.setFieldName(dayOfYear);
        List<Integer> actual = starParser.parse(cronField);
    }
}
