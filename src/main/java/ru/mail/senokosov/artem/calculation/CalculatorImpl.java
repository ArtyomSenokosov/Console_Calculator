package ru.mail.senokosov.artem.calculation;

import ru.mail.senokosov.artem.operation.MathOperator;
import ru.mail.senokosov.artem.operation.enums.Operators;
import ru.mail.senokosov.artem.operation.math.*;
import ru.mail.senokosov.artem.validation.InputValidator;

import java.math.BigDecimal;
import java.util.*;

public class CalculatorImpl implements Calculator {

    private final Map<Operators, MathOperator> mathOperators;

    private final Deque<BigDecimal> calculatorStack;

    private final Addition additionProcessor = new Addition();
    private final Subtraction subtractionProcessor = new Subtraction();
    private final Multiplication multiplicationProcessor = new Multiplication();
    private final Division divisionProcessor = new Division();
    private final Degree degreeProcessor = new Degree();

    public CalculatorImpl() {
        mathOperators = new HashMap<>();
        calculatorStack = new ArrayDeque<>();
        init();
    }

    @Override
    public void init() {
        mathOperators.put(Operators.ADDITION, additionProcessor);
        mathOperators.put(Operators.SUBTRACTION, subtractionProcessor);
        mathOperators.put(Operators.MULTIPLICATION, multiplicationProcessor);
        mathOperators.put(Operators.DIVISION, divisionProcessor);
        mathOperators.put(Operators.DEGREE, degreeProcessor);
    }

    @Override
    public Deque<BigDecimal> calculate(List<String> rpnInput) {

        int position = 1;

        for (String input : rpnInput) {

            InputValidator.validateIfInputNotNull(input);

            if (InputValidator.isNumeric(input)) {
                convertAndAddToStacks(input);
            } else if (InputValidator.isOperator(input)) {
                InputValidator.validateIfInputIsHasLessOperands(calculatorStack, position, input);

                Operators operators = Operators.fromString(input);

                processMathOperators(operators);
            } else {
                throw new IllegalArgumentException("The operation in position " + position + " is not supported: " + input);
            }

            position++;
        }

        return calculatorStack;
    }

    private void convertAndAddToStacks(String input) {
        calculatorStack.push(new BigDecimal(input));
    }

    private void processMathOperators(Operators operators) {
        MathOperator processor = mathOperators.get(operators);

        if (processor != null)
            processor.process(operators, calculatorStack);
    }
}