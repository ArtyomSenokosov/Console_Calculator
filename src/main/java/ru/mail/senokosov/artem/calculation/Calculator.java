package ru.mail.senokosov.artem.calculation;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.List;

public interface Calculator {

    void init();

    Deque<BigDecimal> calculate(List<String> input);
}
