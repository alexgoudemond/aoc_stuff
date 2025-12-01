package za.co.alexgoudemond.aoc.dec_2023.day_05;

import java.util.ArrayList;
import java.util.List;

public class InvertedAlmanac {

  private final List<Long> seeds;

  private final List<AlmanacMapEntry> mapEntries;

  public InvertedAlmanac(String[] seeds) {
    mapEntries = new ArrayList<>();
    this.seeds = new ArrayList<>();
    for (String seed : seeds) {
      this.seeds.add(Long.parseLong(seed));
    }
  }

  public void add(AlmanacMapEntry mapEntry) {
    mapEntries.add(mapEntry);
  }

  public List<AlmanacMapEntry> getMapEntries() {
    return mapEntries;
  }

  public long traverseMapEntries(long walkNumber) {
    for (AlmanacMapEntry mapEntry : getMapEntries()) {
      Rule associatedRule = mapEntry.getAssociatedRule(walkNumber);
      walkNumber = walkNumber - associatedRule.getShift();
    }
    return walkNumber;
  }

  public boolean seedExists(long number) {
    for (int i = 0; i < seeds.size(); i += 2) {
      long seedStart = seeds.get(i);
      long seedEnd = seedStart + seeds.get(i + 1);
      if (number >= seedStart && number < seedEnd) {
        return true;
      }
    }
    return false;
  }

  public long getSeed(long number) {
    if (seedExists(number)) {
      return number;
    }
    return -1;
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (AlmanacMapEntry mapEntry : mapEntries) {
      output.append(mapEntry.toString());
      output.append("\n");
    }
    return output.toString();
  }

  public Rule getLowestRuleFromFirstEntry() {
    return mapEntries.get(0).getLowestRule();
  }

  public long traverseMapEntriesAndGetSeed(long num) {
    long correspondingNum = traverseMapEntries(num); //works?
    return getSeed(correspondingNum);
  }
}
