package no.sikt.calculator;

import java.util.Stack;

import static java.util.Objects.nonNull;

public class ExpressionEvaluator {
    private final transient String expression;
    private final transient Stack<Integer> numberStack = new Stack<>();

    /**
     * Checks if expression is valid for computation.
     * @param expression The expression that should be checked.
     */
    public ExpressionEvaluator(String expression) {
        assert nonNull(expression);
        String[] expArray = expression.split(" ");
        assert isNumber(expArray[0]);
        assert oneMoreOperandsThanOperators(expArray);
        this.expression = expression;
    }

    /**
     * Calculates the expression.
     * @return The result of expression
     */
    public Integer getResult() {
        String[] e = expression.split(" ");

        for (String current : e) {
            if (isNumber(current)) {
                Integer d = Integer.parseInt(current);
                numberStack.push(d);
            } else if (isOperation(current)) {
                Integer b = numberStack.pop();
                Integer a = numberStack.pop();
                Integer result = calculate(a, b, current);
                numberStack.push(result);
            }
        }
        return numberStack.pop();
    }


    private Boolean isNumber(String a) {
        return a.matches("^[0-9]+$");
    }

    private Boolean isOperation(String a) {
        return a.matches("^[\\Q+-*/\\E]$");
    }

    private Integer calculate(Integer a, Integer b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new IllegalArgumentException(operator + " is not an operator.");
        }
    }

    private Boolean oneMoreOperandsThanOperators(String... exp) {
        int amountOfOperands = 0;
        int amountOfOperators = 0;

        for (String str : exp) {
            if (isNumber(str)) {
                amountOfOperands++;
            } else if (isOperation(str)) {
                amountOfOperators++;
            } else {
                throw new IllegalArgumentException(str + " is neither a number nor an operator.");
            }
        }

        return amountOfOperands - amountOfOperators == 1;
    }

}
