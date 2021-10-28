package ru.mail.senokosov.artem.operation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mail.senokosov.artem.operation.enums.Operators;
import ru.mail.senokosov.artem.operation.math.Subtraction;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(MockitoExtension.class)
class SubtractionProcessorTest {

    @InjectMocks
    private Subtraction subtractionProcessor;

    @Test
    void shouldGetSubtractionOperator() {
        Operators operators = subtractionProcessor.getOperator();

        assertThat(operators).isEqualTo(Operators.SUBTRACTION);
    }

    @Test
    void shouldProcessSubtraction() {
        Operators operators = Operators.SUBTRACTION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(100));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));
        actualCalculatorStack.push(BigDecimal.valueOf(10000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(100));
        expectedCalculatorStack.push(BigDecimal.valueOf(-9000));

        subtractionProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldNotProcessSubtraction() {
        Operators operators = Operators.ADDITION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(1000));

        subtractionProcessor.process(operators, expectedCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldThrowExceptionForProcessSubtraction() {
        Operators operators = Operators.SUBTRACTION;

        Deque<BigDecimal> numbers = new ArrayDeque<>();
        numbers.push(BigDecimal.valueOf(10));

        try {
            subtractionProcessor.validate(operators, numbers);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("2 operand(s) are required!");
        }
    }
}