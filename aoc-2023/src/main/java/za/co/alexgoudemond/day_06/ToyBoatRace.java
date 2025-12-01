package za.co.alexgoudemond.day_06;

import za.co.alexgoudemond.util.Utilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ToyBoatRace {

  public static final char COLON_SYMBOL = ':';

  public static void main(String[] args) {
//    String docName = "dec_2023/resources/day_06/testRaceRecords.txt";
    String docName = "dec_2023/resources/day_06/raceRecords.txt";
    List<String> documentContents = Utilities.getDocumentContentsNio1(docName);
    puzzle1(documentContents);
    puzzle2(documentContents);
  }

  private static void puzzle2(List<String> documentContents) {
    long[] raceRecords = parseDocumentWithKerning(documentContents);
    long raceLength = raceRecords[0];
    long distanceRecord = raceRecords[1];
    long smallestTimeNeeded = 0;
    for (long j = 2; j < distanceRecord - 2; j++) {
      if (j * (raceLength - j) > distanceRecord) {
        smallestTimeNeeded = j;
        break;
      }
    }
    long largestTimeNeeded = raceLength - smallestTimeNeeded;
    long numOfWaysToWin = largestTimeNeeded - smallestTimeNeeded + 1;
    System.out.println("numOfWaysToWin = " + numOfWaysToWin); // 27102791 --> correct!
  }

  private static long[] parseDocumentWithKerning(List<String> documentContents) {
    long[] raceRecords = new long[2];
    for (int i = 0; i < documentContents.size(); i++) {
      String line = documentContents.get(i);
      long temp = Long.parseLong(line.substring(line.indexOf(COLON_SYMBOL) + 1).replaceAll(" ", ""));
      raceRecords[i] = temp;
    }
    return raceRecords;
  }

  private static void puzzle1(List<String> documentContents) {
    String[] raceRecords = parseDocumentContents(documentContents);
    Integer[] times = transformStringIntoIntegers(raceRecords[0]);
    Integer[] distances = transformStringIntoIntegers(raceRecords[1]);
    int marginOfError = getMarginOfError(times, distances);
    System.out.println("marginOfError = " + marginOfError); // 3316275 --> correct!
  }

  private static int getMarginOfError(Integer[] times, Integer[] distances) {
    int marginOfError = 1;
    for (int i = 0; i < times.length; i++) {
      int raceLength = times[i];
      int distanceRecord = distances[i];
      int numOfWaysToWin = 0;
      for (int j = 2; j < distanceRecord - 2; j++) {
        if (j * (raceLength - j) > distanceRecord) {
          numOfWaysToWin++;
        }
      }
      marginOfError *= numOfWaysToWin;
    }
    return marginOfError;
  }

  private static Integer[] transformStringIntoIntegers(String str) {
    return Arrays.stream(str.split(" "))
        .mapToInt(Integer::parseInt)
        .boxed()
        .collect(Collectors.toList())
        .toArray(new Integer[2]);
  }

  private static String[] parseDocumentContents(List<String> documentContents) {
    String[] raceRecords = new String[documentContents.size()];
    for (int i = 0; i < documentContents.size(); i++) {
      String line = documentContents.get(i);
      String formattedLine = line.substring(line.indexOf(COLON_SYMBOL) + 1).trim();
      formattedLine = formattedLine.replaceAll("\\s+", " "); //replace more than 1 whitespace
      raceRecords[i] = formattedLine;
    }
    return raceRecords;
  }
}
