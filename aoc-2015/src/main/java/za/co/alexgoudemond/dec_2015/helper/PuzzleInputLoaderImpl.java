package za.co.alexgoudemond.dec_2015.helper;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PuzzleInputLoaderImpl implements PuzzleInputLoader {

    private final String relativeLocation = "dec_2015/resources/aoc/dec_2015/";

    @Override
    public List<String> getRawPuzzleInput(String docName) {
        try {
            return Files.readAllLines(Path.of(relativeLocation, docName));
        } catch (IOException e) {
            throw new RuntimeException("Unable to get the Puzzle Contents.", e);
        }
    }

}
