package fr.projetcalculmental.calculgenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// equation level 2
public class EquationLineaire {

    public static Map<String, Object> generate(int roundRange, int variationC, boolean floatAbleX) {
        double a = (Math.random() * (Math.random() > 0.5 ? 12 : -10));
        a = Math.round(a * 2) / 2.0;
        if (a == 0) {
            a = 0.5;
        }

        double b = Math.round(Math.random() * (Math.random() > 0.5 ? 12 : -10) * Math.pow(10, roundRange)) / Math.pow(10, roundRange);
        int rand = new Random().nextInt(variationC * 2 + 1) - variationC;
        double c = rand * a + b + (floatAbleX ? 0.5 * a : 0);

        String calc = String.format("%.1fx %s %.1f = %.1f", a, b < 0 ? "-" : "+", Math.abs(b), c);
        String calcForEval = String.valueOf((c - b) / a);
        calc = calc.replaceAll(",0", "");
        calc = calc.replaceAll(",", ".");
        calc = calc.replaceAll("\\.0", "");

        Map<String, Object> result = new HashMap<>();
        result.put("calcule", calc);
        result.put("resultat", calcForEval);

        return result;
    }
}
