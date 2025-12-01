package za.co.alexgoudemond.day_05;

import za.co.alexgoudemond.util.Utilities;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SeedLocation {

  public static final String COLON_SYMBOL = ":";

  /*
  * Puzzle 2 is WAY too many numbers, look at Brute Force code to solve*/
  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_05/almanac.txt";
    List<String> documentContents = Utilities.getDocumentContentsNio1(docName);
//    documentContents = getTestInput();
    String lines = String.join("\n", documentContents);

    puzzle1(lines);

    //todo - OutOfMemoryError
//    String[] almanacPieces = getAlmanacPieces(lines);
//    String tempFileName = "resources/day_05/newSeedNumbers.txt";
//    solveWithTempTextFile(almanacPieces, tempFileName);
    //out of memory error - try multiple text files next

//    String[] almanacPieces = getAlmanacPieces(lines);
//    String[] initialSeeds = almanacPieces[0].split(" ");
//    long lowestSeedLocation = getLowestSeedLocation(almanacPieces, initialSeeds);
//    System.out.println("lowestSeedLocation = " + lowestSeedLocation);
  }

  private static Long getLowestSeedLocation(String[] almanacPieces, String[] initialSeeds) {
    long lowestLocation = -1;
    long mappedValue;
    long source, range, destinationDifference;
    long currentSeedNumber, seedNumberRange;
    int i, k;
    long j;
    String[] mapInstruction = new String[3];
    for (i = 0; i < initialSeeds.length; i += 2) {
      currentSeedNumber = castToLong(initialSeeds[i]);
      seedNumberRange = castToLong(initialSeeds[i + 1]);
      for (j = currentSeedNumber; j < currentSeedNumber + seedNumberRange; j++) {
//        System.out.println("seed: " + j);
        mappedValue = j;
        for (k = 1; k < almanacPieces.length; k++) {
          String[] sectionInstructions = almanacPieces[k].split("\n");
          for (String sectionInstruction : sectionInstructions) {
            mapInstruction = sectionInstruction.split(" ");
            source = castToLong(mapInstruction[1]);
            range = source + castToLong(mapInstruction[2]);
            destinationDifference = source - castToLong(mapInstruction[0]);
            if (mappedValue >= source && mappedValue <= range) {
              mappedValue -= destinationDifference;
//              System.out.println("i, j " + i + ", " + j);
              break;
            }
          }
        }
        if (lowestLocation == -1 || mappedValue < lowestLocation) {
          lowestLocation = mappedValue;
        }
      }

    }
    return lowestLocation; //21134645 --> incorrect! todo - debug
  }

  private static void solveWithTempTextFile(String[] almanacPieces, String tempFileName) {
    String[] initialSeeds = almanacPieces[0].split(" ");
    for (int i = 0; i < initialSeeds.length; i += 2) {
      long currentSeedNumber = castToLong(initialSeeds[i]);
      long seedNumberRange = castToLong(initialSeeds[i + 1]);
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName))) {
        for (int j = 0; j < seedNumberRange; j++) {
          writer.write((currentSeedNumber++) + "\n");
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      String[] tempSeeds = Utilities.getDocumentContents(tempFileName).toArray(new String[0]);
      Map<Long, Long> seedLocations = getSeedLocations(almanacPieces, tempSeeds);
      long lowestLocation = -1;
      for (Long location : seedLocations.values()) {
        if (lowestLocation == -1 || location < lowestLocation) {
          lowestLocation = location;
        }
      }
      System.out.println("lowestLocation = " + lowestLocation);

      break;
    }
  }


  // break this into pieces - too many numbers I think
  private static String[] getExpandedRangeOfSeeds(String[] initialSeeds) {
    List<Long> expandedSeeds = new ArrayList<>();
    for (int i = 0; i < initialSeeds.length; i += 2) {
      long currentSeedNumber = castToLong(initialSeeds[i]);
      long seedNumberRange = castToLong(initialSeeds[i + 1]);
      for (int j = 0; j < seedNumberRange; j++) {
        expandedSeeds.add(currentSeedNumber++);
      }
    }
//    System.out.println("expandedSeeds.toString() = " + expandedSeeds);
    String[] newSeeds = new String[expandedSeeds.size()];
    for (int i = 0; i < expandedSeeds.size(); i++) {
      newSeeds[i] = Long.toString(expandedSeeds.get(i));
    }
    return newSeeds;
  }

  private static void puzzle1(String lines) {
    String[] almanacPieces = getAlmanacPieces(lines);
    String[] initialSeeds = almanacPieces[0].split(" ");
    Map<Long, Long> seedLocations = getSeedLocations(almanacPieces, initialSeeds);
    long lowestLocation = -1;
    for (Long location : seedLocations.values()) {
      if (lowestLocation == -1 || location < lowestLocation) {
        lowestLocation = location;
      }
    }
    System.out.println("lowestLocation = " + lowestLocation); // 111627841 --> correct!
  }

  private static Map<Long, Long> getSeedLocations(String[] almanacPieces, String[] initialSeeds) {
    // look through directions of Almanac
    Map<Long, Long> seedLocations = new HashMap<>();
    for (String seed : initialSeeds) {
      long mappedValue = castToLong(seed);
      long source = 0;
      long range = 0;
      long destinationDifference = 0;
      for (int i = 1; i < almanacPieces.length; i++) {
        String[] sectionInstructions = almanacPieces[i].split("\n");
        for (String sectionInstruction : sectionInstructions) {
          String[] mapInstruction = sectionInstruction.split(" ");
          source = castToLong(mapInstruction[1]);
          range = source + castToLong(mapInstruction[2]);
          destinationDifference = source - castToLong(mapInstruction[0]);
          if (mappedValue >= source && mappedValue <= range) {
            mappedValue -= destinationDifference;
            break;
          }
        }
      }
      seedLocations.put(castToLong(seed), mappedValue);
    }
    return seedLocations;
  }

  private static List<String> getTestInput() {
    List<String> documentContents;
    String temp = "seeds: 79 14 55 13\n" +
        "\n" +
        "seed-to-soil map:\n" +
        "50 98 2\n" +
        "52 50 48\n" +
        "\n" +
        "soil-to-fertilizer map:\n" +
        "0 15 37\n" +
        "37 52 2\n" +
        "39 0 15\n" +
        "\n" +
        "fertilizer-to-water map:\n" +
        "49 53 8\n" +
        "0 11 42\n" +
        "42 0 7\n" +
        "57 7 4\n" +
        "\n" +
        "water-to-light map:\n" +
        "88 18 7\n" +
        "18 25 70\n" +
        "\n" +
        "light-to-temperature map:\n" +
        "45 77 23\n" +
        "81 45 19\n" +
        "68 64 13\n" +
        "\n" +
        "temperature-to-humidity map:\n" +
        "0 69 1\n" +
        "1 0 69\n" +
        "\n" +
        "humidity-to-location map:\n" +
        "60 56 37\n" +
        "56 93 4";
    documentContents = temp.lines().collect(Collectors.toList());
    return documentContents;
  }

  private static long castToLong(String str) {
    return Long.parseLong(str);
  }

  private static int castToInt(String str) {
    return Integer.parseInt(str);
  }

  private static String[] getAlmanacPieces(String lines) {
    String[] almanacPieces = lines.split("\n\n");
    for (int i = 0; i < almanacPieces.length; i++) {
      String almanacPiece = almanacPieces[i];
      int colonIndex = almanacPiece.indexOf(COLON_SYMBOL);
      almanacPieces[i] = almanacPiece.substring(colonIndex + 1).trim();
    }
    return almanacPieces;
  }
}
