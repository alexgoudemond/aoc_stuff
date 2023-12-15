package day_03;

import custom_accumulators.MyAccumulator;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GondolaLift {

  public static final char PERIOD_SYMBOL = '.';

  public static final char STAR_SYMBOL = '*';

  public static List<String> documentContents;

  /**
   * Dataset does not have any special symbols on the first or last line
   * -> we can use that!
   */
  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_03/engineSchematic.txt";
    documentContents = Utilities.getDocumentContents(docName);
//    puzzle1();

    List<Integer> gearRatios = getGearRatios();
//    gearRatios.forEach(System.out::println);
    Integer sumOfGearRatios = gearRatios.stream().reduce(0, Integer::sum);
    System.out.println("sumOfGearRatios = " + sumOfGearRatios); //80694070 --> correct!
  }

  private static List<Integer> getGearRatios() {
    List<Integer> gearRatios = new ArrayList<>();
    for (int i = 1; i < documentContents.size() - 1; i++) {
      List<Integer> gearIndices = getGearIndices(i);
//      gearIndices.forEach(System.out::println);
      for (Integer gearIndex : gearIndices) {
        List<Integer> partNumbers = getPartNumbers(gearIndex, i);
        if (partNumbers.size() == 2) {
//          System.out.println("2 gear ratio found @ i: " + i + " " + Arrays.toString(partNumbers.toArray()));
          gearRatios.add(partNumbers.stream().reduce(1, MyAccumulator::multiply));
        }
      }
//      break;
    }
    return gearRatios;
  }

  private static void puzzle1() {
    int sumOfPartNums = 0;
    // don't include first and last rows
    for (int i = 1; i < documentContents.size() - 1; i++) {
      List<Integer> specialSymbolIndices = getSpecialSymbolIndices(i);
//      specialSymbolIndices.forEach(System.out::println);
      for (Integer specialSymbolIndex : specialSymbolIndices) {
        List<Integer> partNumbers = getPartNumbers(specialSymbolIndex, i);
//        partNumbers.forEach(System.out::println);
        for (Integer partNumber : partNumbers) {
          sumOfPartNums += partNumber;
        }
      }
    }
    System.out.println("sumOfPartNums = " + sumOfPartNums); //521601 --> Correct
  }

  //look at each compass position around this point
  private static List<Integer> getPartNumbers(int numCols, int numRows) {
    List<Integer> partNumbers = new ArrayList<>();
    for (int j = numRows - 1; j < numRows + 2; j++) {
      char[] row = documentContents.get(j).toCharArray();
      for (int k = numCols - 1; k < numCols + 2; k++) {
        String partNumber = "";
        if (Character.isDigit(row[k])) {
          int indexOfInterest = k;
          //walk backwards until hit a nonDigit
          while (indexOfInterest > 0 && Character.isDigit(row[indexOfInterest - 1])) {
            indexOfInterest--;
          }
          //walk forwards until hit a nonDigit
          while (indexOfInterest < row.length && Character.isDigit(row[indexOfInterest])) {
            partNumber += row[indexOfInterest++];
          }
          //move k to avoid duplicate partNumbers
          if (indexOfInterest > k) {
            k = indexOfInterest;
          }
        }
        if (!partNumber.isBlank()) {
          partNumbers.add(Integer.valueOf(partNumber));
        }
      }
    }
    return partNumbers;
  }

  private static List<Integer> getSpecialSymbolIndices(int i) {
    char[] charArray = documentContents.get(i).toCharArray();
    List<Integer> specialSymbolIndices = new ArrayList<>();
    for (int j = 0; j < charArray.length; j++) {
      char chr = charArray[j];
      if (!Character.isDigit(chr) && chr != PERIOD_SYMBOL) {
        specialSymbolIndices.add(j);
      }
    }
    return specialSymbolIndices;
  }

  private static List<Integer> getGearIndices(int i) {
    char[] charArray = documentContents.get(i).toCharArray();
    List<Integer> starSymbolIndices = new ArrayList<>();
    for (int j = 0; j < charArray.length; j++) {
      char chr = charArray[j];
      if (!Character.isDigit(chr) && chr == STAR_SYMBOL) {
        starSymbolIndices.add(j);
      }
    }
    return starSymbolIndices;
  }
}

