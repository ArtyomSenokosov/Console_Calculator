package ru.mail.senokosov.artem;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class MathElementsTest {

    private String firstArg;
    private String secondArg;
    private String operator;
    private String result;

    @Parameterized.Parameters
    public static Collection testData() {
        return Arrays.asList(
                new Object[][]{
                        {"2", "2", "+", "4"},
                        {"2", "2", "-", "0"},
                        {"2", "2", "*", "4"},
                        {"2", "2", "/", "1"},
                        {"2", "2", "^", "4"}
                }
        );
    }

    public MathElementsTest(String firstArg, String secondArg, String operator, String result) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
        this.operator = operator;
        this.result = result;
    }

    @Test
    public void isNumber() {
        String num = "1";
        Assert.assertEquals(Integer.parseInt(num), 1);
    }

    @Test
    public void operationTest() {
        int total = 0;

        int one = Integer.parseInt(this.firstArg);
        int two = Integer.parseInt(this.secondArg);

        switch (this.operator) {
            case "+":
                total = one + two;
                break;
            case "-":
                total = one - two;
                break;
            case "*":
                total = one * two;
                break;
            case "/":
                total = one / two;
                break;
            case "^":
                total = (int) Math.pow(one, two);
                break;
        }

        System.out.print("Total = " + total);
        Assert.assertEquals(Integer.parseInt(this.result), total);
        System.out.println(" Passed ");
    }
}