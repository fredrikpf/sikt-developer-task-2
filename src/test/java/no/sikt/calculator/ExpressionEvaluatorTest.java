package no.sikt.calculator;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpressionEvaluatorTest {

    @Test
    void shouldReturnValueWhenExpressionIsValid() {
        var expected = 2;
        var expression = "1 1 +";
        var actual = new ExpressionEvaluator(expression).getResult();
        assertEquals(expected, actual);
    }

    @Test
    void testAllCases() {
        try {
            File myObj = new File("src/test/resources/examples.tsv");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); //Skip header

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                var singleCase = data.split("\t");
                checkSingleCase(singleCase[0], Integer.parseInt(singleCase[1]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void checkSingleCase(String expression, Integer expected) {
        var actual = new ExpressionEvaluator(expression).getResult();
        assertEquals(expected, actual, "Expression: " + expression);
    }


    @Test
    void mathematicallyInvalidExpressionException() {
        String invalidExpression = "1 0 /";
        assertThrows(
                ArithmeticException.class,
                () -> new ExpressionEvaluator(invalidExpression).getResult(),
                invalidExpression + " didn't throw an error!");
    }

    @Test
    void invalidExpressionThrowsException() {
        String[] invalidExpressions = {
            null,
            "+ 1 2 4",
            "d f g 12",
            "1 2 3 4 5 6",
            "1 + + + +"
        };

        for (String invalidExp : invalidExpressions) {
            invalidExpressionThrowsAssertionError(invalidExp);
        }
    }

    private void invalidExpressionThrowsAssertionError(String invalidExpression) {
        assertThrows(
                AssertionError.class,
                () -> new ExpressionEvaluator(invalidExpression).getResult(),
                invalidExpression + " didn't throw an error!");
    }
}
