package sh.momoh.parsers;

import sh.momoh.expression.CronField;

import java.util.List;

public interface ICronFieldParser {
    List<Integer> parse(CronField cronField);
}
