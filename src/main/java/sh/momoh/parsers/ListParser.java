package sh.momoh.parsers;

import sh.momoh.expression.CronField;

import java.util.List;
import java.util.stream.Collectors;

import static sh.momoh.expression.CronFieldType.SINGLE;

public class ListParser implements ICronFieldParser {

    public List<Integer> parse(CronField cronField) {
        String fieldName = cronField.getFieldName();
        String value = cronField.getFieldValue();
        List<String> valuesList = List.of(value.split(","));
        ICronFieldParser singleParser = ParserFactory.getParser(SINGLE);
        return valuesList.stream()
                .map(v -> singleParser.parse(new CronField(fieldName, v)))
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

}
