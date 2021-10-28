package ru.mail.senokosov.artem.operation;

import ru.mail.senokosov.artem.operation.enums.Operators;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;

public abstract class AbstractMathOperator implements MathOperator {

    public static final int MATH_CONTEXT_PRECISION = 11;

    private final MathContext mathContext = new MathContext(MATH_CONTEXT_PRECISION);

    @Override
    public void process(Operators operators, Deque<BigDecimal> calculatorStack) {
        if (operators.equals(getOperator())) {
            validate(operators, calculatorStack);

            operate(calculatorStack, mathContext);
        }
    }

    public void validate(Operators operators, Deque<BigDecimal> calculatorStack) {
        if (operators == null)
            throw new IllegalStateException("The operators cannot be null");

        if (calculatorStack.size() < operators.getRequiredOperandsCount())
            throw new IllegalStateException(operators.getRequiredOperandsCount() + " operand(s) are required!");
    }

    public abstract Operators getOperator();

    public abstract void operate(Deque<BigDecimal> calculatorStack, MathContext mc);
}