package aoc.dec_2024.day_01;

import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.*;
import java.util.stream.Collectors;

public class HistorianHysteria {

    private final PuzzleInputLoader puzzleInputLoader;

    public HistorianHysteria() {
        this.puzzleInputLoader = new PuzzleInputLoaderImpl();
    }

    public Integer solvePuzzle1() {
//        List<String> puzzleContents = puzzleInputLoader.getPuzzleContents("day_01/HistorianHysteriaTest.txt");
        List<String> puzzleContents = puzzleInputLoader.getPuzzleContents("day_01/HistorianHysteria001.txt");
        List<Integer> leftHistorianList = new ArrayList<>();
        List<Integer> rightHistorianList = new ArrayList<>();
        for (String row : puzzleContents) {
            String[] split = row.split(" +");
            leftHistorianList.add(Integer.parseInt(split[0]));
            rightHistorianList.add(Integer.parseInt(split[1]));
        }
        List<Integer> leftHistorianListOrdered = orderHistorianList(leftHistorianList);
        List<Integer> rightHistorianListOrdered = orderHistorianList(rightHistorianList);
        List<Integer> historianListDistance = new ArrayList<>();
        for (int i = 0; i < leftHistorianListOrdered.size(); i++) {
            int distance = rightHistorianListOrdered.get(i) - leftHistorianListOrdered.get(i);
            historianListDistance.add(Math.abs(distance));
        }
        Integer totalDistance = historianListDistance.stream().reduce(0, Integer::sum);
        return totalDistance; // 1577386 ; 1660292 -> correct!
    }

    private List<Integer> orderHistorianList(List<Integer> historianList) {
        return historianList.stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        HistorianHysteria historianHysteria = new HistorianHysteria();
        Integer puzzle1Solution = historianHysteria.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
    }
}
