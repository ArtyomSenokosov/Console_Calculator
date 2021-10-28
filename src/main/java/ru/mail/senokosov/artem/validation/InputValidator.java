package ru.mail.senokosov.artem.validation;

import ru.mail.senokosov.artem.operation.enums.Operators;

import java.math.BigDecimal;
import java.util.*;

public class InputValidator {

    public static void validateIfInputIsHasLessOperands(Deque<BigDecimal> currentCalculatorStack, int position, String input) {
        if (currentCalculatorStack.size() < Objects.requireNonNull(Operators.fromString(input)).getRequiredOperandsCount())
            throw new IllegalArgumentException("Too many operators! The operation in position " + position + " has not enough operands: " + input);
    }

    public static void validateIfInputNotNull(String input) {
        if (input == null || input.equals(""))
            throw new IllegalStateException("Input can not be empty!");
    }

    public static boolean isNumeric(String strNum) {
        try {
            new BigDecimal(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isOperator(String s) {
        return Operators.fromString(s) != null;
    }

    public static StringTokenizer getTokenizer(String input) {

        StringBuilder delimiters = new StringBuilder(",()");

        for (Operators operators : Operators.values()) {
            delimiters.append(operators.getSymbol());
        }

        return new StringTokenizer(input, delimiters.toString(), true);
    }

    public static int priorityComparator(String operator1, String operator2) {
        int priority1 = Objects.requireNonNull(Operators.fromString(operator1)).getPriority();
        int priority2 = Objects.requireNonNull(Operators.fromString(operator2)).getPriority();

        return priority1 - priority2;
    }

}