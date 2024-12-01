package aoc.dec_2024.day_01;

import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.List;

public class HistorianHysteria {

    private final PuzzleInputLoader puzzleInputLoader;

    public HistorianHysteria() {
        this.puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    public void solvePuzzle1() {
        List<String> puzzleContents = puzzleInputLoader.getPuzzleContents("day_01/HistorianHysteria001.txt");
        puzzleContents.forEach(System.out::println);
    }

    public static void main(String[] args) {
        HistorianHysteria historianHysteria = new HistorianHysteria();
        historianHysteria.solvePuzzle1();
    }
}
