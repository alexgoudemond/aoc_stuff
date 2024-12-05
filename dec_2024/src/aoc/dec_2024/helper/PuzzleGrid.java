package aoc.dec_2024.helper;

import java.util.ArrayList;
import java.util.List;

public class PuzzleGrid {

    private final String[][] puzzleGrid;

    private final String delimiter;

    private final int maxWidth;

    private final int maxHeight;

    public PuzzleGrid(List<String> rawPuzzleContent, String delimiter) {
        this.delimiter = delimiter;
        this.puzzleGrid = getContentsAsGrid(rawPuzzleContent);
        maxWidth = puzzleGrid[0].length;
        maxHeight = puzzleGrid.length;
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

    public int numRows(){
        return puzzleGrid.length;
    }

    public int numColumns(){
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

    public List<Coordinate> getCoordinatesFor(String letterToFind) {
        List<Coordinate> xLocations = new ArrayList<>();
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                if (elementAt(i, j).equals(letterToFind)) {
                    xLocations.add(new Coordinate(i, j));
                }
            }
        }
        return xLocations;
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
}
