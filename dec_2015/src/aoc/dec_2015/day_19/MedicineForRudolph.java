package aoc.dec_2015.day_19;

import aoc.dec_2015.helper.PuzzleInputLoader;
import aoc.dec_2015.helper.PuzzleInputLoaderImpl;

import java.util.List;

public class MedicineForRudolph {

    private final PuzzleInputLoader puzzleInputLoader;

    public MedicineForRudolph() {
        puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    private void solvePuzzle1() {
        List<String> rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_19/MedicineForRudolphTest.txt");
        rawPuzzleInput.forEach(System.out::println);
    }

    public static void main(String[] args) {
        MedicineForRudolph medicineForRudolph = new MedicineForRudolph();
        medicineForRudolph.solvePuzzle1();
    }
}
