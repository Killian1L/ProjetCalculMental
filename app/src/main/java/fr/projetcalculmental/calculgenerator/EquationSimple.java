package fr.projetcalculmental.calculgenerator;

import fr.projetcalculmental.calculgenerator.Calculator;
import java.util.*;

// simple calcule, level 1
public class EquationSimple {
    public static Map<String, Object> generate(
            Integer max_operands, Integer parenthesis_percent, List<String> operators_list,
            Boolean division, Integer round_range, Integer max_power, Integer power_rate_percente,
            Integer n_range, boolean n_negative) {

        int parenthesis_rate = 5 - (int) (parenthesis_percent * 5 / 100);
        int power_rate = 10 - (int) (power_rate_percente / 10);
        Random random = new Random();

        StringBuilder calculation = new StringBuilder();
        int num_operands = random.nextInt(max_operands - 1) + 2;
        int num_operator = num_operands - 1;

        int total_lenght = num_operator + num_operands;
        List<Integer> numbers = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        for (int index = 0; index < num_operands; index++) {
            double randomNumber;
            if(n_negative)
                randomNumber = random.nextInt(n_range*2) -n_range;
            else randomNumber = random.nextInt(n_range);
            numbers.add((int) randomNumber);
        }

        for (int index = 0; index < num_operator; index++) {
            operators.add(operators_list.get(random.nextInt(operators_list.size())));
        }

        // joindre opérateurs & opérandes
        int index_operator = 0;
        int last_parenthesis_index = -1;
        boolean parenthesis_open = false;

        for (int index = 0; index < numbers.size(); index++) {
            double number = numbers.get(index);
            String casted_number;
            if (index_operator < operators.size() && Arrays.asList("/", "*").contains(operators.get(index_operator))) {
                casted_number = Integer.toString((int) (number + 1));
            } else {
                casted_number = Double.toString(Math.round(number * 2) / 2.0);
            }
            if ((Math.round(number * 2) / 2.0 == (int) number || Math.round(number * 2) / 2.0 == (int) number + 1) &&
                    random.nextInt(11) > power_rate && Math.round(number * 2) / 2.0 != 0) {
                if (casted_number.charAt(0) == '-') {
                    int power = random.nextInt(max_power + 1);
                    if (power == 0) power = 1;
                    calculation.append("(").append(casted_number).append(")^#").append(power).append("#");
                } else {
                    int power = random.nextInt(max_power + 1);
                    if (power == 0) power = 1;
                    calculation.append(casted_number).append("^#").append(power).append("#");
                }
            } else {
                calculation.append(casted_number);
            }
            if (!parenthesis_open && random.nextInt(6) > parenthesis_rate &&
                    last_parenthesis_index != index && index + 2 < numbers.size() && total_lenght > 2) {

                parenthesis_open = true;
                last_parenthesis_index = index;
                calculation.append(" ").append(operators.get(index_operator)).append(" (");
            } else if (random.nextInt(6) > parenthesis_rate - 1 && parenthesis_open && last_parenthesis_index + 2 < index && index_operator < operators.size()) {
                parenthesis_open = false;
                last_parenthesis_index = index;
                if (random.nextInt(11) > 7 && division) {
                    calculation.append(" / 2 ");
                }
                calculation.append(") ").append(operators.get(index_operator)).append(" ");
            } else if (index_operator < operators.size()) {
                calculation.append(" ").append(operators.get(index_operator)).append(" ");
            }
            index_operator++;
        }

        if (parenthesis_open) {
            if (division) {
                calculation.append(") / 2");
            } else {
                calculation.append(")");
            }
        }

        String calc = calculation.toString().replaceAll("#", "");
        if (round_range == 0) {
            calc = calc.toString().replaceAll("\\.0", "");
        }

        Map<String, Object> result_map = new HashMap<>();
        result_map.put("calcule", calc);
        result_map.put("resultat", Calculator.eval(calc));
        return result_map;
    }
}
