package za.co.alexgoudemond.aoc.dec_2024.day_04;

import za.co.alexgoudemond.aoc.dec_2024.helper.*;

import java.util.List;

public class CeresSearch {

    private final String finalLetterToFind;

    private final PuzzleGrid puzzleGrid;

    private final String puzzle1WordToFind;

    private final String puzzle2WordToFind;

    public CeresSearch() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearchTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_04/CeresSearch001.txt");
        puzzleGrid = puzzleContents.getPuzzleGrid();
        puzzle1WordToFind = "XMAS";
        puzzle2WordToFind = "MAS";
        finalLetterToFind = puzzle1WordToFind.substring(puzzle1WordToFind.length() - 1);
    }

    private int solvePuzzle1() {
//        puzzleGrid.showAsGrid();
        List<Coordinate> xLocations = puzzleGrid.getCoordinatesFor("X");
//        xLocations.forEach(System.out::println);
        int wordCount = 0;
        for (Coordinate location : xLocations) {
            wordCount += leftToRight(location.right(), puzzle1NextLetterToFind(location));     // 3
            wordCount += rightToLeft(location.left(), puzzle1NextLetterToFind(location));     // 2
            wordCount += topToBottom(location.down(), puzzle1NextLetterToFind(location));     // 1
            wordCount += bottomToTop(location.up(), puzzle1NextLetterToFind(location));     // 2
            wordCount += diagonalLeftUp(location.up().left(), puzzle1NextLetterToFind(location));     // 4
            wordCount += diagonalRightUp(location.up().right(), puzzle1NextLetterToFind(location));     // 4
            wordCount += diagonalRightDown(location.down().right(), puzzle1NextLetterToFind(location));     // 1
            wordCount += diagonalLeftDown(location.down().left(), puzzle1NextLetterToFind(location));     // 1
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

    private int solvePuzzle2() {
        List<Coordinate> aLocations = puzzleGrid.getCoordinatesFor("A");
        int wordCount = 0;
        boolean leftDiagonalValid;
        boolean rightDiagonalValid;
        for (Coordinate location : aLocations) {
            leftDiagonalValid = false;
            rightDiagonalValid = false;
            if (
                    (coordinateHasLetter(location.up().left(), 'M') && coordinateHasLetter(location.down().right(), 'S'))
                            || (coordinateHasLetter(location.up().left(), 'S') && coordinateHasLetter(location.down().right(), 'M'))
            ) {
                leftDiagonalValid = true;
            }
            if (
                    (coordinateHasLetter(location.up().right(), 'M') && coordinateHasLetter(location.down().left(), 'S'))
                            || (coordinateHasLetter(location.up().right(), 'S') && coordinateHasLetter(location.down().left(), 'M'))
            ) {
                rightDiagonalValid = true;
            }
            if (leftDiagonalValid && rightDiagonalValid) {
                wordCount++;
            }
        }
        return wordCount;
    } // 1807 --> correct!

    private boolean coordinateHasLetter(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord) || puzzleGrid.tooShort(coord)
                || puzzleGrid.tooNarrow(coord) || puzzleGrid.tooWide(coord)) {
            return false;
        }
        return puzzleGrid.correspondingLetter(coord).equals(Character.toString(letterToCheck));
    }

    private int diagonalLeftDown(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord) || puzzleGrid.tooNarrow(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalLeftDown(coord.down().left(), puzzle1NextLetterToFind(coord));
    }

    private int diagonalRightDown(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord) || puzzleGrid.tooWide(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalRightDown(coord.down().right(), puzzle1NextLetterToFind(coord));
    }

    private int diagonalRightUp(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooShort(coord) || puzzleGrid.tooWide(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalRightUp(coord.up().right(), puzzle1NextLetterToFind(coord));
    }

    private int diagonalLeftUp(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooShort(coord) || puzzleGrid.tooNarrow(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return diagonalLeftUp(coord.up().left(), puzzle1NextLetterToFind(coord));
    }

    private int bottomToTop(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooShort(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return bottomToTop(coord.up(), puzzle1NextLetterToFind(coord));
    }

    private int topToBottom(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooTall(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return topToBottom(coord.down(), puzzle1NextLetterToFind(coord));
    }

    private int rightToLeft(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooNarrow(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return rightToLeft(coord.left(), puzzle1NextLetterToFind(coord));
    }

    private int leftToRight(Coordinate coord, char letterToCheck) {
        if (puzzleGrid.tooWide(coord)) {
            return 0;
        }
        int letterFound = terminalConditionForLetterToFind(coord, letterToCheck);
        if (letterFound != -1) {
            return letterFound;
        }
        return leftToRight(coord.right(), puzzle1NextLetterToFind(coord));
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

    private char puzzle1NextLetterToFind(Coordinate location) {
        return puzzle1WordToFind.charAt(getNextLetterToFindIndex(location));
    }

    private int getNextLetterToFindIndex(Coordinate location) {
        String letter = puzzleGrid.correspondingLetter(location);
        int nextLetterToFindIndex = puzzle1WordToFind.indexOf(letter);
        return nextLetterToFindIndex == -1 ? -1 : nextLetterToFindIndex + 1;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        CeresSearch ceresSearch = new CeresSearch();
        int puzzle1Solution = ceresSearch.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        int puzzle2Solution = ceresSearch.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
        // TODO Goudemond 2024/12/05 | Consider abstracting the maze as a Class
    }
}
