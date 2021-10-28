package ru.mail.senokosov.artem.operation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mail.senokosov.artem.operation.enums.Operators;
import ru.mail.senokosov.artem.operation.math.Division;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class DivisionProcessorTest {

    @InjectMocks
    private Division divisionProcessor;

    @Test
    void shouldGetDivisionOperator() {
        Operators operators = divisionProcessor.getOperator();

        assertThat(operators).isEqualTo(Operators.DIVISION);
    }

    @Test
    void shouldProcessDivision() {
        Operators operators = Operators.DIVISION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(100));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));
        actualCalculatorStack.push(BigDecimal.valueOf(10000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(100));
        expectedCalculatorStack.push(BigDecimal.valueOf(0.1));

        divisionProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldNotProcessDivision() {
        Operators operators = Operators.SUBTRACTION;

        Deque<BigDecimal> actualCalculatorStack = new ArrayDeque<>();
        actualCalculatorStack.push(BigDecimal.valueOf(10));
        actualCalculatorStack.push(BigDecimal.valueOf(1000));

        Deque<BigDecimal> expectedCalculatorStack = new ArrayDeque<>();
        expectedCalculatorStack.push(BigDecimal.valueOf(10));
        expectedCalculatorStack.push(BigDecimal.valueOf(1000));


        divisionProcessor.process(operators, actualCalculatorStack);

        assertThat(actualCalculatorStack).isEqualTo(expectedCalculatorStack);
    }

    @Test
    void shouldThrowExceptionForProcessDivision() {
        Operators operators = Operators.DIVISION;

        Deque<BigDecimal> numbers = new ArrayDeque<>();
        numbers.push(BigDecimal.valueOf(10));

        try {
            divisionProcessor.validate(operators, numbers);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("2 operand(s) are required!");
        }
    }
}