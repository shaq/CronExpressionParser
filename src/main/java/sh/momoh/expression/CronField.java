package sh.momoh.expression;

import sh.momoh.expression.timeunit.TimeBounds;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class CronField {

    private String fieldName;
    private CronFieldType cronFieldType;
    private String fieldValue;

    public static final List<String> NAMES = List.of("minutes", "hours", "day of month", "month", "day of week", "year");

    public static final Map<String, TimeBounds> FIELD_TIME_BOUNDS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("minutes", new TimeBounds(0, 59)),
            new AbstractMap.SimpleEntry<>("hours", new TimeBounds(0, 23)),
            new AbstractMap.SimpleEntry<>("day of month", new TimeBounds(1, 31)),
            new AbstractMap.SimpleEntry<>("month", new TimeBounds(1, 12)),
            new AbstractMap.SimpleEntry<>("day of week", new TimeBounds(0, 7)),
            new AbstractMap.SimpleEntry<>("year", new TimeBounds(1000, 2022)));

    public CronField(String fieldName, CronFieldType cronFieldType, String fieldValue) {
        this.cronFieldType = cronFieldType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public CronField(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public CronField() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public CronFieldType getCronType() {
        return cronFieldType;
    }

    public void setCronType(CronFieldType cronFieldType) {
        this.cronFieldType = cronFieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

//    public List<Integer> parse() {
//        ICronFieldParser parser = null;
////        return switch (getCronType()) {
////            case STAR -> StarParser.parse(this);
////            case SINGLE -> SingleParser.parse(this);
////            case RANGE -> RangeParser.parse(this);
////            case INTERVAL -> IntervalParser.parse(this);
////            case LIST -> ListParser.parse(this);
////        };
//        switch (cronFieldType) {
//            case STAR:
//                new StarParser();
//            case SINGLE: parser = new SingleParser();
//            case RANGE: parser = new RangeParser();
//            case INTERVAL: parser = new IntervalParser();
//            case LIST: parser = new ListParser();
//        }
//        return parser.parse(this);
//    }

    @Override
    public String toString() {
        return "CronField{" +
                "fieldName='" + fieldName + '\'' +
                ", cronFieldType=" + cronFieldType +
                ", cronField='" + fieldValue + '\'' +
                '}';
    }
}
