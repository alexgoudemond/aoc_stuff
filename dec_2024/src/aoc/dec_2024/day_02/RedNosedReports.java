package aoc.dec_2024.day_02;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.List;
import java.util.stream.Collectors;

public class RedNosedReports {

    private PuzzleInputLoader puzzleInputLoader;

    public RedNosedReports() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    private int solvePuzzle1() {
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_02/RedNosedReportsTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_02/RedNosedReports001.txt");
        int safeLevels = 0;
        for (int i = 0; i < puzzleContents.getNumberOfRows(); i++) {
            List<Integer> level = puzzleContents.getPuzzleRow(i).stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            if (bothIncreasingAndDecreasing(level)) {
                continue;
            }
            if (levelIsSafe(level)) {
                safeLevels++;
            }
        }
        return safeLevels; // 246 --> correct
    }

    private boolean bothIncreasingAndDecreasing(List<Integer> level) {
        return !increasingLevel(level) && !decreasingLevel(level);
    }

    private boolean levelIsSafe(List<Integer> level) {
        List<Integer> safeLevels = List.of(1, 2, 3);
        for (int i = 1; i < level.size(); i++) {
            int difference = Math.abs(level.get(i - 1) - level.get(i));
            if (!safeLevels.contains(difference)){
                return false;
            }
        }
        return true;
    }

    private boolean increasingLevel(List<Integer> level) {
        for (int i = 1; i < level.size(); i++) {
            if (level.get(i) <= level.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean decreasingLevel(List<Integer> level) {
        for (int i = 1; i < level.size(); i++) {
            if (level.get(i - 1) <= level.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RedNosedReports redNosedReports = new RedNosedReports();
        int puzzle1Solution = redNosedReports.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
    }

}
