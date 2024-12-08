package aoc.dec_2024.day_07;

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
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_07/BridgeRepairTest.txt");
        this.puzzleRows = puzzleContents.getPuzzleRows();
    }

    public void solvePuzzle1() {
//        puzzleRows.forEach(System.out::println);
        String calibrationEquation = puzzleRows.get(1);
        String[] calibrationArguments = calibrationEquation.split(": ");
        int result = getResult(calibrationArguments);
        List<String> calibrationInputs = getCalibrationInputs(calibrationArguments);
        System.out.println("result = " + result);
        // binary string to show all possible combos
        List<String> allPossibleEquations = getAllPossibleEquations(result, calibrationInputs);
        System.out.println("allPossibleEquations = " + allPossibleEquations);
        // TODO Goudemond 2024/12/07 | implement custom evalLeftToRight()

    }

    private List<String> getAllPossibleEquations(int result, List<String> calibrationInputs) {
        int numberOperatorsNeeded = calibrationInputs.size() - 1;
        int permutationCount = 0;
        String defaultExpression = String.join("+", calibrationInputs);
        List<String> operations = Arrays.stream(defaultExpression.split("(\\d+)"))
                .collect(Collectors.toList());
        List<String> expressions = new ArrayList<>();
        expressions.add(defaultExpression);
        while (permutationCount <= numberOperatorsNeeded) {
            permutationCount++;
            String permutationAsBinaryString = getPermutationAsBinaryString(numberOperatorsNeeded, permutationCount);
            String expression = createExpression(calibrationInputs, permutationAsBinaryString, operations);
            expressions.add(expression);
        }
        return expressions;

    }

    private static String getPermutationAsBinaryString(int numberOperatorsNeeded, int permutationCount) {
        String permutationAsBinaryString = String.format("%" + numberOperatorsNeeded + "s", Integer.toBinaryString(permutationCount));
        permutationAsBinaryString = permutationAsBinaryString.replace(' ', '0');
        return permutationAsBinaryString;
    }

    private static String createExpression(List<String> calibrationInputs, String formatString, List<String> operations) {
        String[] permutationAsBinaryString = formatString.split("");
        int operatorPlacementIndex = operations.size() - 1;
        for (String permutation : permutationAsBinaryString) {
            if (permutation.equals("0")) {
                operations.set(operatorPlacementIndex, "+");
            } else {
                operations.set(operatorPlacementIndex, "*");
            }
            operatorPlacementIndex--;
        }
        String startingInput = calibrationInputs.get(0);
        String expression = startingInput;
        for (int i = calibrationInputs.size() - 1; i > 0; i--) {
            expression += operations.get(i) + calibrationInputs.get(i);
        }
        return expression;
    }

    private static List<String> getCalibrationInputs(String[] calibrationArguments) {
        return Arrays.stream(calibrationArguments[1].split(" "))
//                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static int getResult(String[] calibrationArguments) {
        return Integer.parseInt(calibrationArguments[0]);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        BridgeRepair bridgeRepair = new BridgeRepair();
        bridgeRepair.solvePuzzle1();
    }
}
