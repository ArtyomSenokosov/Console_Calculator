package ru.mail.senokosov.artem;

import java.util.List;
import java.util.Stack;

public class StackMachine {

    private static Stack<Double> numberStack = new Stack<>();

    public static double evaluatingReversePolishNotation(List<String> reversePolishNotation) {

        List<String> rpn = reversePolishNotation;
        String token = "";
        double firstArg = 0d;
        double secondArg = 0d;

        lookToNextToken:
        while (!rpn.isEmpty()) {
            token = rpn.get(0);

            if (MathElements.isNumber(token)) {
                numberStack.push(Double.valueOf(token));

                rpn.remove(0);
                continue lookToNextToken;
            }

            if (MathElements.isOperator(token)) {
                if (!token.equals("%")) {
                    secondArg = numberStack.pop();
                    firstArg = numberStack.pop();
                    numberStack.push(MathElements.evaluateOperation(token,
                            firstArg, secondArg));
                } else {
                    firstArg = numberStack.pop();
                    numberStack.push(MathElements.evaluateOperation(token,
                            firstArg));
                }

                rpn.remove(0);
                continue lookToNextToken;
            }
        }
        return numberStack.pop();
    }
}
