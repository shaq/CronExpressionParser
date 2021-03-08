package sh.momoh.parsers;

import sh.momoh.expression.CronField;
import sh.momoh.expression.CronFieldType;

import java.util.ArrayList;
import java.util.List;

public class IntervalParser implements ICronFieldParser {

    public List<Integer> parse(CronField cronField) {
        String fieldName = cronField.getFieldName();
        String value = cronField.getFieldValue();
        List<String> valuesList = List.of(value.split("/"));
        String frequency = valuesList.get(0), divisor = valuesList.get(1);

        CronFieldType freqType = CronFieldType.from(frequency);
        ICronFieldParser parser = ParserFactory.getParser(freqType);
        List<Integer> frequencyList = parser.parse(new CronField(fieldName, frequency));
        if (frequencyList == null) throw new AssertionError();

        List<Integer> filtered = new ArrayList<>();
        int start = frequencyList.get(0), end = frequencyList.get(frequencyList.size() - 1);
        for (int i = start; i < end; i += Integer.parseInt(divisor)) {
            filtered.add(i);
        }

        return filtered;
    }

}
