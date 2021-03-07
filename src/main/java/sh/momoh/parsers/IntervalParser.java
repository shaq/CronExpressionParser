package sh.momoh.parsers;

import sh.momoh.expression.CronField;
import sh.momoh.expression.CronFieldType;

import java.util.ArrayList;
import java.util.List;

public class IntervalParser {

    public static List<Integer> parse(String fieldName, CronField cronField) {
        String value = cronField.getFieldValue();
        List<String> valuesList = List.of(value.split("/"));
        String frequency = valuesList.get(0), divisor = valuesList.get(1);

        CronFieldType type = CronFieldType.from(frequency);
        List<Integer> frequencyList = null;
        switch (type) {
            case STAR -> frequencyList = StarParser.parse(fieldName);
            case RANGE -> frequencyList = RangeParser.parse(fieldName, new CronField(fieldName, frequency));
        }

        List<Integer> filtered = new ArrayList<>();
        if (frequencyList == null) throw new AssertionError();
        int start = frequencyList.get(0), end = frequencyList.get(frequencyList.size() - 1);
        for (int i = start; i < end; i += Integer.parseInt(divisor)) {
            filtered.add(i);
        }

        return filtered;
    }

}
