package ru.mail.senokosov.artem.operation.math;

import ru.mail.senokosov.artem.operation.AbstractMathOperator;
import ru.mail.senokosov.artem.operation.enums.Operators;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;

public class Subtraction extends AbstractMathOperator {

    @Override
    public Operators getOperator() {
        return Operators.SUBTRACTION;
    }

    @Override
    public void operate(Deque<BigDecimal> calculatorStack, MathContext mc) {

        BigDecimal secondNumber = calculatorStack.pop();
        BigDecimal firstNumber = calculatorStack.pop();

        BigDecimal result = firstNumber.subtract(secondNumber, mc);

        calculatorStack.push(result);
    }
}