package sh.momoh;

import sh.momoh.expression.CronField;
import sh.momoh.expression.CronFieldType;
import sh.momoh.expression.IllegalCronExpressionException;
import sh.momoh.parsers.ICronFieldParser;
import sh.momoh.parsers.ParserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CronParser {

    public static void main(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException("No cron expression supplied.");
        String expression = args[0];
        List<String> progArgs = List.of(expression.split("\\s"));
        if (progArgs.size() < 6) throw new IllegalCronExpressionException("CronParser requires at least 6 arguments.");

        // If year field is present, set length of cron to 6.
        int cronFieldNum;
        try {
            CronFieldType.from(progArgs.get(5));
            cronFieldNum = 6;
        } catch (IllegalCronExpressionException e) {
            cronFieldNum = 5;
        }

        List<String> cronExpression = progArgs.subList(0, cronFieldNum);
        List<String> cronCommand = progArgs.subList(cronFieldNum, progArgs.size());
        printOutputTable(cronExpression, cronCommand);
    }

    private static List<CronOutput<Integer>> parseCronFields(List<CronField> cronFields) {
        List<CronOutput<Integer>> cronOutput = new ArrayList<>();
        for (CronField cronField : cronFields) {
            ICronFieldParser parser = ParserFactory.getParser(cronField.getCronType());
            String fieldName = cronField.getFieldName();
            List<Integer> times = parser.parse(cronField);
            cronOutput.add(new CronOutput<>(fieldName, times));
        }
        return cronOutput;
    }

    private static List<CronField> parseCronExpression(List<String> cronExpression) {
        return IntStream
                .range(0, cronExpression.size())
                .mapToObj(index -> CronFieldType.from(cronExpression.get(index), CronField.NAMES.get(index)))
                .collect(Collectors.toList());
    }

    private static void printOutputTable(List<String> cronExpression, List<String> cronCommand) {
        List<CronField> cronFields = parseCronExpression(cronExpression);
        List<CronOutput<Integer>> table = parseCronFields(cronFields);
        table.forEach(System.out::println);
        System.out.println(new CronOutput<>("command", cronCommand));
    }

}