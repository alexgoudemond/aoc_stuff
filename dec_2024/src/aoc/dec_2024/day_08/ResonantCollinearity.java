package aoc.dec_2024.day_08;

import aoc.dec_2024.helper.*;

import java.util.*;

public class ResonantCollinearity {

    private final PuzzleGrid puzzleGrid;

    public ResonantCollinearity() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearityTest.txt");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearity.txt");
        this.puzzleGrid = puzzleContents.getPuzzleGrid();
    }

    // TODO Goudemond 2024/12/10 | Bresenham Line Algorithm? Or super simple?
    private int solvePuzzle1() {
        System.out.println("Hello World!");
        Map<String, List<Coordinate>> uniqueElementsMap = puzzleGrid.getUniqueElements();
//        uniqueElementsMap.forEach((key, value) -> {
//            System.out.println(key + ": " + value);
//        });
        Set<Coordinate> antiNodes = new HashSet<>();
        for (String element : uniqueElementsMap.keySet()) {
//            System.out.println("puzzleGrid.getCoordinatesFor(" + element + ") = " + puzzleGrid.getCoordinatesFor(element));
            if (element.equals(".")) {
                continue;
            }
            List<Coordinate> antennas = puzzleGrid.getCoordinatesFor(element);
            if (antennas.size() == 1) {
                continue;
            }
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Coordinate antennaOne = antennas.get(i);
                    Coordinate antennaTwo = antennas.get(j);
//                    System.out.println("antennaOne = " + antennaOne);
//                    System.out.println("antennaTwo = " + antennaTwo);
                    List<Coordinate> straightLineCoordinates = puzzleGrid.getCoordinatesThatLiePerfectlyInTheStraightLine(antennaOne, antennaTwo);
//                    System.out.println("straightLineCoordinates = " + straightLineCoordinates);
                    for (Coordinate antennaNeighbour : straightLineCoordinates) {
                        if (antennaNeighbour.equals(antennaOne) || antennaNeighbour.equals(antennaTwo)) {
                            continue;
                        }
                        if (correctDistanceForAntiNode(antennaOne, antennaTwo, antennaNeighbour)) {
                            antiNodes.add(antennaNeighbour);
                        }
                    }
                }
//                System.out.println("antiNodes = " + antiNodes);
//                return;
            }
        }
        System.out.println("antiNodes = " + antiNodes);
        return antiNodes.size(); // 216 --> too low
    }

    private boolean correctDistanceForAntiNode(Coordinate antennaOne, Coordinate antennaTwo, Coordinate antennaNeighbour) {
        int singleHorizontalDistance = Math.abs(antennaOne.getX() - antennaTwo.getX());
//        int singleVerticalDistance = Math.abs(antennaOne.getY() - antennaTwo.getY());
        if (Math.abs(antennaNeighbour.getX() - antennaOne.getX()) == singleHorizontalDistance
                && Math.abs(antennaNeighbour.getX() - antennaTwo.getX()) == 2 * singleHorizontalDistance) {
            return true;
        }
        if (Math.abs(antennaNeighbour.getX() - antennaOne.getX()) == 2 * singleHorizontalDistance
                && Math.abs(antennaNeighbour.getX() - antennaTwo.getX()) == singleHorizontalDistance) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ResonantCollinearity resonantCollinear = new ResonantCollinearity();
        int puzzle1Solution = resonantCollinear.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
    }

}
