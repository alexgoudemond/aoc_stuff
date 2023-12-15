package day_01;

import util.Utilities;

import java.util.*;

public class Trebuchet {

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_01/calibrationDocument.txt";
    puzzle1(docName);
    puzzle2(docName);
  }

  private static void puzzle1(String docName) {
    List<String> documentContents = Utilities.getDocumentContents(docName);
    List<Integer> calibrationValues = getCalibrationValues1(documentContents);
    Integer finalSum = calibrationValues.stream().reduce(0, Integer::sum);
    System.out.println("finalSum1 = " + finalSum); //54940 --> correct
  }

  private static void puzzle2(String docName) {
    List<String> documentContents = Utilities.getDocumentContents(docName);
//    documentContents = new ArrayList<>(
//        List.of("15nine1")
////        List.of("two1nine", "eightwothree", "abcone2threexyz", "xtwone3four",
////            "4nineeightseven2", "zoneight234", "7pqrstsixteen")
//    );
    List<Integer> calibrationValues = getCalibrationValues2(documentContents);
//    calibrationValues.forEach(System.out::println);
    Integer finalSum = calibrationValues.stream().reduce(0, Integer::sum);
    System.out.println("finalSum2 = " + finalSum); //54208 --> correct
  }

  private static List<Integer> getCalibrationValues2(List<String> documentContents) {
    List<Integer> calibrationValues = new ArrayList<>();
    for (String line : documentContents) {
      TreeMap<Integer, Integer> digitPositions = getDigitPositions(line);
      Integer firstDigit = digitPositions.firstEntry().getValue();
      Integer lastDigit = digitPositions.lastEntry().getValue();
      calibrationValues.add(Integer.parseInt("" + firstDigit + lastDigit));
    }
    return calibrationValues;
  }

  private static TreeMap<Integer, Integer> getDigitPositions(String line) {
    TreeMap<Integer, Integer> digitPosition = new TreeMap<>();
    for (Map.Entry<String, Integer> entry : getSpelledOutLetters().entrySet()) {
      if (line.contains(entry.getKey())) {
        digitPosition.put(line.indexOf(entry.getKey()), entry.getValue());
        digitPosition.put(line.lastIndexOf(entry.getKey()), entry.getValue());
      }
      if (line.contains(Integer.toString(entry.getValue()))) {
        digitPosition.put(line.indexOf(Integer.toString(entry.getValue())), entry.getValue());
        digitPosition.put(line.lastIndexOf(Integer.toString(entry.getValue())), entry.getValue());
      }
    }
    if (digitPosition.isEmpty()) {
      throw new RuntimeException("No Numbers in line:" + line);
    }
//    for (Map.Entry<Integer, Integer> entry : digitPosition.entrySet()) {
//      System.out.print("index: " + entry.getKey());
//      System.out.println(", value: " + entry.getValue());
//    }
    return digitPosition;
  }

  private static Map<String, Integer> getSpelledOutLetters() {
    Map<String, Integer> spelledOutLetters = new HashMap<>();
    spelledOutLetters.put("one", 1);
    spelledOutLetters.put("two", 2);
    spelledOutLetters.put("three", 3);
    spelledOutLetters.put("four", 4);
    spelledOutLetters.put("five", 5);
    spelledOutLetters.put("six", 6);
    spelledOutLetters.put("seven", 7);
    spelledOutLetters.put("eight", 8);
    spelledOutLetters.put("nine", 9);
    return spelledOutLetters;
  }

  private static List<Integer> getCalibrationValues1(List<String> documentContents) {
    List<Integer> calibrationValues = new ArrayList<>();
    for (String line : documentContents) {
      LinkedList<Character> digits = new LinkedList<>();
      for (char c : line.toCharArray()) {
        if (Character.isDigit(c)) {
          digits.add(c);
        }
      }
      if (digits.isEmpty()) {
        throw new RuntimeException("No Numbers in line:" + line);
      }
      calibrationValues.add(Integer.parseInt("" + digits.getFirst() + digits.getLast()));
    }
    return calibrationValues;
  }

}
