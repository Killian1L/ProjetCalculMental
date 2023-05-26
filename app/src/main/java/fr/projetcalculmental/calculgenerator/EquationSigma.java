package fr.projetcalculmental.calculgenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// equation level 3
public class EquationSigma {

    public static Map<String, Object> generate(int xRange, int iRange, boolean iInsertAble, boolean iPowerAble,
                                               boolean iConstance, int iPowerPercent) {
        int iPowerRate = 10 - iPowerPercent / 10;
        String[] operators = {"+", "-", "*"};
        Random random = new Random();
        int x = random.nextInt(2 * xRange + 1) - xRange;
        Map<String, Object> calculation = EquationSimple.generate(5, 50, List.of(operators), true, 0, 2, 50, 6, true);
        String E = (String) calculation.get("calcule");

        int iStart = iPowerAble ? 0 : random.nextInt(2 * iRange + 1) - iRange;
        int iEnd = random.nextInt(iRange - iStart + 1) + iStart;
        StringBuilder calc = new StringBuilder();
        StringBuilder iE = new StringBuilder();

        if (iConstance && !iPowerAble && !iInsertAble) {
            int rd = random.nextInt(4);
            if (rd == 0) {
                calc.append(x).append(" * Σ <de i = ").append(iStart).append(" jusqu'à ").append(iEnd).append("> [(").append(E).append(") + i]");
            } else if (rd == 1) {
                calc.append(x).append(" * Σ <de i = ").append(iStart).append(" jusqu'à ").append(iEnd).append("> [(").append(E).append(") - i]");
            } else if (rd == 2) {
                calc.append(x).append(" * Σ <de i = ").append(iStart).append(" jusqu'à ").append(iEnd).append("> [i + (").append(E).append(")]");
            } else {
                calc.append(x).append(" * Σ <de i = ").append(iStart).append(" jusqu'à ").append(iEnd).append("> [i - (").append(E).append(")]");
            }
        } else if (!iConstance && !iPowerAble && !iInsertAble) {
            int rd = random.nextInt(2);
            if (rd == 0) {
                calc.append(x).append(" * Σ <de i = ").append(iStart).append(" jusqu'à ").append(iEnd).append("> [(").append(E).append(") * i]");
            } else {
                calc.append(x).append(" * Σ <de i = ").append(iStart).append(" jusqu'à ").append(iEnd).append("> [i * (").append(E).append(")]");
            }
        } else {
            boolean iIsInsert = false;
            String[] eSplit = E.split(" ");
            if (eSplit[eSplit.length - 1].equals("")) {
                eSplit = Arrays.copyOf(eSplit, eSplit.length - 1);
            }
            for (int index = 0; index < eSplit.length; index++) {
                String expression = eSplit[index];
                if (!expression.contains("+") && !expression.contains("-") && !expression.contains("*") && !expression.contains("/")) {
                    if (expression.contains("^")) {
                        String number = expression.split("\\^")[0].replace("^", "").replace("(", "").replace(")", "");
                        if (!iIsInsert && (random.nextInt(6) == 3 || index >= eSplit.length - 3)) {
                            if (iPowerAble && random.nextInt(11) > iPowerRate) {
                                iE.append(number).append("^i");
                            } else {
                                iE.append(expression.replace(number + '^', "i^")).append(" ");
                            }
                            iIsInsert = true;
                        } else {
                            iE.append(expression).append(" ");
                        }
                    }
                    else {
                        if (!iIsInsert && (random.nextInt(6) == 3 || index >= eSplit.length - 3)) {
                            iE.append(expression.replaceFirst("\\d+", " i")).append(" ");
                            iIsInsert = true;
                        } else {
                            iE.append(expression).append(" ");
                        }
                    }
                } else {
                    if (!iIsInsert && (random.nextInt(6) == 3 || index >= eSplit.length - 3)) {
                        iE.append(expression.replaceFirst("\\d+", " i")).append(" ");
                        iIsInsert = true;
                    } else {
                        iE.append(expression).append(" ");
                    }

                }
            }

            if (iE.toString().contains("/ i") && iStart < 0 && iEnd > 0) {
                if (Math.abs(iStart) > iEnd) {
                    iEnd = -1;
                } else {
                    iStart = 1;
                }
            } else if (iE.toString().contains("/ i") && iStart == 0) {
                iStart = 1;
            } else if (iE.toString().contains("/ i") && iEnd == 0) {
                iEnd = -1;
            }

            calc.append(x).append(" * Σ<de i=").append(iStart).append(" à ").append(iEnd).append("> [").append(iE).append("]");
        }

        calc = new StringBuilder(calc.toString().replace("0^0", "1"));

        StringBuilder eNormal = new StringBuilder();
        String[] e = calc.substring(calc.indexOf("[") + 1, calc.length() - 1).split(" ");

        // cration calc
        for (int i = iStart; i <= iEnd; i++) {
            StringBuilder eINormal = new StringBuilder();
            for (String expression : e) {
                if (expression.contains("^")) {
                    String number = expression.split("\\^")[0].replace("(", "").replace(")", "");
                    String exp = expression.split("\\^")[1].replace("(", "").replace(")", "");
                    String motif = expression.contains("(" + number + ")^" + exp) ? "(" + number + ")^" + exp : number + "^" + exp;
                    StringBuilder newExpression = new StringBuilder();

                    if (!exp.equals("i")) {
                        if (Integer.parseInt(exp) == 0) {
                            newExpression.append(expression.replace(motif, "1"));
                        } else if (Integer.parseInt(exp) == 1) {
                            newExpression.append(expression.replace(motif, number));
                        } else {
                            for (int index = 0; index <= Integer.parseInt(exp); index++) {
                                newExpression.append(number).append("*");
                            }
                            if (newExpression.charAt(newExpression.length() - 1) == '*') {
                                newExpression = new StringBuilder(newExpression.substring(0, newExpression.length() - 1));
                            }
                            newExpression = new StringBuilder(expression.replace(motif, "(" + newExpression.toString() + ")" ));
                        }
                    } else {
                        exp = "" + i;
                        if (Integer.parseInt(exp) == 0) {
                            newExpression.append(expression.replace(motif, "1"));
                        } else if (Integer.parseInt(exp) == 1) {
                            newExpression.append(expression.replace(motif, number));
                        } else {
                            for (int index = 0; index < i; index++) {
                                newExpression.append(number).append("*");
                            }
                            if (newExpression.charAt(newExpression.length() - 1) == '*') {
                                newExpression = new StringBuilder(newExpression.substring(0, newExpression.length() - 1));
                            }
                            newExpression = new StringBuilder(expression.replace(motif, newExpression.toString()));
                        }
                    }
                    eINormal.append(newExpression.toString().replace("i", String.valueOf(i))).append(" ");
                } else {
                    eINormal.append(expression.replace("i", String.valueOf(i))).append(" ");
                }
            }
            eNormal.append("(").append(eINormal).append(") + ");
        }
        eNormal = new StringBuilder(x + " * (" + eNormal + " 0)");

        double result = Calculator.eval(eNormal.toString());

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("calcule", calc.toString());
        resultData.put("resultat", result);

        return resultData;
    }
}
