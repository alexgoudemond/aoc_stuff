package aoc.dec_2024.day_09;

import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.ArrayList;
import java.util.List;

public class DiskDefragmenter {

    private final List<String> rawPuzzleInput;

    public DiskDefragmenter() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("");
//        this.rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_09/DiskDefragmenterTest.txt");
//        this.rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_09/DiskDefragmenterTest2.txt");
        this.rawPuzzleInput = puzzleInputLoader.getRawPuzzleInput("day_09/DiskDefragmenter.txt");
    }

    public long solvePuzzle1() {
//        System.out.println("Hello World!");
//        System.out.println("rawPuzzleInput = " + rawPuzzleInput);
        String[] diskMap = rawPuzzleInput.get(0).split("");
        long actualFileSize = calculateActualFileSize(diskMap);
//        System.out.println("actualFileSize = " + actualFileSize);
        List<String> memory = getFileBlocksInMemory(diskMap);
//        System.out.println("memory = " + memory);
        int numberEmptySlots = (int) memory.stream()
                .filter(file -> file.equals("."))
                .count();
//        System.out.println("numberEmptySlots = " + numberEmptySlots);
        fragment(memory);
//        System.out.println("memory = " + memory);
        long checksum = getChecksum(memory, numberEmptySlots);
        System.out.println("checksum = " + checksum);
        return checksum;
    }

    private long getChecksum(List<String> memory, int numberEmptySlots) {
        long checksum = 0L;
        int usedMemorySpace = memory.size() - numberEmptySlots;
        for (int i = 0; i < usedMemorySpace; i++) {
            checksum += (long) i * asNumber(memory.get(i));
        }
        return checksum;
    }

    private static void fragment(List<String> memory) {
        int leftCursor = -1;
        int rightCursor = memory.size() - 1;
        while (true) {
            leftCursor++;
            if (!memory.get(leftCursor).equals(".")) {
                continue;
            }
            if (leftCursor >= rightCursor) {
                break;
            }
            memory.set(leftCursor, memory.get(rightCursor));
            memory.set(rightCursor--, ".");
            while(rightCursor != 0 && memory.get(rightCursor).equals(".")) {
                rightCursor--;
            }
//            System.out.println("memory = " + memory);
        }
    }

    private List<String> getFileBlocksInMemory(String[] diskMap) {
        List<String> memory = new ArrayList<>();
        // populate info with correct info
        int idNumber = 0;
        for (int i = 0; i < diskMap.length; i++) {
            int digit = asNumber(diskMap[i]);
            for (int j = 0; j < digit; j++) {
                if (i % 2 == 1) {
                    memory.add(".");
                } else {
                    memory.add(Integer.toString(idNumber));
                }
            }
            if (i % 2 == 0) {
                idNumber++;
            }
        }
        return memory;
    }

    private long calculateActualFileSize(String[] diskMap) {
        long counter = 0;
        for (String digit : diskMap) {
            int number = asNumber(digit);
            counter += number;
        }
        return counter;
    }

    private int asNumber(String digit) {
        return Integer.parseInt(digit);
    }

    public static void main(String[] args) {
        DiskDefragmenter diskDefragmenter = new DiskDefragmenter();
        long puzzle1solution = diskDefragmenter.solvePuzzle1();
        System.out.println("puzzle1solution = " + puzzle1solution);
    }
}
