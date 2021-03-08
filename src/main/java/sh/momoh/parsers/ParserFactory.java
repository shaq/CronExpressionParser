package sh.momoh.parsers;

import sh.momoh.expression.CronFieldType;

public class ParserFactory {

    public static ICronFieldParser getParser(CronFieldType cronType) {
        return switch (cronType) {
            case STAR -> new StarParser();
            case SINGLE -> new SingleParser();
            case RANGE -> new RangeParser();
            case INTERVAL -> new IntervalParser();
            case LIST -> new ListParser();
        };
    }

}
