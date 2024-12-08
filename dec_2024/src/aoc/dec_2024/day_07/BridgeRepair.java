package aoc.dec_2024.day_07;

import aoc.dec_2024.helper.MathHelper;
import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeRepair {

    private final List<String> puzzleRows;

    public BridgeRepair() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_07/BridgeRepairTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_07/BridgeRepair.txt");
        this.puzzleRows = puzzleContents.getPuzzleRows();
    }

    public long solvePuzzle1() {
        long totalCalibrationResult = 0;
//        for (String calibrationEquation : puzzleRows) {
//        String calibrationEquation = "26324383: 7 9 8 7 414 471 185 8";
        String calibrationEquation = "11884578: 26 91 864 391 590 78";     // TODO Goudemond 2024/12/08 | Should this be accepted?
        String[] calibrationArguments = calibrationEquation.split(": ");
        long product = getResult(calibrationArguments);
        List<String> calibrationInputs = getCalibrationInputs(calibrationArguments);
        List<String> allPossibleEquations = getAllPossibleEquations(calibrationInputs);
        for (String expression : allPossibleEquations) {
            long result = MathHelper.evalLeftToRight(expression);
            System.out.printf("%s = %s%n", expression, result);
            if (result == product) {
                System.out.printf("%s = %s%n", expression, result);
                totalCalibrationResult += result;
                break;
            }
//            }
        }
        // Reddit spoilers say I should be getting something like (146111650210682)
        // they seem to be concatenating as a 3rd operation!?!?!?!?
        return totalCalibrationResult; // 51277701 (too low) ; 51277870 (too low)
    }

    private List<String> getAllPossibleEquations(List<String> calibrationInputs) {
        int numberOperatorsNeeded = calibrationInputs.size() - 1;
        int permutationCount = 0;
        String defaultExpression = String.join("+", calibrationInputs);
        List<String> expressions = new ArrayList<>();
        List<String> operations = Arrays.stream(defaultExpression.split("(\\d+)"))
                .collect(Collectors.toList());
        expressions.add(defaultExpression);
        double allPossiblePermutations = Math.pow(2, numberOperatorsNeeded) - 1;
        while (permutationCount < allPossiblePermutations) {
            permutationCount++;
            String permutationAsBinaryString = getPermutationAsBinaryString(numberOperatorsNeeded, permutationCount);
            String expression = createExpression(calibrationInputs, permutationAsBinaryString, operations);
            expressions.add(expression);
        }
        return expressions;

    }

    private String getPermutationAsBinaryString(int numberOperatorsNeeded, int permutationCount) {
        String permutationAsBinaryString = String.format("%" + numberOperatorsNeeded + "s", Integer.toBinaryString(permutationCount));
        permutationAsBinaryString = permutationAsBinaryString.replace(' ', '0');
        return permutationAsBinaryString;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private String createExpression(List<String> calibrationInputs, String formatString, List<String> operations) {
        String[] permutationAsBinaryString = formatString.split("");
        int operatorPlacementIndex = operations.size() - 1;
        String startingInput = calibrationInputs.get(0);
        for (String permutation : permutationAsBinaryString) {
            if (permutation.equals("0")) {
                operations.set(operatorPlacementIndex, "+");
            } else {
                operations.set(operatorPlacementIndex, "*");
            }
            operatorPlacementIndex--;
        }
        String expression = startingInput;
        int j = 1;
        for (int i = calibrationInputs.size() - 1; i > 0; i--) {
            expression += operations.get(i) + calibrationInputs.get(j++);
        }
        return expression;
    }

    private List<String> getCalibrationInputs(String[] calibrationArguments) {
        return Arrays.stream(calibrationArguments[1].split(" "))
//                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private long getResult(String[] calibrationArguments) {
        return Long.parseLong(calibrationArguments[0]);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        BridgeRepair bridgeRepair = new BridgeRepair();
        long puzzle1solution = bridgeRepair.solvePuzzle1();
        System.out.println("puzzle1solution = " + puzzle1solution);
    }
}
