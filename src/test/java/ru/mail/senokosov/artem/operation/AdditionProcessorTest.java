package ru.mail.senokosov.artem.operation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mail.senokosov.artem.operation.enums.Operators;
import ru.mail.senokosov.artem.operation.math.Addition;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class AdditionProcessorTest {

    @InjectMocks
    private Addition additionProcessor;

    @Test
    void shouldGetAdditionOperator() {
        Operators operators = additionProcessor.getOperator();

        assertThat(operators).isEqualTo(Operators.ADDITION);
    }

    @Test
    void shouldProcessAddition() {
        Operators operators = Operators.ADDITION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(100));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));
        actualCalculatorStack.push(BigDecimal.valueOf(10000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(100));
        expectedCalculatorStack.push(BigDecimal.valueOf(11000));

        additionProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldNotProcessAddition() {
        Operators operators = Operators.SUBTRACTION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(1000));

        additionProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldThrowExceptionForProcessAddition() {
        Operators operators = Operators.ADDITION;

        Deque<BigDecimal> numbers = new ArrayDeque<>();
        numbers.push(BigDecimal.valueOf(10));

        try {
            additionProcessor.validate(operators, numbers);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("2 operand(s) are required!");
        }
    }

    @Test
    void shouldThrowExceptionForNullOperator() {
        Deque<BigDecimal> numbers = new ArrayDeque<>();
        numbers.push(BigDecimal.valueOf(10));
        numbers.push(BigDecimal.valueOf(100));

        try {
            additionProcessor.validate(null, numbers);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("The operator cannot be null");
        }
    }
}