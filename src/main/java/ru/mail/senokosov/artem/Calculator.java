package ru.mail.senokosov.artem;

import ru.mail.senokosov.artem.calculation.CalculatorImpl;
import ru.mail.senokosov.artem.converter.ReversePolishNotationConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Calculator");

        boolean flag = true;

        while (flag) {
            System.out.println("Please, enter expression (and press enter): \n");
            String expression = input.nextLine();

            try {
                List<String> rpn = ReversePolishNotationConverter.sortingStation(expression);
                System.out.println("reverse polish notation is:");
                ReversePolishNotationConverter.printRPN(rpn);
                ru.mail.senokosov.artem.calculation.Calculator calculator = new CalculatorImpl();
                Deque<BigDecimal> result = calculator.calculate(rpn);

                System.out.println("\nresult:\n" + result + "\n");

            } catch (IOException exception) {
                System.out.println(exception.getMessage() + "\n");
            }

            System.out.println("Do You want to re-enter expression? \n"
                    + "press any key to another calculation, or \n"
                    + "enter \"E\" to exit.");

            flag = !input.nextLine().equalsIgnoreCase("e");
        }
        input.close();
    }
}