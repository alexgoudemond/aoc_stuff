package aoc.dec_2024.day_04;

import aoc.dec_2024.helper.Coordinate;
import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.ArrayList;
import java.util.List;

public class CeresSearch {

    private final PuzzleInputLoader puzzleInputLoader;

    public CeresSearch() {
        puzzleInputLoader = new PuzzleInputLoaderImpl("");
    }

    private void solvePuzzle1() {
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearchTest.txt");
        // do a star algorithm instead. find an x and walk in every direction
        puzzleContents.showAsGrid();
        List<String> rawPuzzleContent = puzzleContents.getRawPuzzleContent();
        String[][] puzzleGrid = puzzleContents.getPuzzleGrid();
        // TODO Goudemond 2024/12/04 | do logic to walk through all compass directions of the grid
        List<Coordinate> xLocations = getCoordinatesForX(puzzleGrid);
        xLocations.forEach(System.out::println);
    }

    private List<Coordinate> getCoordinatesForX(String[][] puzzleGrid) {
        List<Coordinate> xLocations = new ArrayList<>();
        for (int i = 0; i < puzzleGrid.length; i++) {
            for (int j = 0; j < puzzleGrid[i].length; j++) {
                if (puzzleGrid[i][j].equals("X")) {
                    xLocations.add(new Coordinate(i, j));
                }
            }
        }
        return xLocations;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        CeresSearch ceresSearch = new CeresSearch();
        ceresSearch.solvePuzzle1();
    }
}
