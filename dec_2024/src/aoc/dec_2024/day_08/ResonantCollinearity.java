package aoc.dec_2024.day_08;

import aoc.dec_2024.helper.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResonantCollinearity {

    private final PuzzleGrid puzzleGrid;

    public ResonantCollinearity() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearityTest.txt");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearity.txt");
        this.puzzleGrid = puzzleContents.getPuzzleGrid();
    }

    // TODO Goudemond 2024/12/10 | Bresenham Line Algorithm? Or super simple?
    private void solvePuzzle1() {
        System.out.println("Hello World!");
        Map<String, List<Coordinate>> uniqueElementsMap = puzzleGrid.getUniqueElements();
//        uniqueElementsMap.forEach((key, value) -> {
//            System.out.println(key + ": " + value);
//        });
//        for (String element : uniqueElementsMap.keySet()) {
//            System.out.println("puzzleGrid.getCoordinatesFor(" + element + ") = " + puzzleGrid.getCoordinatesFor(element));
//        }

    }

    public static void main(String[] args) {
        ResonantCollinearity resonantCollinear = new ResonantCollinearity();
        resonantCollinear.solvePuzzle1();
    }

}
