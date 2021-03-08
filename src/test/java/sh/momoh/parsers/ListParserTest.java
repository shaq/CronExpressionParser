package sh.momoh.parsers;

import org.junit.Before;
import org.junit.Test;
import sh.momoh.expression.CronField;
import sh.momoh.expression.CronFieldType;
import sh.momoh.expression.IllegalCronExpressionException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ListParserTest {

    private final CronField cronField = new CronField();
    private ICronFieldParser listParser;

    @Before
    public void setUp() {
        CronFieldType fieldType = CronFieldType.LIST;
        cronField.setCronType(fieldType);
        listParser = ParserFactory.getParser(fieldType);
    }

    @Test
    public void testMinutesInRange() {
        String minutes = "minutes";
        cronField.setFieldName(minutes);
        cronField.setFieldValue("0,1,2,3");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(0, 1, 2, 3);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testMinutesOutOfRange() {
        String minutes = "minutes";
        cronField.setFieldName(minutes);
        cronField.setFieldValue("61,1,2,100");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test
    public void testHoursInRange() {
        String hours = "hours";
        cronField.setFieldName(hours);
        cronField.setFieldValue("0,12,23");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(0, 12, 23);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testHoursOutOfRange() {
        String hours = "hours";
        cronField.setFieldName(hours);
        cronField.setFieldValue("24,30");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test
    public void testDayOfMonthInRange() {
        String day = "day of month";
        cronField.setFieldName(day);
        cronField.setFieldValue("1,15,2,31");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(1, 2, 15, 31);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testDayOfMonthOutOfRange() {
        String day = "day of month";
        cronField.setFieldName(day);
        cronField.setFieldValue("32,1,2,0");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test
    public void testMonthInRangeNumbers() {
        String month = "month";
        cronField.setFieldName(month);
        cronField.setFieldValue("1,2,3");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(1, 2, 3);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testMonthInRangeNames() {
        String month = "month";
        cronField.setFieldName(month);
        cronField.setFieldValue("jan,feb,mar");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(1, 2, 3);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testMonthOutOfRangeNumbers() {
        String month = "month";
        cronField.setFieldName(month);
        cronField.setFieldValue("13,0");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testMonthIncorrectNames() {
        String month = "month";
        cronField.setFieldName(month);
        cronField.setFieldValue("jan,blah,february");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test
    public void testDayOfWeekInRangeNumbers() {
        String day = "day of week";
        cronField.setFieldName(day);
        cronField.setFieldValue("0,3,6,7");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(3, 6, 7);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testDayOfWeekInRangeNames() {
        String day = "day of week";
        cronField.setFieldName(day);
        cronField.setFieldValue("mon,tue,sun");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(1, 2, 7);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testDayOfWeekInRangeMixed() {
        String day = "day of week";
        cronField.setFieldName(day);
        cronField.setFieldValue("mon,2,thu,7");
        List<Integer> actual =  listParser.parse(cronField);
        List<Integer> expected = List.of(1, 2, 4, 7);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testDayOfWeekOutOfRangeNumbers() {
        String month = "month";
        cronField.setFieldName(month);
        cronField.setFieldValue("8,0");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test(expected = IllegalCronExpressionException.class)
    public void testDayOfWeekIncorrectNames() {
        String month = "month";
        cronField.setFieldName(month);
        cronField.setFieldValue("man,teu,wednesday");
        List<Integer> actual =  listParser.parse(cronField);
    }

    @Test
    public void testDuplocateList() {
        String minute = "minutes", day = "day of week";

        List<Integer> expectedMin = List.of(48);
        cronField.setFieldName(minute);
        cronField.setFieldValue("48,48,48");
        List<Integer> actualMin =  listParser.parse(cronField);
        assertThat(actualMin, is(equalTo(expectedMin)));

        List<Integer> expectedDay = List.of(5);
        cronField.setFieldName(day);
        cronField.setFieldValue("fri,5,fri");
        List<Integer> actualDay =  listParser.parse(cronField);
        assertThat(actualDay, is(equalTo(expectedDay)));
    }
}
