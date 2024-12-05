package aoc.dec_2024.day_04;

import aoc.dec_2024.helper.Coordinate;
import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.ArrayList;
import java.util.List;

public class CeresSearch {

    private final PuzzleContents puzzleContents;

    private final String finalLetterToFind;

    // TODO Goudemond 2024/12/05 | Model as own class
    private final String[][] puzzleGrid;

    private final String wordToFind;

    private final int maxWidth;

    private final int maxHeight;

    public CeresSearch() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
        puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearchTest.txt");
        puzzleGrid = puzzleContents.getPuzzleGrid();
        wordToFind = "XMAS";
        finalLetterToFind = wordToFind.substring(wordToFind.length() - 1);
        maxWidth = puzzleGrid[0].length;
        maxHeight = puzzleGrid.length;
    }

    private void solvePuzzle1() {
        // do a star algorithm instead. find an x and walk in every direction
        puzzleContents.showAsGrid();
        // TODO Goudemond 2024/12/04 | do logic to walk through all compass directions of the grid
        List<Coordinate> xLocations = getCoordinatesForX();
//        xLocations.forEach(System.out::println);
        int wordCount = 0;
        for (Coordinate location : xLocations) {
            // start small, just left to right
//            wordCount += leftToRight(location.right(), nextLetterToFind(location));
            System.out.print(location);
            if (leftToRight(location.right(), nextLetterToFind(location)) == 1){
                System.out.println(" has xmas");
            }else{
                System.out.println();
            }
//            break;
        }
    }

    private char nextLetterToFind(Coordinate location) {
        return wordToFind.charAt(getNextLetterToFindIndex(location));
    }

    private int getNextLetterToFindIndex(Coordinate location) {
        String letter = correspondingLetter(location);
        int nextLetterToFindIndex = wordToFind.indexOf(letter);
        return nextLetterToFindIndex == -1 ? -1 : nextLetterToFindIndex + 1;
    }

    private String correspondingLetter(Coordinate location) {
        return puzzleGrid[location.getX()][location.getY()];
    }

    private int leftToRight(Coordinate coord, char letterToCheck) {
        if (coord.getY() >= maxWidth || coord.right().getY() >= maxWidth) {
            return 0;
        }
        String correspondingLetter = correspondingLetter(coord);
        if (!correspondingLetter.equals(Character.toString(letterToCheck))) {
            return 0;
        }
        if (correspondingLetter.equals(finalLetterToFind)) {
            return 1;
        }
        return leftToRight(coord.right(), nextLetterToFind(coord));
    }

    private List<Coordinate> getCoordinatesForX() {
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
