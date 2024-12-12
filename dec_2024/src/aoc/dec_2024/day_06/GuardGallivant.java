package aoc.dec_2024.day_06;

import aoc.dec_2024.helper.*;

import java.util.HashSet;
import java.util.Set;

public class GuardGallivant {

    public static final String GUARD_DIRECTIONS = "^>V<";

    public static final String OBSTACLES = "#O";

    public static final String ADDED_OBSTACLE = "O";

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
                if (puzzleGrid.elementAt(i, j).equals(VISITED) || GUARD_DIRECTIONS.contains(puzzleGrid.elementAt(i, j))) {
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
        Coordinate guardStartingPosition = getGuardStartingPosition();
        simulateGuardPatrolInLab();
        Set<Coordinate> guardVisitsOnMap = getGuardVisitsOnMap();
        int obstructionPositions = 0;
        for (Coordinate positionToCheck : guardVisitsOnMap) {
            puzzleGrid.resetGrid();
            if (positionToCheck.equals(guardStartingPosition)) {
                continue;
            }
            String originalElement = puzzleGrid.elementAt(positionToCheck);
            puzzleGrid.setElementTo(positionToCheck, "O");
            if (guardTrappedInInfinitePatrol()) {
                obstructionPositions++;
            }
            puzzleGrid.setElementTo(positionToCheck, originalElement);
        }

        return obstructionPositions;
    }

    // TODO Goudemond 2024/12/07 | Infinite Loop with coord 3, 69! not getting this
    // TODO Goudemond 2024/12/12 | tried again - no luck and im tired
    private boolean guardTrappedInInfinitePatrol() {
        Coordinate guardStartingPosition = getGuardStartingPosition();
        int facingDirectionIndex = GUARD_DIRECTIONS.indexOf(puzzleGrid.elementAt(guardStartingPosition));
        Coordinate nextPosition = getNextPositionConsidering(guardStartingPosition, facingDirectionIndex);
        Coordinate previousPosition = nextPosition;
        Coordinate obstacleFacingPosition = Coordinate.dummyCoordinate();
        while (true) {
            if (puzzleGrid.outsideGrid(nextPosition)) {
                return false;
            }
            if (puzzleGrid.elementAt(nextPosition).equals("0") && !obstacleFacingPosition.equals(Coordinate.dummyCoordinate())) {
                obstacleFacingPosition = nextPosition;
//                nextPosition = getNextPositionConsidering(guardStartingPosition, ++facingDirectionIndex);
            } else if (puzzleGrid.elementAt(nextPosition).equals("0") && nextPosition.equals(obstacleFacingPosition)) {
                return true;
            } else if (puzzleGrid.elementAt(nextPosition).equals(".")) {
//                nextPosition = getNextPositionConsidering(guardStartingPosition, ++facingDirectionIndex);
//            } else {
                markPositionOnLabMap(previousPosition, VISITED);
                String guardDirection = puzzleGrid.elementAt(previousPosition.getX(), previousPosition.getY());
                markPositionOnLabMap(nextPosition, guardDirection);
            }
            previousPosition = nextPosition;
            nextPosition = getNextPositionConsidering(guardStartingPosition, ++facingDirectionIndex);
//        while (true) {
//            nextPosition = getNextPosition(nextPosition);
//            if (nextPosition.equals(Coordinate.dummyCoordinate())) {
//                return false;
//            }
//            if (!noteworthyCoord.equals(Coordinate.dummyCoordinate()) && noteworthyCoord.equals(nextPosition)) {
//                if (guardJourneySnapshot.equals(guardJourney)) {
//                    return true;
//                }
//                guardJourneySnapshot = guardJourney;
//                guardJourney = "";
//            }
//
//            guardJourney += nextPosition.toString();
//            String guardDirection = puzzleGrid.elementAt(previousPosition.getX(), previousPosition.getY());
//            markPositionOnLabMap(previousPosition, VISITED);
//            markPositionOnLabMap(nextPosition, guardDirection);
//////            System.out.println("nextPosition = " + nextPosition);
//            previousPosition = nextPosition;
//            if (firstTime && nextFacingPositionIsObstacle(nextPosition)) {
//                firstTime = false;
//                guardJourneySnapshot = guardJourney;
//                guardJourney = "";
//                noteworthyCoord = nextPosition;
//            }
//        }
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean nextFacingPositionIsObstacle(Coordinate position) {
        int facingDirectionIndex = GUARD_DIRECTIONS.indexOf(puzzleGrid.elementAt(position));
        Coordinate nextPosition = position;
        switch (facingDirectionIndex) {
            case 0:
                nextPosition = nextPosition.up();
                break;
            case 1:
                nextPosition = nextPosition.right();
                break;
            case 2:
                nextPosition = nextPosition.down();
                break;
            case 3:
                nextPosition = nextPosition.left();
                break;
            default:
                throw new RuntimeException("Invalid facing direction in adjacentPositionIsObstacle!");
        }
        if (!puzzleGrid.outsideGrid(nextPosition) && puzzleGrid.elementAt(nextPosition).equals(ADDED_OBSTACLE)) {
            return true;
        }
        return false;
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
        int facingDirectionIndex = GUARD_DIRECTIONS.indexOf(puzzleGrid.elementAt(guardPosition));
        nextPosition = getNextPositionConsidering(guardPosition, facingDirectionIndex);
        // TODO Goudemond 2024/12/06 | handle boundaries
        if (puzzleGrid.outsideGrid(nextPosition)) {
            return Coordinate.dummyCoordinate();
        }
        while (OBSTACLES.contains(puzzleGrid.elementAt(nextPosition.getX(), nextPosition.getY()))) {
            nextPosition = getNextPositionConsidering(guardPosition, ++facingDirectionIndex);
        }
        updateGuardDirection(facingDirectionIndex, guardPosition);
        return nextPosition;
    }

    private void updateGuardDirection(int facingDirectionIndex, Coordinate currentPosition) {
        int index = facingDirectionIndex % GUARD_DIRECTIONS.length();
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
