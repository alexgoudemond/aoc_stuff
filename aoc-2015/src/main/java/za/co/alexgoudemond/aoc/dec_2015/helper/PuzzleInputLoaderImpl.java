package za.co.alexgoudemond.aoc.dec_2015.helper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PuzzleInputLoaderImpl implements PuzzleInputLoader {

    private final String relativeLocation = "aoc-2015/src/main/resources/";

    @Override
    public List<String> getRawPuzzleInput(String docName) {
        try {
            return Files.readAllLines(Path.of(relativeLocation, docName));
        } catch (IOException e) {
            throw new RuntimeException("Unable to get the Puzzle Contents.", e);
        }
    }

}
