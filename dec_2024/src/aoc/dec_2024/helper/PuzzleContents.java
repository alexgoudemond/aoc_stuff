package aoc.dec_2024.helper;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PuzzleContents {

    private final List<String> rawPuzzleContent;

    private final List<String[]> puzzleRows;

    public PuzzleContents(List<String> rawPuzzleContent) {
        this.rawPuzzleContent = rawPuzzleContent;
        this.puzzleRows = rawPuzzleContent.stream()
                .map(str -> str.split(" "))
                .collect(Collectors.toList());
    }

    public List<String> getRawPuzzleContent() {
        return rawPuzzleContent;
    }

    public List<String[]> getPuzzleRows() {
        return puzzleRows;
    }

    public String[] getPuzzleRow(int index){
        return puzzleRows.get(index);
    }

    public void forEach(Consumer<String> consumer) {
        rawPuzzleContent.forEach(consumer);
    }
}
