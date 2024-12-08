package aoc.dec_2024.helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MathHelper {

    private MathHelper() {}

    public static long evalLeftToRight(String expression){
        List<Long> inputs = Arrays.stream(expression.split("[\\+\\*]"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<String> operators = Arrays.stream(expression.split("(\\d+)"))
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
        long startingInput = inputs.get(0);
        int nextIndex = 1;
        long result = startingInput;
        for (String operator : operators) {
            long nextInput = inputs.get(nextIndex++);
            if (operator.equals("+")) {
                result = result + nextInput;
            }else{
                result = result * nextInput;
            }
        }
        return result;
    }
}
