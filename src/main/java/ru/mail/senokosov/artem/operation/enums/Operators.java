package ru.mail.senokosov.artem.operation.enums;

public enum Operators {

    DEGREE("^", 2, 10),
    MULTIPLICATION("*", 2, 9),
    DIVISION("/", 2, 9),
    ADDITION("+", 2, 8),
    SUBTRACTION("-", 2, 8);

    private final String symbol;

    private final Integer requiredOperandsCount;

    private final Integer priority;

    Operators(String symbol, Integer requiredOperandsCount, Integer priority) {
        this.symbol = symbol;
        this.requiredOperandsCount = requiredOperandsCount;
        this.priority = priority;
    }

    public static Operators fromString(String s) {
        for (Operators operators : Operators.values()) {
            if (operators.getSymbol().equals(s)) {
                return operators;
            }
        }

        return null;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getRequiredOperandsCount() {
        return requiredOperandsCount;
    }

    public Integer getPriority() {
        return priority;
    }
}