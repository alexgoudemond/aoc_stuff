package za.co.alexgoudemond.aoc.dec_2024.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PuzzleInputLoaderImpl implements PuzzleInputLoader {

    private final String relativeLocation = "aoc-2024/src/main/resources/dec_2024/";

    private final String delimiter;

    public PuzzleInputLoaderImpl(String delimiter) {
        this.delimiter = delimiter == null ? "" : delimiter;
    }

    @Override
    public List<String> getRawPuzzleInput(String docName) {
        try {
            return Files.readAllLines(Path.of(relativeLocation, docName));
        } catch (IOException e) {
            throw new RuntimeException("Unable to get the Puzzle Contents.", e);
        }
    }

    @Override
    public PuzzleContents getPuzzleContents(String docName) {
        List<String> puzzleContents = getRawPuzzleInput(docName);
        return new PuzzleContents(puzzleContents, delimiter);
    }
}
