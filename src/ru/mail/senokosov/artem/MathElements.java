package ru.mail.senokosov.artem;

import ru.mail.senokosov.artem.functions.FunctionsWithOneArgument;
import ru.mail.senokosov.artem.functions.FunctionsWithTwoArguments;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MathElements {

    private static Map<String, FunctionsWithOneArgument> oneArgFunctions = new HashMap<>();
    private static Map<String, FunctionsWithTwoArguments> twoArgFunctions = new HashMap<>();
    private static Map<String, Integer> operations = new HashMap<>();

    public static void setCalculator() {
        operations.put("^", 10);
        operations.put("*", 9);
        operations.put("/", 9);
        operations.put("%", 9);
        operations.put("+", 8);
        operations.put("-", 8);
    }

    public static StringTokenizer getTokenizer(String input) {

        String delimiters = ",()";

        for (String operator : operations.keySet()) {
            delimiters = delimiters + operator;
        }

        return new StringTokenizer(input, delimiters, true);
    }

    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFunction(String token) {
        return twoArgFunctions.containsKey(token) || oneArgFunctions.containsKey(token);
    }

    public static boolean isOperator(String token) {
        return operations.containsKey(token);
    }

    public static boolean isOneArgFunction(String token) {
        return oneArgFunctions.containsKey(token);
    }

    public static boolean isTwoArgFunction(String token) {
        return twoArgFunctions.containsKey(token);
    }

    public static Double evaluateOperation(String token, Double firstArg,
                                           Double secondArg) {
        double result = 0d;
        switch (token) {
            case "+":
                result = firstArg + secondArg;
                break;
            case "-":
                result = firstArg - secondArg;
                break;
            case "*":
                result = firstArg * secondArg;
                break;
            case "/":
                result = firstArg / secondArg;
                break;
            case "^":
                result = Math.pow(firstArg, secondArg);
                break;
            default:
                throw new IllegalArgumentException(token + "is unknown operator.");
        }
        return result;
    }

    public static Double evaluateOperation(String token, Double firstArg) {
        double result = 0d;
        switch (token) {
            case "%":
                result = firstArg / 100;
                break;
            default:
                throw new IllegalArgumentException(token + "is unknown operator.");
        }
        return result;
    }

    public static Double evaluateOneArgFunction(String token, Double argument) {
        return oneArgFunctions.get(token).evaluate(argument);
    }

    public static Double evaluateTwoArgFunction(String token, Double firstArg, Double secondArg) {
        return twoArgFunctions.get(token).evaluate(firstArg, secondArg);
    }

    public static int priorityComparator(String operator1, String operator2) {
        int priority1 = operations.get(operator1);
        int priority2 = operations.get(operator2);

        return priority1 - priority2;
    }
}