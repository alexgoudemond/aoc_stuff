package za.co.alexgoudemond.aoc.dec_2024.day_10;

import aoc.dec_2024.helper.*;
import za.co.alexgoudemond.aoc.dec_2024.helper.Coordinate;
import za.co.alexgoudemond.aoc.dec_2024.helper.PuzzleContents;
import za.co.alexgoudemond.aoc.dec_2024.helper.PuzzleGrid;
import za.co.alexgoudemond.aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.*;

public class HoofIt {

    private final PuzzleContents puzzleContents;

    private final PuzzleGrid puzzleGrid;

    public HoofIt() {
        PuzzleInputLoaderImpl puzzleInputLoader = new PuzzleInputLoaderImpl("");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt001.txt");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt002.txt");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt003.txt");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt004.txt");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt005.txt");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt.txt");
//        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt006.txt");
        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt007.txt");
        this.puzzleGrid = puzzleContents.getPuzzleGrid();
    }

    // TODO Goudemond 2024/12/26 | Why supposed to get 13 here?
    private int solvePuzzle2() {
        //        puzzleContents.showAsGrid();
        List<Coordinate> startingCoordinates = getStartingCoordinates();
//        System.out.println("Starting coordinates: " + startingCoordinates);
        int score = 0;
        for (Coordinate trailHead : startingCoordinates) {
//            System.out.println("info for trailhead: " + trailHead);
            List<Coordinate> endPositions = new ArrayList<>();
            Set<Coordinate> visitedCoordinates = new HashSet<>();
            Stack<Coordinate> stack = new Stack<>();
            Coordinate currentCoordinate = Coordinate.dummyCoordinate();
            stack.push(trailHead);
            int rating = 0;
            // each compass position, if not visited - add to stack. then add currentPosition to visited
            while (!stack.isEmpty()) {
                currentCoordinate = stack.pop();
                visitedCoordinates.add(currentCoordinate);
                if (puzzleGrid.elementAt(currentCoordinate).equals("9")) {
//                    endPositions.add(currentCoordinate);
                    rating++;
                    continue;
                }
                if (!puzzleGrid.outsideGrid(currentCoordinate.up()) && validSlope(currentCoordinate, currentCoordinate.up())) {
                    if (visitedCoordinates.contains(currentCoordinate.up())) {
                        rating++;
                    } else {
                        stack.push(currentCoordinate.up());
                    }
                }
                if (!puzzleGrid.outsideGrid(currentCoordinate.right()) && validSlope(currentCoordinate, currentCoordinate.right())) {
                    if (visitedCoordinates.contains(currentCoordinate.right())) {
                        rating++;
                    } else {
                        stack.push(currentCoordinate.right());
                    }
                }
                if (!puzzleGrid.outsideGrid(currentCoordinate.down()) && validSlope(currentCoordinate, currentCoordinate.down())) {
                    if (visitedCoordinates.contains(currentCoordinate.down())) {
                        rating++;
                    } else {
                        stack.push(currentCoordinate.down());
                    }
                }
                if (!puzzleGrid.outsideGrid(currentCoordinate.left()) && validSlope(currentCoordinate, currentCoordinate.left())) {
                    if (visitedCoordinates.contains(currentCoordinate.left())) {
                        rating++;
                    } else {
                        stack.push(currentCoordinate.left());
                    }
                }
            }
            //            endPositions.forEach(System.out::println);
            System.out.println("rating = " + rating);
        }
        return -1;
    }

    private int solvePuzzle1() {
//        puzzleContents.showAsGrid();
        List<Coordinate> startingCoordinates = getStartingCoordinates();
//        System.out.println("Starting coordinates: " + startingCoordinates);
        int score = 0;
        for (Coordinate trailHead : startingCoordinates) {
//            System.out.println("info for trailhead: " + trailHead);
            List<Coordinate> endPositions = getEndPositions(trailHead);
//            endPositions.forEach(System.out::println);
            score += endPositions.size();
        }
        return score; // 796 --> correct!
    }

    private List<Coordinate> getEndPositions(Coordinate trailHead) {
        List<Coordinate> endPositions = new ArrayList<>();
        Set<Coordinate> visitedCoordinates = new HashSet<>();
        Stack<Coordinate> stack = new Stack<>();
        Coordinate currentCoordinate = Coordinate.dummyCoordinate();
        stack.push(trailHead);
        // each compass position, if not visited - add to stack. then add currentPosition to visited
        while (!stack.isEmpty()) {
            currentCoordinate = stack.pop();
            visitedCoordinates.add(currentCoordinate);
            if (puzzleGrid.elementAt(currentCoordinate).equals("9")) {
                endPositions.add(currentCoordinate);
                continue;
            }
            if (adjacentPositionIsValid(visitedCoordinates, currentCoordinate, currentCoordinate.up())) {
                stack.push(currentCoordinate.up());
            }
            if (adjacentPositionIsValid(visitedCoordinates, currentCoordinate, currentCoordinate.right())) {
                stack.push(currentCoordinate.right());
            }
            if (adjacentPositionIsValid(visitedCoordinates, currentCoordinate, currentCoordinate.down())) {
                stack.push(currentCoordinate.down());
            }
            if (adjacentPositionIsValid(visitedCoordinates, currentCoordinate, currentCoordinate.left())) {
                stack.push(currentCoordinate.left());
            }
        }
        return endPositions;
    }

    private boolean adjacentPositionIsValid(Set<Coordinate> visitedCoordinates, Coordinate currentCoordinate, Coordinate nextCoordinate) {
        return !visitedCoordinates.contains(nextCoordinate)
                && !puzzleGrid.outsideGrid(nextCoordinate)
                && validSlope(currentCoordinate, nextCoordinate);
    }

    private boolean validSlope(Coordinate currentPosition, Coordinate nextPosition) {
        String currentElement = puzzleGrid.elementAt(currentPosition);
        String nextElement = puzzleGrid.elementAt(nextPosition);
        if (currentElement.equals(".") || nextElement.equals(".")) {
            return false;
        }
        int currentHeight = Integer.parseInt(currentElement);
        int adjacentHeight = Integer.parseInt(nextElement);
        return adjacentHeight == currentHeight + 1;
    }

    private List<Coordinate> getStartingCoordinates() {
        List<Coordinate> startingCoordinates = new ArrayList<>();
        for (int i = 0; i < puzzleGrid.numRows(); i++) {
            for (int j = 0; j < puzzleGrid.numColumns(); j++) {
                if (puzzleGrid.elementAt(i, j).equals("0")) {
                    startingCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        return startingCoordinates;
    }

    public static void main(String[] args) {
        HoofIt hoofIt = new HoofIt();
        int puzzle1Solution = hoofIt.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        int puzzle2Solution = hoofIt.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }
}
