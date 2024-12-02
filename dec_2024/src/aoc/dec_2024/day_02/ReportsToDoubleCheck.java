package aoc.dec_2024.day_02;

import java.util.ArrayList;
import java.util.List;

public class ReportsToDoubleCheck {

    private final List<List<Integer>> reportsToDoubleCheck;

    private int safeLevels;

    public ReportsToDoubleCheck() {
        this.reportsToDoubleCheck = new ArrayList<>();
        this.safeLevels = 0;
    }

    public void add(List<Integer> report) {
        this.reportsToDoubleCheck.add(report);
    }

    public void increaseSafeLevels() {
        safeLevels++;
    }

    public List<List<Integer>> getReportsToDoubleCheck() {
        return reportsToDoubleCheck;
    }

    public int getSafeLevels() {
        return safeLevels;
    }
}
