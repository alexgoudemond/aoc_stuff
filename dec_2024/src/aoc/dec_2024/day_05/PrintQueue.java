package aoc.dec_2024.day_05;

import aoc.dec_2024.helper.PuzzleContents;
import aoc.dec_2024.helper.PuzzleInputLoader;
import aoc.dec_2024.helper.PuzzleInputLoaderImpl;

import java.util.*;

public class PrintQueue {

    private final List<String> pageOrderingRules;

    private final List<String> updatePages;

    public PrintQueue() {
        PuzzleInputLoader puzzleInputLoader = new PuzzleInputLoaderImpl("\n");
//        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_05/PrintQueueTest.txt");
        PuzzleContents puzzleContents = puzzleInputLoader.getPuzzleContents("day_05/PrintQueue001.txt");
        this.pageOrderingRules = new ArrayList<>();
        this.updatePages = new ArrayList<>();
        for (String puzzleRow : puzzleContents.getPuzzleRows()) {
            if (puzzleRow.contains("|")) {
                pageOrderingRules.add(puzzleRow);
            } else if (puzzleRow.contains(",")) {
                updatePages.add(puzzleRow);
            }
        }
    }

    // checked - no duplicate rules ; all pageOrderingRules have odd length
    public int solvePuzzle1() {
        int sumOfMiddlePageNumbers = 0;
        for (String updatePage : updatePages) {
            String[] orderedPages = updatePage.split(",");
            if (validUpdate(orderedPages)) {
                sumOfMiddlePageNumbers += Integer.parseInt(orderedPages[orderedPages.length / 2]);
            }
        }
        return sumOfMiddlePageNumbers; // 4959 --> Right Answer!
    }

    public int solvePuzzle2() {
        int sumOfMiddlePageNumbers = 0;
        List<String> invalidPages = new ArrayList<>();
        for (String updatePage : updatePages) {
            String[] orderedPages = updatePage.split(",");
            if (!validUpdate(orderedPages)) {
                invalidPages.add(updatePage);
            }
        }
        for (String invalidPage : invalidPages) {
//            System.out.println("invalid Page = " + invalidPage);
            String[] orderedPages = invalidPage.split(",");
            while (!validUpdate(orderedPages)) {
                orderedPages = getCorrectedUpdate(orderedPages);
            }
//            System.out.println("fixed page: " + Arrays.toString(orderedPages));
            sumOfMiddlePageNumbers += Integer.parseInt(orderedPages[orderedPages.length / 2]);
        }
        return sumOfMiddlePageNumbers; // 4655 --> right answer!
    }

    private String[] getCorrectedUpdate(String[] orderedPages) {
        for (int i = 0; i < orderedPages.length; i++) {
            String cursor = orderedPages[i];
            List<String> rulesForCursor = getRulesForPageNumber(cursor);
            for (int j = i + 1; j < orderedPages.length; j++) {
                String pageNumber = orderedPages[j];
                if (!isLessThan(cursor, pageNumber, rulesForCursor)) {
                    orderedPages[j] = cursor;
                    orderedPages[i] = pageNumber;
                    return orderedPages;
                }
            }
            for (int j = orderedPages.length - 1; j > i; j--) {
                String pageNumber = orderedPages[j];
                if (!isGreaterThan(pageNumber, cursor, rulesForCursor)) {
                    orderedPages[j] = cursor;
                    orderedPages[i] = pageNumber;
                    return orderedPages;
                }
            }
        }
        return orderedPages;
    }

    private boolean validUpdate(String[] orderedPages) {
        for (int i = 0; i < orderedPages.length; i++) {
            String cursor = orderedPages[i];
            List<String> rulesForCursor = getRulesForPageNumber(cursor);
            for (int j = i + 1; j < orderedPages.length; j++) {
                if (!isLessThan(cursor, orderedPages[j], rulesForCursor)) {
//                    System.out.println(updatePage + " !");
                    return false;
                }
            }
            for (int j = orderedPages.length - 1; j > i; j--) {
                if (!isGreaterThan(orderedPages[j], cursor, rulesForCursor)) {
//                    System.out.println(updatePage + " !");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLessThan(String cursorValue, String pageValue, List<String> pageRules) {
        for (String rule : pageRules) {
            if (rule.contains(pageValue)) {
                return rule.indexOf(pageValue) > rule.indexOf(cursorValue);
            }
        }
        return false;
    }

    private boolean isGreaterThan(String pageValue, String cursorValue, List<String> pageRules) {
        for (String rule : pageRules) {
            if (rule.contains(pageValue)) {
                return rule.indexOf(cursorValue) < rule.indexOf(pageValue);
            }
        }
        return false;
    }

    private List<String> getRulesForPageNumber(String pageNumber) {
        List<String> rulesForPage = new ArrayList<>();
        for (String rule : pageOrderingRules) {
            if (rule.contains(pageNumber)) {
                rulesForPage.add(rule);
            }
        }
        return rulesForPage;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        PrintQueue printQueue = new PrintQueue();
        int puzzle1Solution = printQueue.solvePuzzle1();
        System.out.println("puzzle1Solution = " + puzzle1Solution);
        int puzzle2Solution = printQueue.solvePuzzle2();
        System.out.println("puzzle2Solution = " + puzzle2Solution);
    }

}
