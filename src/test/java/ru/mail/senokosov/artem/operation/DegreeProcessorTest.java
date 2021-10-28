package ru.mail.senokosov.artem.operation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mail.senokosov.artem.operation.enums.Operators;
import ru.mail.senokosov.artem.operation.math.Degree;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class DegreeProcessorTest {

    @InjectMocks
    private Degree degreeProcessor;

    @Test
    void shouldGetSqrtOperator() {
        Operators operators = degreeProcessor.getOperator();

        assertThat(operators).isEqualTo(Operators.DEGREE);
    }

    @Test
    void shouldProcessSquareRoot() {
        Operators operators = Operators.DEGREE;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(2));
        actualCalculatorStack.push(BigDecimal.valueOf(2));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(4));

        degreeProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldNotProcessSquareRoot() {
        Operators operators = Operators.SUBTRACTION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(1000));

        degreeProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldThrowExceptionForProcessSquareRoot() {
        Operators operators = Operators.DEGREE;

        Deque<BigDecimal> numbers = new ArrayDeque<>();
        numbers.push(BigDecimal.valueOf(10));

        try {
            degreeProcessor.validate(operators, numbers);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("2 operand(s) are required!");
        }
    }
}