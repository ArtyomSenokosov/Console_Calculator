package ru.mail.senokosov.artem;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        String expression = "";
        Scanner input = new Scanner(System.in);

        System.out.println("Calculator");

        MathElements.setCalculator();

        double result = 0d;
        List<String> rpn = null;
        boolean flag = true;

        while (flag) {
            System.out.println("Please, enter expression (and press enter): \n");
            expression = input.nextLine();

            try {
                rpn = ReversePolishNotationConverter.sortingStation(expression);
                System.out.println("reverse polish notation is:");
                ReversePolishNotationConverter.printRPN(rpn);
                result = StackMachine.evaluatingReversePolishNotation(rpn);

                System.out.println("\nresult:\n" + result + "\n");

            } catch (IOException exception) {
                System.out.println(exception.getMessage() + "\n");
            }

            System.out.println("Do You want to re-enter expression? \n"
                    + "press any key to another calculation, or \n"
                    + "enter \"E\" to exit.");

            if (!input.nextLine().equalsIgnoreCase("e")) {
                flag = true;
            } else {
                flag = false;
            }
        }
        input.close();
    }
}