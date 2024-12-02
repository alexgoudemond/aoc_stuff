package aoc.dec_2024.helper;

import java.util.List;

public interface PuzzleInputLoader {

    List<String> getRawPuzzleContents(String docName);

    PuzzleContents getPuzzleContents(String docName);
}
