package day_02;

import util.Utilities;

import java.util.*;

public class CubeConundrum {

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_02/gameHistory.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
    puzzle1(documentContents);
    puzzle2(documentContents);
  }

  private static void puzzle1(List<String> documentContents) {
    Set<Integer> impossibleGameIds = getImpossibleGameIds(documentContents);
    int sumOfIds = 0;
    for (int i = 1; i < documentContents.size(); i++) {
      if (!impossibleGameIds.contains(i)) {
        sumOfIds += i;
      }
    }
    System.out.println("sumOfIds = " + sumOfIds); //2406 --> Correct!
  }

  private static void puzzle2(List<String> documentContents) {
    Integer sumOfMinPowers = getMinPowers(documentContents).stream().reduce(0, Integer::sum);
    System.out.println("sumOfMinPowers = " + sumOfMinPowers); // 78375 --> Correct!
  }

  private static List<Integer> getMinPowers(List<String> documentContents) {
    List<Integer> minPowers = new ArrayList<>();
    for (String line : documentContents) {
      Map<String, Integer> minBagContents = getMinBagContents();
      for (String cubeSet : line.substring(line.indexOf(": ") + 1).split(";")) {
        for (String cubeSummary : cubeSet.trim().split(", ")) {
          String[] colouredCubes = cubeSummary.split(" ");
          if (Integer.parseInt(colouredCubes[0]) > minBagContents.get(colouredCubes[1])) {
            minBagContents.replace(colouredCubes[1], Integer.valueOf(colouredCubes[0]));
          }
        }
      }
      Integer minPower = minBagContents.values().stream().reduce(1, (a, b) -> a * b);
      minPowers.add(minPower);
//      for (Map.Entry<String, Integer> entry : minBagContents.entrySet()) {
//        System.out.print(" key = " + entry.getKey());
//        System.out.println(" value = " + entry.getValue());
//      }
//      System.out.println("minPower = " + minPower);
    }
    return minPowers;
  }

  private static Map<String, Integer> getMinBagContents() {
    Map<String, Integer> minBagContents = new HashMap<>();
    minBagContents.put("blue", 0);
    minBagContents.put("green", 0);
    minBagContents.put("red", 0);
    return minBagContents;
  }



  private static Set<Integer> getImpossibleGameIds(List<String> documentContents) {
    String[][] bagContents = new String[][] {{"12", "red"}, {"13", "green"}, {"14", "blue"}};
    int i = 0;
    Set<Integer> impossibleGameIds = new HashSet<>();
    for (String line : documentContents) {
      i++;
      for (String cubeSet : line.substring(line.indexOf(": ") + 1).split(";")) {
        for (String cubeSummary : cubeSet.trim().split(", ")) {
          String[] colouredCubes = cubeSummary.split(" ");
          //look at bag contents
          for (String[] bagColouredCubes : bagContents) {
            if (bagColouredCubes[1].equals(colouredCubes[1])) {
              if (Integer.parseInt(colouredCubes[0]) > Integer.parseInt(bagColouredCubes[0])) {
                impossibleGameIds.add(i);
                break;
              }
            }
          }
        }
      }
    }
    return impossibleGameIds;
  }
}
