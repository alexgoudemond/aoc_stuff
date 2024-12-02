package aoc.dec_2024.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PuzzleInputLoaderImpl implements PuzzleInputLoader {

    private final String relativeLocation = "dec_2024/resources/aoc/dec_2024/";

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
        return new PuzzleContents(puzzleContents);
    }
}
