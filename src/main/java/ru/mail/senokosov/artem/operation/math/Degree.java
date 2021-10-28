package ru.mail.senokosov.artem.operation.math;

import ru.mail.senokosov.artem.operation.AbstractMathOperator;
import ru.mail.senokosov.artem.operation.enums.Operators;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;

public class Degree extends AbstractMathOperator {

    @Override
    public Operators getOperator() {
        return Operators.DEGREE;
    }

    @Override
    public void operate(Deque<BigDecimal> calculatorStack, MathContext mc) {

        BigDecimal secondNumber = calculatorStack.pop();
        BigDecimal firstNumber = calculatorStack.pop();

        try {
            calculatorStack.push(firstNumber.pow(secondNumber.intValue(), mc));
        } catch (ArithmeticException e) {
            calculatorStack.push(firstNumber);
            throw e;
        }
    }
}
