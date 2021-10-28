package ru.mail.senokosov.artem.operation;

import ru.mail.senokosov.artem.operation.enums.Operators;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;

public interface MathOperator {

    void process(Operators operators, Deque<BigDecimal> numbers);

    void operate(Deque<BigDecimal> numbers, MathContext mathContext);

    Operators getOperator();
}
