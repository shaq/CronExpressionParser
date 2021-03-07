package sh.momoh;

import java.util.List;
import java.util.stream.Collectors;

public class CronOutput<T> {

    private String fieldName;
    private List<T> values;

    public CronOutput(String fieldName, List<T> values) {
        this.fieldName = fieldName;
        this.values = values;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        String valuesOutput = values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        return String.format("%-14s %s", fieldName, valuesOutput);
    }
}
