package aoc.dec_2024.day_03;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    private final PuzzleInputLoader puzzleInputLoader;

    public MullItOver() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    private int solvePuzzle1() {
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_03/MullItOverTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_03/MullItOver001.txt");
        String programMemory = puzzleContents.getRawPuzzleContent().get(0);
        List<String> programInstructions = getProgramInstructions(programMemory);
        System.out.println("programInstructions = " + programInstructions);
        int result = multiplyInstructionsAndSumTheResults(programInstructions);
        return result; // 28434750 -- incorrect
    }

    // TODO Goudemond 2024/12/03 | What am I doing wrong?
    private static int multiplyInstructionsAndSumTheResults(List<String> programInstructions) {
        Pattern pattern = Pattern.compile("([^mul()]+)");
        Matcher matcher;
        int result = 0;
        for (String programInstruction : programInstructions) {
            matcher = pattern.matcher(programInstruction);
            if (matcher.find()){
                String[] arguments = matcher.group().split(",");
                int argument1 = Integer.parseInt(arguments[0]);
                int argument2 = Integer.parseInt(arguments[1]);
                int multiplication = argument1 * argument2;
                System.out.println(argument1 + " * " + argument2 + " = " + multiplication);
                result += multiplication;
            }
        }
        return result;
    }

    private static List<String> getProgramInstructions(String programMemory) {
        List<String> programInstructions = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(programMemory);
        while(matcher.find()){
            String instruction = programMemory.substring(matcher.start(), matcher.end());
            programInstructions.add(instruction);
        }
        return programInstructions;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        MullItOver mullItOver = new MullItOver();
        int puzzle1Solution = mullItOver.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
    }
}
