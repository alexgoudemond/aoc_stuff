package aoc.dec_2024.day_06;

import aoc.dec_2024.helper.*;

import java.util.HashSet;
import java.util.Set;

public class GuardGallivant {

    public static final String GUARD_DIRECTIONS = "^>V<";

    public static final String OBSTACLES = "#O";

    public static final String VISITED = "X";

    private final PuzzleGrid puzzleGrid;

//    private String[][] labMap;

    public GuardGallivant() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_06/GuardGallivantTest.txt");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_06/GuardGallivant001.txt");
        this.puzzleGrid = puzzleContents.getPuzzleGrid();
//        this.labMap = puzzleContents.getPuzzleGrid().getGrid();
    }

    private int solvePuzzle1() {
//        puzzleGrid.showAsGrid();
        simulateGuardPatrolInLab();
//        puzzleGrid.showAsGrid();
        Set<Coordinate> guardVisitsOnMap = getGuardVisitsOnMap();
        // (Goudemond 20241206) Don't forget to add the final position, marked with 'V'!
        return guardVisitsOnMap.size() + 1; // 5239 --> Correct!
    }

    private Set<Coordinate> getGuardVisitsOnMap() {
        Set<Coordinate> guardVisitsOnMap = new HashSet<>();
        for (int i = 0; i < puzzleGrid.numRows(); i++) {
            for (int j = 0; j < puzzleGrid.numColumns(); j++) {
                if (puzzleGrid.elementAt(i, j).equals(VISITED)) {
                    guardVisitsOnMap.add(new Coordinate(i, j));
                }
            }
        }
        return guardVisitsOnMap;
    }

    // TODO Goudemond 2024/12/06 | Note all coordinates visited inline with a boundary
    // TODO Goudemond 2024/12/06 | check all those
    private int solvePuzzle2() {
        puzzleGrid.resetGrid();
        simulateGuardPatrolInLab();
        Set<Coordinate> guardVisitsOnMap = getGuardVisitsOnMap();
        puzzleGrid.resetGrid();
        int obstructionPositions = 0;

//        Coordinate positionToCheck = new Coordinate(6, 3); // works
//        Coordinate positionToCheck = new Coordinate(7, 6); // works
//        Coordinate positionToCheck = new Coordinate(7, 7); // works
//        Coordinate positionToCheck = new Coordinate(8, 1); // works
//        Coordinate positionToCheck = new Coordinate(8, 3); // works
        Coordinate positionToCheck = new Coordinate(9, 7); // works
        // TODO Goudemond 2024/12/06 | put it all together
        String originalElement = puzzleGrid.elementAt(positionToCheck);
        puzzleGrid.setElementTo(positionToCheck, "O");
        puzzleGrid.showAsGrid();
        if (guardTrappedInInfinitePatrol()) {
            obstructionPositions++;
            System.out.println("trapped");
        }
        puzzleGrid.setElementTo(positionToCheck, originalElement);

        return -1;
    }

    private boolean guardTrappedInInfinitePatrol() {
        Coordinate guardStartingPosition = getGuardStartingPosition();
        Coordinate previousPosition = guardStartingPosition;
        Coordinate nextPosition = guardStartingPosition;
        while (true) {
            nextPosition = getNextPosition(nextPosition);
            if (nextPosition.equals(Coordinate.dummyCoordinate())) {
                return false;
            }
            if (nextPosition.equals(guardStartingPosition)) {
                return true;
            }
            String guardDirection = puzzleGrid.elementAt(previousPosition.getX(), previousPosition.getY());
            markPositionOnLabMap(previousPosition, VISITED);
            markPositionOnLabMap(nextPosition, guardDirection);
////            System.out.println("nextPosition = " + nextPosition);
            previousPosition = nextPosition;
        }
    }

    private void simulateGuardPatrolInLab() {
        Coordinate guardStartingPosition = getGuardStartingPosition();
//        System.out.println("guardStartingPosition = " + guardStartingPosition);
        Coordinate previousPosition = guardStartingPosition;
        Coordinate nextPosition = guardStartingPosition;
        while (true) {
            nextPosition = getNextPosition(nextPosition);
            if (nextPosition.equals(Coordinate.dummyCoordinate())) {
                break;
            }
            String guardDirection = puzzleGrid.elementAt(previousPosition.getX(), previousPosition.getY());
            markPositionOnLabMap(previousPosition, VISITED);
            markPositionOnLabMap(nextPosition, guardDirection);
//            System.out.println("nextPosition = " + nextPosition);
            previousPosition = nextPosition;
        }
    }

    private void markPositionOnLabMap(Coordinate position, String symbol) {
        puzzleGrid.setElementTo(position, symbol);
    }

    private Coordinate getNextPosition(Coordinate guardPosition) {
        Coordinate nextPosition = guardPosition;
        Coordinate currentPosition = guardPosition;
        int facingDirectionIndex = GUARD_DIRECTIONS.indexOf(puzzleGrid.elementAt(guardPosition));
        nextPosition = getNextPositionConsidering(currentPosition, facingDirectionIndex);
        // TODO Goudemond 2024/12/06 | handle boundaries
        if (puzzleGrid.tooNarrow(nextPosition) || puzzleGrid.tooWide(nextPosition) ||
                puzzleGrid.tooShort(nextPosition) || puzzleGrid.tooTall(nextPosition)) {
            return Coordinate.dummyCoordinate();
        }
        while (OBSTACLES.contains(puzzleGrid.elementAt(nextPosition.getX(), nextPosition.getY()))) {
            nextPosition = getNextPositionConsidering(currentPosition, ++facingDirectionIndex);
        }
        updateGuardDirection(facingDirectionIndex, currentPosition);
        return nextPosition;
    }

    private void updateGuardDirection(int facingDirectionIndex, Coordinate currentPosition) {
        int index = facingDirectionIndex >= GUARD_DIRECTIONS.length() ? 0 : facingDirectionIndex;
        String string = Character.toString(GUARD_DIRECTIONS.charAt(index));
        puzzleGrid.setElementTo(currentPosition, string);
    }

    private Coordinate getNextPositionConsidering(Coordinate position, int facingDirectionIndex) {
        facingDirectionIndex = facingDirectionIndex % GUARD_DIRECTIONS.length();
        switch (facingDirectionIndex) {
            case 0:
                position = position.up();
                break;
            case 1:
                position = position.right();
                break;
            case 2:
                position = position.down();
                break;
            case 3:
                position = position.left();
                break;
            default:
                throw new RuntimeException("Invalid facing direction!");
        }
        return position;
    }

    private Coordinate getGuardStartingPosition() {
        for (int i = 0; i < puzzleGrid.getGrid().length; i++) {
            for (int j = 0; j < puzzleGrid.getGrid()[i].length; j++) {
                if (GUARD_DIRECTIONS.contains(puzzleGrid.elementAt(i, j))) {
                    return new Coordinate(i, j);
                }
            }
        }
        throw new RuntimeException("Guard not found in puzzle input!");
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        GuardGallivant guardGallivant = new GuardGallivant();
        int puzzle1Solution = guardGallivant.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        int puzzle2Solution = guardGallivant.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }

}
