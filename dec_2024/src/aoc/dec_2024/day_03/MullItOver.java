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
        puzzleInputLoader = new PuzzleInputLoaderImpl("");
    }

    private long solvePuzzle1() {
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_03/MullItOverTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_03/MullItOver001.txt");
        List<String> programMemory = puzzleContents.getRawPuzzleContent();
        long finalSum = 0L;
        for (String memoryAddress : programMemory) {
            List<String> programInstructions = getProgramInstructions(memoryAddress);
//            System.out.println("programInstructions = " + programInstructions);
            long result = multiplyInstructionsAndSumTheResults(programInstructions);
            finalSum += result;
        }
        return finalSum; // 28434750 -- incorrect ; 162813399 --> Correct!
    }

    private long solvePuzzle2() {
//                PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_03/MullItOverTest2.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_03/MullItOver001.txt");
        List<String> programMemory = puzzleContents.getRawPuzzleContent();
        long finalSum = 0L;
        String singleMemory = "";
        for (String memoryAddress : programMemory) {
            singleMemory += memoryAddress;
        }
        String newMemoryAddress = removeDontInstruction(singleMemory);
        List<String> programInstructions = getProgramInstructions(newMemoryAddress);
//            System.out.println("programInstructions = " + programInstructions);
        long result = multiplyInstructionsAndSumTheResults(programInstructions);
        finalSum += result;
        return finalSum;
        // 69631176 -- incorrect ;
        // 99897464 -- incorrect ;
        // 58488142 -- incorrect ;
        // 66505565 -- incorrect ;
        // 53783319 --> Correct! (1 massive file, add 'do()' at end)
    }

    private String removeDontInstruction(String memoryAddress) {
        String regexRemoveDontInstruction = "don't.*?do\\(\\)"; // (Goudemond 20241203) The '?' makes it non-greedy
        memoryAddress = memoryAddress.concat(" do()");
        return memoryAddress.replaceAll(regexRemoveDontInstruction, "#####");
    }

    private long multiplyInstructionsAndSumTheResults(List<String> programInstructions) {
        Pattern pattern = Pattern.compile("([^mul()]+)");
        Matcher matcher;
        long result = 0L;
        for (String programInstruction : programInstructions) {
            matcher = pattern.matcher(programInstruction);
            if (matcher.find()) {
                String[] arguments = matcher.group().split(",");
                Long argument1 = Long.parseLong(arguments[0]);
                Long argument2 = Long.parseLong(arguments[1]);
                long multiplication = argument1 * argument2;
//                System.out.println(argument1 + " * " + argument2 + " = " + multiplication);
                result += multiplication;
//                System.out.println("result = " + result);
            }
        }
        return result;
    }

    private List<String> getProgramInstructions(String programMemory) {
        List<String> programInstructions = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(programMemory);
        while (matcher.find()) {
            String instruction = programMemory.substring(matcher.start(), matcher.end());
            programInstructions.add(instruction);
        }
        return programInstructions;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        MullItOver mullItOver = new MullItOver();
        long puzzle1Solution = mullItOver.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        long puzzle2Solution = mullItOver.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }
}
