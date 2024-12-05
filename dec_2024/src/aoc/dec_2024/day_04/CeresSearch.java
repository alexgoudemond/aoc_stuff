package aoc.dec_2024.day_04;

import aoc.dec_2024.helper.*;

import java.util.List;

public class CeresSearch {

    private final String finalLetterToFind;

    private final PuzzleGrid puzzleGrid;

    private final String wordToFind;

    public CeresSearch() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearchTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearch001.txt");
        puzzleGrid = puzzleContents.getPuzzleGrid();
        wordToFind = "XMAS";
        finalLetterToFind = wordToFind.substring(wordToFind.length() - 1);
    }

    private int solvePuzzle1() {
//        puzzleContents.showAsGrid();
        List<Coordinate> xLocations = puzzleGrid.getCoordinatesFor("X");
//        xLocations.forEach(System.out::println);
        int wordCount = 0;
        for (Coordinate location : xLocations) {
            wordCount += leftToRight(location.right(), nextLetterToFind(location));     // 3
            wordCount += rightToLeft(location.left(), nextLetterToFind(location));     // 2
            wordCount += topToBottom(location.down(), nextLetterToFind(location));     // 1
            wordCount += bottomToTop(location.up(), nextLetterToFind(location));     // 2
            wordCount += diagonalLeftUp(location.up().left(), nextLetterToFind(location));     // 4
            wordCount += diagonalRightUp(location.up().right(), nextLetterToFind(location));     // 4
            wordCount += diagonalRightDown(location.down().right(), nextLetterToFind(location));     // 1
            wordCount += diagonalLeftDown(location.down().left(), nextLetterToFind(location));     // 1
//            System.out.print(location);
//            if (leftToRight(location.right(), nextLetterToFind(location)) == 1){ // 3
//            if (rightToLeft(location.left(), nextLetterToFind(location)) == 1){ // 2
//            if (topToBottom(location.down(), nextLetterToFind(location)) == 1){ // 1
//            if (bottomToTop(location.up(), nextLetterToFind(location)) == 1){ // 2
//            if (diagonalLeftUp(location.up().left(), nextLetterToFind(location)) == 1){ // 4
//            if (diagonalRightUp(location.up().right(), nextLetterToFind(location)) == 1){ // 4
//            if (diagonalRightDown(location.down().right(), nextLetterToFind(location)) == 1){ // 1
//            if (diagonalLeftDown(location.down().left(), nextLetterToFind(location)) == 1){ // 1
//                System.out.println(" has xmas");
//            }else{
//                System.out.println();
//            }
        }
        return wordCount; //2406 --> correct!
    }

    private int diagonalLeftDown(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord) || puzzleGrid.tooNarrow(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalLeftDown(coord.down().left(), nextLetterToFind(coord));
    }

    private int diagonalRightDown(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord) || puzzleGrid.tooWide(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalRightDown(coord.down().right(), nextLetterToFind(coord));
    }

    private int diagonalRightUp(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooShort(coord) || puzzleGrid.tooWide(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalRightUp(coord.up().right(), nextLetterToFind(coord));
    }

    private int diagonalLeftUp(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooShort(coord) || puzzleGrid.tooNarrow(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalLeftUp(coord.up().left(), nextLetterToFind(coord));
    }

    private int bottomToTop(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooShort(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return bottomToTop(coord.up(), nextLetterToFind(coord));
    }

    private int topToBottom(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return topToBottom(coord.down(), nextLetterToFind(coord));
    }

    private int rightToLeft(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooNarrow(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return rightToLeft(coord.left(), nextLetterToFind(coord));
    }

    private int leftToRight(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooWide(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return leftToRight(coord.right(), nextLetterToFind(coord));
    }

    private int terminalConditionForLetterToFind(Coordinate coord, char letterToCheck) {
        String correspondingLetter = puzzleGrid.correspondingLetter(coord);
        if (!correspondingLetter.equals(Character.toString(letterToCheck))) {
            return 0;
        }
        if (correspondingLetter.equals(finalLetterToFind)) {
            return 1;
        }
        return -1;
    }

    private char nextLetterToFind(Coordinate location) {
        return wordToFind.charAt(getNextLetterToFindIndex(location));
    }

    private int getNextLetterToFindIndex(Coordinate location) {
        String letter = puzzleGrid.correspondingLetter(location);
        int nextLetterToFindIndex = wordToFind.indexOf(letter);
        return nextLetterToFindIndex == -1 ? -1 : nextLetterToFindIndex + 1;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        CeresSearch ceresSearch = new CeresSearch();
        int puzzle1Solution = ceresSearch.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        // TODO Goudemond 2024/12/05 | do Puzzle 2
        // TODO Goudemond 2024/12/05 | Consider abstracting the maze as a Class
    }
}
