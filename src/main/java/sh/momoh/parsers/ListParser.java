package sh.momoh.parsers;

import sh.momoh.expression.*;

import java.util.ArrayList;
import java.util.List;

public class ListParser {

    public static List<Integer> parse(String fieldName, CronField cronField) {
        String value = cronField.getFieldValue();
        List<String> valuesList = List.of(value.split(","));
        List<Integer> timesList = new ArrayList<>();
        valuesList.stream()
                .map(v -> SingleParser.parse(fieldName, new CronField(fieldName, v)))
                .distinct()
                .forEachOrdered(timesList::addAll);
        return timesList;
    }

}
