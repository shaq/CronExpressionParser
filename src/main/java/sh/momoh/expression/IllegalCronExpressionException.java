package sh.momoh.expression;

public class IllegalCronExpressionException extends IllegalArgumentException {
    public IllegalCronExpressionException(String s) {
        super(s);
    }
}
