package aoc.dec_2024.day_04;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

public class CeresSearch {

    private final PuzzleInputLoader puzzleInputLoader;

    public CeresSearch() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    private void solvePuzzle1() {
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearchTest.txt");
        // do a star algorithm instead. find an x and walk in every direction
        puzzleContents.showAsGrid();
        String[][] puzzleGrid = puzzleContents.getPuzzleGrid();
        // TODO Goudemond 2024/12/04 | do logic to walk through all compas directions of the grid
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        CeresSearch ceresSearch = new CeresSearch();
        ceresSearch.solvePuzzle1();
    }
}
