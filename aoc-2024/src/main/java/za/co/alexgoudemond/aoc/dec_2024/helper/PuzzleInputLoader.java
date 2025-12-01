package za.co.alexgoudemond.aoc.dec_2024.helper;

import java.util.List;

public interface PuzzleInputLoader {

    List<String> getRawPuzzleInput(String docName);

    PuzzleContents getPuzzleContents(String docName);
}
