package za.co.alexgoudemond.aoc.dec_2024.day_09;

import za.co.alexgoudemond.aoc.dec_2024.helper.PuzzleInputLoader;
import za.co.alexgoudemond.aoc.dec_2024.helper.PuzzleInputLoaderImpl;

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
        fragmentByIndividualID(memory);
        System.out.println("memory = " + memory);
        return getChecksumOfLeftFragmentedMemory(memory); // 6398252054886 --> correct
    }

    // TODO Goudemond 2024/12/14 | keep going
    public long solvePuzzle2() {
        String[] diskMap = rawPuzzleInput.get(0).split("");
        List<String> memory = getFileBlocksInMemory(diskMap);
//        System.out.println("memory = " + memory);
        List<String> exclusionList = new ArrayList<>();
        while (true) {
            List<String> rightMostFileIDCluster = getRightMostFileIDCluster(memory, exclusionList);
//            System.out.println("rightMostFileIDCluster = " + rightMostFileIDCluster);
            int nextAvailableGapIndex = getNextAvailableGap(rightMostFileIDCluster, memory, exclusionList);
//            System.out.println("nextAvailableGapIndex = " + nextAvailableGapIndex);
            if (nextAvailableGapIndex != -1) {
                swapFileIDs(rightMostFileIDCluster, nextAvailableGapIndex, memory);
            }else{
                exclusionList.add(rightMostFileIDCluster.get(0));
            }
            if (rightMostFileIDCluster.contains("0")){
                break;
            }
            System.out.println("memory = " + memory);
        }
        System.out.println("memory = " + memory);
        //checksum
        // TODO Goudemond 2024/12/15 | taking too long to solve - need to find another approach to the solution
        return getChecksumEntireMemory(memory);
    }

    private long getChecksumEntireMemory(List<String> memory) {
        long checksum = 0L;
        for (int i = 0; i < memory.size(); i++) {
            String digit = memory.get(i).equals(".") ? "0" : memory.get(i);
            checksum += (long) i * asNumber(digit);
        }
//        System.out.println("checksum = " + checksum);
        return checksum;
    }

    private void swapFileIDs(List<String> fileIDCluster, int nextAvailableGap, List<String> memory) {
        for (int i = fileIDCluster.size() - 1; i >= 0; i--) {
            String fileIDNumber = fileIDCluster.get(i);
            int memoryIndexToWipe = memory.lastIndexOf(fileIDNumber);
            memory.set(nextAvailableGap, fileIDNumber);
            memory.set(memoryIndexToWipe, ".");
            nextAvailableGap--;
        }
    }

    private int getNextAvailableGap(List<String> fileIDCluster, List<String> memory, List<String> exclusionList) {
        int leftIndex = -1;
        int emptySlotCount = 0;
        String fileIDNumber;
        int endOfSearch = memory.indexOf(fileIDCluster.get(0));
        while (emptySlotCount < fileIDCluster.size()) {
            leftIndex++;
            if (leftIndex >= memory.size() || leftIndex == endOfSearch) {
                return -1;
            }
            fileIDNumber = memory.get(leftIndex);
            if (exclusionList.contains(fileIDNumber)) {
                return -1;
            }
            if (fileIDNumber.equals(".")) {
                emptySlotCount++;
            }else{
                emptySlotCount = 0;
            }
        }
        return leftIndex;
    }

    private List<String> getRightMostFileIDCluster(List<String> memory, List<String> exclusionList) {
        List<String> fileIDCluster = new ArrayList<>();
        int rightIndex = memory.size() - 1;
        String rightMostfileID;
        while (true) {
            do {
                rightMostfileID = memory.get(rightIndex);
                rightIndex--;
            } while (rightMostfileID.equals(".") || exclusionList.contains(rightMostfileID));
            if (fileIDCluster.isEmpty()) {
                fileIDCluster.add(rightMostfileID);
            } else if (fileIDCluster.contains(rightMostfileID)) {
                fileIDCluster.add(rightMostfileID);
            } else {
                break;
            }
            if (rightIndex == 0) {
                return fileIDCluster;
            }
        }
        return fileIDCluster;
    }

    private long getChecksumOfLeftFragmentedMemory(List<String> memory) {
        int numberEmptySlots = (int) memory.stream()
                .filter(file -> file.equals("."))
                .count();
//        System.out.println("numberEmptySlots = " + numberEmptySlots);
        long checksum = 0L;
        int usedMemorySpace = memory.size() - numberEmptySlots;
        for (int i = 0; i < usedMemorySpace; i++) {
            checksum += (long) i * asNumber(memory.get(i));
        }
        return checksum;
    }

    private void fragmentByIndividualID(List<String> memory) {
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
            while (rightCursor != 0 && memory.get(rightCursor).equals(".")) {
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
        long puzzle1Solution = diskDefragmenter.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        long puzzle2Solution = diskDefragmenter.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }
}
