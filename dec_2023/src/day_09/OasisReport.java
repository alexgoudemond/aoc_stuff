package day_09;

import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OasisReport {

  public static void main(String[] args) {
//    String docName = "dec_2023/resources/day_09/testReport.txt";
    String docName = "dec_2023/resources/day_09/report.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
//    documentContents.forEach(System.out::println);
    puzzle1(documentContents);
    puzzle2(documentContents);
  }

  private static void puzzle2(List<String> documentContents) {
    List<Integer> predictions = getBwdPredictions(documentContents);
//    System.out.println("predictions = " + predictions);
    Integer addedPredictions = predictions.stream().reduce(0, Integer::sum);
    System.out.println("addedBwdPredictions = " + addedPredictions); //1100 --> correct
  }

  private static void puzzle1(List<String> documentContents) {
    List<Integer> predictions = getFwdPredictions(documentContents);
//    System.out.println("predictions = " + predictions);
    Integer addedPredictions = predictions.stream().reduce(0, Integer::sum);
    System.out.println("addedFwdPredictions = " + addedPredictions); // 1898776583 --> correct
  }

  private static List<Integer> getBwdPredictions(List<String> documentContents) {
    List<Integer> predictions = new ArrayList<>();
    for (String line : documentContents) {
      String[] pattern = line.split(" ");
      int[] array = strArrayToInt(pattern);
      List<List<Integer>> allDifferences = getDifferences(array);
      int nextNum = 0;
      for (int j = allDifferences.size() - 2; j >= 0; j--) {
        List<Integer> differences = allDifferences.get(j);
        nextNum = differences.get(0) - nextNum;
      }
      int prediction = array[0] - nextNum;
      predictions.add(prediction);
    }
    return predictions;
  }

  private static List<Integer> getFwdPredictions(List<String> documentContents) {
    List<Integer> predictions = new ArrayList<>();
    for (String line : documentContents) {
      String[] pattern = line.split(" ");
      int[] array = strArrayToInt(pattern);
      List<List<Integer>> allDifferences = getDifferences(array);
      int nextNum = 0;
      for (int j = allDifferences.size() - 2; j >= 0; j--) {
        List<Integer> differences = allDifferences.get(j);
        nextNum = nextNum + differences.get(differences.size() - 1);
      }
      int prediction = array[array.length - 1] + nextNum;
      predictions.add(prediction);
    }
    return predictions;
  }

  private static List<List<Integer>> getDifferences(int[] array) {
    int difference;
    List<List<Integer>> differences = new ArrayList<>();
    List<Integer> temp;
    boolean patternDifferenceIsZero = false;
    while (!patternDifferenceIsZero) {
      temp = new ArrayList<>();
      patternDifferenceIsZero = true;
      for (int i = 1; i < array.length; i++) {
        difference = array[i] - array[i - 1];
        temp.add(difference);
        if (difference != 0) {
          patternDifferenceIsZero = false;
        }
      }
      if (!patternDifferenceIsZero) {
        array = temp.stream().mapToInt(Integer::valueOf).toArray();
      }
      differences.add(temp);
    }
    return differences;
  }

  private static int[] strArrayToInt(String[] pattern) {
    return Arrays.stream(pattern).mapToInt(Integer::parseInt).toArray();
  }

}
