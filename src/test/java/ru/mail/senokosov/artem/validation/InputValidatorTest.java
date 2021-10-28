package ru.mail.senokosov.artem.validation;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class InputValidatorTest {

    @Test
    void shouldValidateInputIsNotNull() {
        try {
            InputValidator.validateIfInputNotNull("*");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void shouldValidateInputIsNull() {
        try {
            InputValidator.validateIfInputNotNull(null);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("Input can not be empty!");
        }
    }

    @Test
    void shouldNotValidateInputStringDueToMoreOperatorsThanNumbers() {
        try {
            InputValidator.validateIfInputIsHasLessOperands(new ArrayDeque<>(), 8, "*");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Too many operators! The operation in position 8 has not enough operands: *");
        }
    }
}