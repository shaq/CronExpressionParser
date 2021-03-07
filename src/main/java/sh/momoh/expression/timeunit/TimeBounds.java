package sh.momoh.expression.timeunit;

import static sh.momoh.expression.CronField.FIELD_TIME_BOUNDS;

public class TimeBounds {

    private final Integer lowerBound;
    private final Integer upperBound;

    public TimeBounds(Integer lowerBound, Integer upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public static boolean isWithinBounds(String fieldName, Integer value) {
        TimeBounds bounds = FIELD_TIME_BOUNDS.get(fieldName);
        return value >= bounds.getLowerBound()
                && value <= bounds.getUpperBound();
    }
}
