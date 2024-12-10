package aoc.dec_2024.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PuzzleGrid {

    private final List<String> rawPuzzleContent;

    private Map<String, List<Coordinate>> uniqueElements;

    public String[][] getGrid() {
        return puzzleGrid;
    }

    private String[][] puzzleGrid;

    private final String delimiter;

    private final int maxWidth;

    private final int maxHeight;

    public PuzzleGrid(List<String> rawPuzzleContent, String delimiter) {
        this.delimiter = delimiter;
        this.rawPuzzleContent = rawPuzzleContent;
        setPuzzleGrid(rawPuzzleContent);
        this.maxWidth = puzzleGrid[0].length;
        this.maxHeight = puzzleGrid.length;
        this.uniqueElements = new HashMap<>();
    }

    private void setPuzzleGrid(List<String> rawPuzzleContent) {
        this.puzzleGrid = getContentsAsGrid(rawPuzzleContent);
    }

    private String[][] getContentsAsGrid(List<String> rawPuzzleContent) {
        String[][] grid = new String[rawPuzzleContent.size()][rawPuzzleContent.get(0).length()];
        for (int i = 0; i < rawPuzzleContent.size(); i++) {
            grid[i] = rawPuzzleContent.get(i).split(delimiter);
        }
        return grid;
    }

    @SuppressWarnings({"ForLoopReplaceableByWhile", "ForLoopReplaceableByForEach"})
    public void showAsGrid() {
        for (int i = 0; i < puzzleGrid.length; i++) {
            for (int j = 0; j < puzzleGrid[i].length; j++) {
                System.out.print(puzzleGrid[i][j] + delimiter);
            }
            System.out.println();
        }
    }

    public int numRows() {
        return puzzleGrid.length;
    }

    public int numColumns() {
        return puzzleGrid[0].length;
    }

    public int maxWidth() {
        return maxWidth;
    }

    public int maxHeight() {
        return maxHeight;
    }

    public String elementAt(int row, int col) {
        return puzzleGrid[row][col];
    }

    public String elementAt(Coordinate coord) {
        return puzzleGrid[coord.getX()][coord.getY()];
    }

    public List<Coordinate> getCoordinatesFor(String letterToFind) {
        Map<String, List<Coordinate>> uniqueElements = getUniqueElements();
        if (uniqueElements.containsKey(letterToFind)) {
            return uniqueElements.get(letterToFind);
        }
        throw new RuntimeException(letterToFind + " not found in the PuzzleGrid");
    }

    public String correspondingLetter(Coordinate location) {
        return elementAt(location.getX(), location.getY());
    }

    public boolean tooShort(Coordinate coord) {
        return coord.getX() < 0;
    }

    public boolean tooTall(Coordinate coord) {
        return coord.getX() >= maxHeight();
    }

    public boolean tooNarrow(Coordinate coord) {
        return coord.getY() < 0;
    }

    public boolean tooWide(Coordinate coord) {
        return coord.getY() >= maxWidth();
    }

    public void setElementTo(Coordinate position, String symbol) {
        this.puzzleGrid[position.getX()][position.getY()] = symbol;
    }

    public void resetGrid() {
        setPuzzleGrid(rawPuzzleContent);
    }

    public boolean outsideGrid(Coordinate nextPosition) {
        return tooNarrow(nextPosition) || tooWide(nextPosition) ||
                tooShort(nextPosition) || tooTall(nextPosition);
    }

    public Map<String, List<Coordinate>> getUniqueElements() {
        if (uniqueElements.isEmpty()) {
            populateUniqueElements();
        }
        return uniqueElements;
    }

    private void populateUniqueElements() {
        Map<String, List<Coordinate>> uniqueElements = new HashMap<>();
        for (int i = 0; i < puzzleGrid.length; i++) {
            for (int j = 0; j < puzzleGrid[i].length; j++) {
                String element = puzzleGrid[i][j];
                Coordinate coordinate = new Coordinate(i, j);
                if (uniqueElements.containsKey(element)) {
                    uniqueElements.get(element).add(coordinate);
                } else {
                    List<Coordinate> coordinates = new ArrayList<>();
                    coordinates.add(coordinate);
                    uniqueElements.put(element, coordinates);
                }
            }
        }
        this.uniqueElements = uniqueElements;
    }

    /**
     * A very simple Straight Line algorithm. It only checks which Coordinates inside the Grid lie
     * perfectly on a straight line (taking the form <code>y = mx + c</code>.
     * <p>
     * Note: This will NOT include Coordinates that lie between the 2 Coordinates
     * </p>
     * Note that the Coordinates are flipped to ensure the line graph calculation is correct for our quadrant.
     *
     * @param coordinateOne {@link Coordinate}
     * @param coordinateTwo {@link Coordinate}
     * @return A list of Coordinates that lie perfectly on the straight line formed by the 2 Coordinates
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<Coordinate> getCoordinatesThatLiePerfectlyInTheStraightLine(Coordinate coordinateOne, Coordinate coordinateTwo) {
        coordinateOne = coordinateOne.flip();
        coordinateTwo = coordinateTwo.flip();
        int x0 = coordinateOne.getX();
        int y0 = coordinateOne.getY();
        int x1 = coordinateTwo.getX();
        int y1 = coordinateTwo.getY();
        double deltaX = x1 - x0;
        double deltaY = y1 - y0;
        double gradient = deltaY / deltaX;
        List<Coordinate> straightLineNeighbours = getStraightLineNeighbours(coordinateOne, gradient).stream()
                .map(Coordinate::flip).collect(Collectors.toList());
        return straightLineNeighbours;
    }

    private List<Coordinate> getStraightLineNeighbours(Coordinate coordinateOne, double gradient) {
        double c = coordinateOne.getY() - (gradient * coordinateOne.getX());
        List<Coordinate> straightLineNeighbours = new ArrayList<>();
        for (int i = 0; i < puzzleGrid.length; i++) {
            for (int j = 0; j < puzzleGrid[i].length; j++) {
                Coordinate coordinate = new Coordinate(j, i); // (Goudemond 20241210) Note the order - bottom right quadrant
                if (coordinate.getY() == (gradient * coordinate.getX()) + c) {
                    straightLineNeighbours.add(coordinate);
                    break;
                }
            }
        }
        return straightLineNeighbours;
    }
}
