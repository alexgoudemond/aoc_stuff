package aoc.dec_2024.day_05;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.List;

public class PrintQueue {

    private final List<String[]> puzzleRows;

    public PrintQueue() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("\n");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_05/PrintQueueTest.txt");
        this.puzzleRows = puzzleContents.getPuzzleRows();
    }

    public int solvePuzzle1(){
        for (String[] puzzleRow : puzzleRows) {

        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        PrintQueue printQueue = new PrintQueue();
        printQueue.solvePuzzle1();
    }

}
