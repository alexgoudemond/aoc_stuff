package aoc.dec_2024.day_10;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

public class HoofIt {

    private final PuzzleContents puzzleContents;

    public HoofIt() {
        PuzzleInputLoaderImpl puzzleInputLoader = new PuzzleInputLoaderImpl("");
        this.puzzleContents = puzzleInputLoader.getPuzzleContents("day_10/HoofIt001.txt");
    }

    private void solvePuzzle1() {
        puzzleContents.showAsGrid();
        // get starting coords

        // algorithm - foreach trailhead : endPositions ; currentCoordinate ; visitedCoordinates ; stack

        // size of the endPositions, foreach trailhead is the score. Add all scores together

    }

    public static void main(String[] args) {
        HoofIt hoofIt = new HoofIt();
        hoofIt.solvePuzzle1();
    }
}
