package aoc.dec_2024.day_02;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

public class RedNosedReports {

    private PuzzleInputLoader puzzleInputLoader;

    public RedNosedReports() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    private void solvePuzzle1() {
        System.out.println("Hello World!");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_02/RedNosedReportsTest.txt");
        puzzleContents.forEach(System.out::println);
    }

    public static void main(String[] args) {
        RedNosedReports redNosedReports = new RedNosedReports();
        redNosedReports.solvePuzzle1();
    }

}
