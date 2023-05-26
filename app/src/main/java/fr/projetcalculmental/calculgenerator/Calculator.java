package fr.projetcalculmental.calculgenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Calculator {

    public static Map<String, Object> getEasyCalcul() {
        String[] operands = {"+", "-", "*"};
        return EquationSimple.generate(3, 20,
                List.of(operands), false, 0, 2, 0, 9, false);
    }

    public static Map<String, Object> getMediumCalcul() {

        Random random = new Random();

        String[] operands = {"+", "-", "*"};
        return EquationSimple.generate(2, 60,
                List.of(operands), true, 0, 2, 0, 15, true);

    }

    public static Map<String, Object> getHardCalcul() {

        Random random = new Random();
        int calculRandom = random.nextInt(3);

        if(calculRandom < 2) {
            String[] operands = {"+", "*"};
            return EquationSimple.generate(3, 80,
                    List.of(operands), true, 0, 2, 40, 9, true);
        } else {
            return EquationLineaire.generate(0, 3, false);
        }
    }

    public static Map<String, Object> getImpossibleCalcul() {

        Random random = new Random();
        int calculRandom = random.nextInt(3);

        if(calculRandom == 0) {
            String[] operands = {"+", "*"};
            return EquationSimple.generate(3, 90,
                    List.of(operands), true, 0, 2, 60, 11, true);
        } else if(calculRandom == 1) {
            return EquationLineaire.generate(0, 3, false);
        } else {
            return EquationSigma.generate(3, 3, true,
                    true, true, 25);
        }
    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

}
