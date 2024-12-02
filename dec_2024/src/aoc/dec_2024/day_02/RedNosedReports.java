package aoc.dec_2024.day_02;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.ArrayList;
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
            List<Integer> report = puzzleContents.getPuzzleRow(i).stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            if (bothIncreasingAndDecreasing(report)) {
                continue;
            }
            if (reportIsSafe(report)) {
                safeLevels++;
            }
        }
        return safeLevels; // 246 --> correct
    }

    private int solvePuzzle2() {
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("/day_02/RedNosedReportsTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_02/RedNosedReports001.txt");
        int safeLevels = 0;
        List<List<Integer>> reportsToDoubleCheck = new ArrayList<>();
        for (int i = 0; i < puzzleContents.getNumberOfRows(); i++) {
            List<Integer> report = puzzleContents.getPuzzleRow(i).stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            if (bothIncreasingAndDecreasing(report) || !reportIsSafe(report)) {
                reportsToDoubleCheck.add(report);
                continue;
            }
            safeLevels++;
        }
        for (List<Integer> integers : reportsToDoubleCheck) {
            for (int k = 0; k < integers.size(); k++) {
                List<Integer> reportToCheck = new ArrayList<>(integers);
                reportToCheck.remove(k);
                if (bothIncreasingAndDecreasing(reportToCheck)) {
                    continue;
                }
                if (reportIsSafe(reportToCheck)) {
                    safeLevels++;
                    break;
                }
            }
        }
        return safeLevels; // 409 --> incorrect ; 318 --> correct!
    }

    private boolean bothIncreasingAndDecreasing(List<Integer> level) {
        return !increasingReport(level) && !decreasingReport(level);
    }

    private boolean reportIsSafe(List<Integer> level) {
        List<Integer> safeLevels = List.of(1, 2, 3);
        for (int i = 1; i < level.size(); i++) {
            int difference = Math.abs(level.get(i - 1) - level.get(i));
            if (!safeLevels.contains(difference)) {
                return false;
            }
        }
        return true;
    }

    private boolean increasingReport(List<Integer> level) {
        for (int i = 1; i < level.size(); i++) {
            if (level.get(i) <= level.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean decreasingReport(List<Integer> level) {
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
        int puzzle2Solution = redNosedReports.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }

}
