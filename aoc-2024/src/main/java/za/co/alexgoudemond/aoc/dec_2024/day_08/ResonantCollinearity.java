package za.co.alexgoudemond.aoc.dec_2024.day_08;

import aoc.dec_2024.helper.*;
import za.co.alexgoudemond.aoc.dec_2024.helper.*;
import za.co.alexgoudemond.dec_2024.helper.*;

import java.util.*;

public class ResonantCollinearity {

    private final PuzzleGrid puzzleGrid;

    public ResonantCollinearity() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearityTest.txt");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearityTest002.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_08/ResonantCollinearity.txt");
        this.puzzleGrid = puzzleContents.getPuzzleGrid();
    }

    private int solvePuzzle1() {
        Map<String, List<Coordinate>> uniqueElementsMap = puzzleGrid.getUniqueElements();
//        uniqueElementsMap.forEach((key, value) -> {
//            System.out.println(key + ": " + value);
//        });
        Set<Coordinate> antiNodes = new HashSet<>();
        for (String element : uniqueElementsMap.keySet()) {
            addAntiNodes(element, antiNodes);
        }
        System.out.println("antiNodes = " + antiNodes);
        return antiNodes.size(); // 216 --> too low ; 240 --> correct
    }

    private int solvePuzzle2() {
        Map<String, List<Coordinate>> uniqueElementsMap = puzzleGrid.getUniqueElements();
        Set<Coordinate> antiNodes = new HashSet<>();
        for (String element : uniqueElementsMap.keySet()) {
            if (element.equals(".")) {
                continue;
            }
            List<Coordinate> antennas = puzzleGrid.getCoordinatesFor(element);
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Coordinate antennaOne = antennas.get(i);
                    Coordinate antennaTwo = antennas.get(j);
                    antiNodes.addAll(addAllAntiNodesInLine(antennaTwo, antennaOne));
                    antiNodes.addAll(addAllAntiNodesInLine(antennaOne, antennaTwo));
                }
            }
        }
        return antiNodes.size(); // 931 --> too low ; 955 --> correct! (did have help from reddit)
    }

    private List<Coordinate> addAllAntiNodesInLine(Coordinate antennaTwo, Coordinate antennaOne) {
        List<Coordinate> antiNodes = new ArrayList<>();
        Coordinate newAntinode;
        int newXValue = antennaTwo.getX() + (antennaTwo.getX() - antennaOne.getX());
        int newYValue = antennaTwo.getY() + (antennaTwo.getY() - antennaOne.getY());
        newAntinode = new Coordinate(newXValue, newYValue);
        antiNodes.add(antennaTwo);
        while (!puzzleGrid.outsideGrid(newAntinode)) {
            antiNodes.add(newAntinode);
            newXValue += (antennaTwo.getX() - antennaOne.getX());
            newYValue += (antennaTwo.getY() - antennaOne.getY());
            newAntinode = new Coordinate(newXValue, newYValue);
        }
        return antiNodes;
    }

    private void addAntiNodes(String element, Set<Coordinate> antiNodes) {
        Coordinate antiNode = Coordinate.dummyCoordinate();
//        System.out.println("puzzleGrid.getCoordinatesFor(" + element + ") = " + puzzleGrid.getCoordinatesFor(element));
        if (element.equals(".")) {
            return;
        }
        List<Coordinate> antennas = puzzleGrid.getCoordinatesFor(element);
        if (antennas.size() == 1) {
            return;
        }
        for (int i = 0; i < antennas.size(); i++) {
            for (int j = i + 1; j < antennas.size(); j++) {
                Coordinate antennaOne = antennas.get(i);
                Coordinate antennaTwo = antennas.get(j);
                antiNode = getAntinode(antennaOne, antennaTwo);
                if (!antiNode.equals(Coordinate.dummyCoordinate())) {
                    antiNodes.add(antiNode);
                }
                antiNode = getAntinode(antennaTwo, antennaOne);
                if (!antiNode.equals(Coordinate.dummyCoordinate())) {
                    antiNodes.add(antiNode);
                }
            }
        }
    }

    private Coordinate getAntinode(Coordinate antennaOne, Coordinate antennaTwo) {
        int newXValue = antennaTwo.getX() + (antennaTwo.getX() - antennaOne.getX());
        int newYValue = antennaTwo.getY() + (antennaTwo.getY() - antennaOne.getY());
        Coordinate newAntinode = new Coordinate(newXValue, newYValue);
        if (puzzleGrid.outsideGrid(newAntinode)) {
            return Coordinate.dummyCoordinate();
        }
        return newAntinode;
    }

    private boolean correctDistanceForAntiNode(Coordinate antennaOne, Coordinate antennaTwo, Coordinate antennaNeighbour) {
//        int singleHorizontalDistance = Math.abs(antennaOne.getX() - antennaTwo.getX());
//        int singleVerticalDistance = Math.abs(antennaOne.getY() - antennaTwo.getY());
//        if (Math.abs(antennaNeighbour.getX() - antennaOne.getX()) == singleHorizontalDistance
//                && Math.abs(antennaNeighbour.getX() - antennaTwo.getX()) == 2 * singleHorizontalDistance
//                && Math.abs(antennaNeighbour.getY() - antennaOne.getY()) == singleVerticalDistance
//                && Math.abs(antennaNeighbour.getY() - antennaTwo.getY()) == 2 * singleVerticalDistance) {
//            return true;
//        }
//        if (Math.abs(antennaNeighbour.getX() - antennaOne.getX()) == 2 * singleHorizontalDistance
//                && Math.abs(antennaNeighbour.getX() - antennaTwo.getX()) == singleHorizontalDistance
//                && Math.abs(antennaNeighbour.getY() - antennaOne.getY()) == 2 * singleVerticalDistance
//                && Math.abs(antennaNeighbour.getY() - antennaTwo.getY()) == singleVerticalDistance) {
//            return true;
//        }
        double distance = distance(antennaOne, antennaTwo);
        double distanceToCoordinate1 = distance(antennaNeighbour, antennaOne);
        double distanceToCoordinate2 = distance(antennaNeighbour, antennaTwo);
        if (distanceToCoordinate2 == distance && distanceToCoordinate1 == 2 * distance) {
            return true;
        }
        if (distanceToCoordinate2 == 2 * distance && distanceToCoordinate1 == distance) {
            return true;
        }
        return false;
    }

    private double distance(Coordinate antennaOne, Coordinate antennaTwo) {
        return Math.sqrt(Math.pow(antennaOne.getX() - antennaTwo.getX(), 2) + Math.pow(antennaOne.getY() - antennaTwo.getY(), 2));
    }

    public static void main(String[] args) {
        ResonantCollinearity resonantCollinear = new ResonantCollinearity();
        int puzzle1Solution = resonantCollinear.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        int puzzle2Solution = resonantCollinear.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }

}
