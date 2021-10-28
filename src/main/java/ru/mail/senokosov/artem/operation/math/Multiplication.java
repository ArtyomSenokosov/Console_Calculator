package ru.mail.senokosov.artem.operation.math;

import ru.mail.senokosov.artem.operation.AbstractMathOperator;
import ru.mail.senokosov.artem.operation.enums.Operators;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;

public class Multiplication extends AbstractMathOperator {

    @Override
    public Operators getOperator() {
        return Operators.MULTIPLICATION;
    }

    @Override
    public void operate(Deque<BigDecimal> calculatorStack, MathContext mc) {

        BigDecimal result = calculatorStack.pop().multiply(calculatorStack.pop(), mc);

        calculatorStack.push(result);
    }
}