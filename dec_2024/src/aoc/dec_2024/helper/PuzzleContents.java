package aoc.dec_2024.helper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PuzzleContents {

    private final List<String> rawPuzzleContent;

    private final List<String[]> puzzleRows;

    private final String[][] puzzleGrid;

    private final String delimiter;

    public PuzzleContents(List<String> rawPuzzleContent, String delimiter) {
        this.delimiter = delimiter == null ? "" : delimiter;
        this.rawPuzzleContent = rawPuzzleContent;
        this.puzzleRows = getPuzzleAsRows(rawPuzzleContent);
        this.puzzleGrid = getContentsAsGrid();
    }

    @SuppressWarnings("DataFlowIssue")
    private List<String[]> getPuzzleAsRows(List<String> rawPuzzleContent) {
        return rawPuzzleContent.stream()
                .map(str -> str.split(delimiter))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("DataFlowIssue")
    private String[][] getContentsAsGrid() {
        String[][] grid = new String[rawPuzzleContent.size()][rawPuzzleContent.get(0).length()];
        for (int i = 0; i < rawPuzzleContent.size(); i++) {
            grid[i] = rawPuzzleContent.get(i).split(delimiter);
        }
        return grid;
    }

    public List<String> getRawPuzzleContent() {
        return rawPuzzleContent;
    }

    public List<String[]> getPuzzleRows() {
        return puzzleRows;
    }

    public List<String> getPuzzleRow(int index) {
        return List.of(puzzleRows.get(index));
    }

    public int getNumberOfRows() {
        return puzzleRows.size();
    }

    public String[][] getPuzzleGrid() {
        return puzzleGrid;
    }

    public void forEach(Consumer<String> consumer) {
        rawPuzzleContent.forEach(consumer);
    }

    @Override
    public String toString() {
        return "PuzzleContents{" +
                "rawPuzzleContent=" + rawPuzzleContent +
                ", puzzleRows=" + puzzleRowsToString() +
                '}';
    }

    public List<String> puzzleRowsToString() {
        return puzzleRows.stream()
                .map(Arrays::toString)
                .collect(Collectors.toList());
    }

    public void showAsGrid() {
        rawPuzzleContent.forEach(row -> System.out.println(row.toCharArray()));
    }
}
