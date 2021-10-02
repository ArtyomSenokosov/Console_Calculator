package ru.mail.senokosov.artem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class ReversePolishNotationConverter {

    private static String originExpression;
    private static String preparedExpression;
    private static List<String> output = new LinkedList<>();
    private static Stack<String> stackOperators = new Stack<>();
    private static int indexOfCurrentToken = 0;
    private static String token = "";
    private static String previousToken = "";

    public static List<String> sortingStation(String inputExpression) throws IOException {

        if (inputExpression == null || inputExpression.length() == 0) {
            throw new IOException("Mathematical expression isn't specified.");
        }

        originExpression = inputExpression;
        preparedExpression = doPreparations(inputExpression);

        stackOperators.clear();
        output.clear();
        indexOfCurrentToken = 0;
        token = "";
        previousToken = "";

        return sortingStation();
    }

    private static List<String> sortingStation() throws IOException {

        StringTokenizer tokenizer = MathElements.getTokenizer(preparedExpression);

        if (tokenizer.countTokens() < 2) {
            throw new IOException("incomplete expression");
        }

        analyze_next_token:
        while (tokenizer.hasMoreTokens()) {

            previousToken = token;
            token = tokenizer.nextToken();
            indexOfCurrentToken++;

            checkTokens(previousToken, token);

            if (MathElements.isNumber(token)) {
                output.add(token);
                continue analyze_next_token;
            }

            if (token.equals("(")) {
                stackOperators.push(token);
                continue analyze_next_token;
            }

            if (token.equals(",")) {
                while (!stackOperators.isEmpty()
                        && !"(".equals(stackOperators.peek())) {

                    output.add(stackOperators.pop());
                }
                stackOperators.push(token);
                continue analyze_next_token;
            }

            if (token.equals(")")) {
                byte delimiterCounter = 0;
                String top = "";
                while (!stackOperators.empty()) {
                    top = stackOperators.pop();
                    if (top.equals(",")) {
                        delimiterCounter++;
                        continue;
                    }
                    if (!top.equals("(")) {
                        output.add(top);
                    } else {
                        continue analyze_next_token;
                    }
                }
                bugNoLeftBracket();
            }

            if (MathElements.isOperator(token)) {

                while (!stackOperators.isEmpty()
                        && MathElements.isOperator(stackOperators.peek())
                        && MathElements.priorityComparator(token,
                        stackOperators.peek()) <= 0) {

                    output.add(stackOperators.pop());
                }
                stackOperators.push(token);
                continue analyze_next_token;
            }

            errorUnknownArgument(token);
        }

        if (!(MathElements.isNumber(token) || token.equals(")") || token.equals("%"))) {
            throw new IOException("Incorrect last argument: \"" + token + "\"");
        }

        while (!stackOperators.isEmpty()) {
            if ("(".equals(stackOperators.peek())) {
                errorNoRightBracket();
            } else {
                output.add(stackOperators.pop());
            }
        }

        return output;
    }

    public static void printRPN(List<String> sortingStation) {
        for (String sort : sortingStation) {
            System.out.print(sort + " ");
        }
    }

    private static void checkTokens(String previousToken, String currentToken) throws IOException {

        if (!previousToken.isEmpty()) {
            checkAdjacentTokens(previousToken, currentToken);

        } else if (MathElements.isOperator(currentToken)
                || currentToken.equals(")") || currentToken.equals(",")) {

            throw new IOException("Incorrect first argument: \"currentToken\"");
        }
    }

    private static void checkAdjacentTokens(String previousToken,
                                            String currentToken) throws IOException {

        if ((previousToken.equals("(") && currentToken.equals(")"))
                || (previousToken.equals("(") && currentToken.equals(","))
                || (previousToken.equals("(") && MathElements.isOperator(currentToken))
                || (previousToken.equals(")") && currentToken.equals("("))
                || (previousToken.equals(")") && MathElements.isNumber(currentToken))
                || (previousToken.equals(",") && currentToken.equals(")"))
                || (previousToken.equals(",") && MathElements.isOperator(currentToken))
                || (MathElements.isOperator(previousToken) && MathElements.isOperator(currentToken))) {

            int bugPosition = findBugPosition();
            throw new IOException("Uncorrect sequense of math elements \""
                    + previousToken + currentToken + "\" that in the positions #"
                    + (bugPosition - 1) + "-" + bugPosition);
        }
    }

    private static int findBugPosition() {

        String withoutSpaces = originExpression.replace(" ", "");

        StringTokenizer withoutSpacesTokenizer = MathElements.getTokenizer(withoutSpaces);
        StringTokenizer processedTokenizer = MathElements.getTokenizer(preparedExpression);

        String withoutSpacesToken = withoutSpacesTokenizer.nextToken();
        String processedToken = processedTokenizer.nextToken();
        int indexInWithoutSpaces = 1;
        int indexInPreparedExpression = 1;

        while (indexInWithoutSpaces < indexOfCurrentToken) {

            if (withoutSpacesToken.equals(processedToken)) {
                indexInPreparedExpression++;
                if (!withoutSpacesTokenizer.hasMoreTokens()) {
                    break;
                }
                withoutSpacesToken = withoutSpacesTokenizer.nextToken();
            }

            indexInWithoutSpaces++;
            processedToken = processedTokenizer.nextToken();
        }

        return indexInPreparedExpression;
    }

    private static String doPreparations(String processingData) {

        if (processingData.charAt(0) == '-') {
            processingData = "0" + processingData;
        }

        processingData = processingData.replace(" ", "");
        processingData = processingData.replace("(-", "(0-");
        processingData = processingData.replace(",-", ",0-");

        return processingData;
    }

    private static void bugNoLeftBracket() throws IOException {

        int bugPosition = findBugPosition();
        throw new IOException("There is a mistake in math expression: "
                + "\")\", " + "that is in position #: " + bugPosition
                + " don't " + "have appropriate left bracket \"(\" ");
    }

    private static void errorNoRightBracket() throws IOException {

        int bugPosition = findBugPosition();
        throw new IOException("There is a mistake in math expression: "
                + " put right bracket \")\" " + "at last position (#: "
                + (bugPosition + 1) + ") to complete expression");
    }

    private static void errorUnknownArgument(String unknownToken) throws IOException {

        int bugPosition = findBugPosition();
        throw new IOException("There is a mistake in math expression: "
                + "unknown math element \"" + unknownToken + "\" at position #"
                + bugPosition);
    }
}