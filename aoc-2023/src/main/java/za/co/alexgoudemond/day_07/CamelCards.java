package za.co.alexgoudemond.day_07;

import za.co.alexgoudemond.util.Utilities;

import java.util.*;

public class CamelCards {

  public static void main(String[] args) {
//    String docName = "dec_2023/resources/day_07/testHandSet.txt";
    String docName = "dec_2023/resources/day_07/handSet.txt";
//    String docName = "dec_2023/resources/day_07/debugging.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
//    System.out.println("documentContents = " + documentContents);
//    documentContents.forEach(System.out::println);
//    puzzle1(documentContents);
    puzzle2(documentContents);
  }

  private static void puzzle2(List<String> documentContents) {
    Map<CardType, List<CamelCardHand>> handsByStrength = getHandsByIncreasingStrengthWithJokers(documentContents);
    for (Map.Entry<CardType, List<CamelCardHand>> entry : handsByStrength.entrySet()) {
//      System.out.println("key: " + entry.getKey());
      entry.getValue().forEach(System.out::println);
    }
    int rankNumber = 0;
    long totalWinnings = 0;
    for (Map.Entry<CardType, List<CamelCardHand>> entry : handsByStrength.entrySet()) {
      for (CamelCardHand camelCardHand : entry.getValue()) {
        long winningScore = (long) camelCardHand.getBid() * ++rankNumber;
        totalWinnings += winningScore;
      }
    }
    System.out.println("totalWinnings = " + totalWinnings); // 245794069 --> correct!
  }

  private static void puzzle1(List<String> documentContents) {
    Map<CardType, List<CamelCardHand>> handsByStrength = getHandsByIncreasingStrength(documentContents);
    int rankNumber = 0;
    long totalWinnings = 0;
    for (Map.Entry<CardType, List<CamelCardHand>> entry : handsByStrength.entrySet()) {
      for (CamelCardHand camelCardHand : entry.getValue()) {
        long winningScore = (long) camelCardHand.getBid() * ++rankNumber;
        totalWinnings += winningScore;
      }
    }
    System.out.println("totalWinnings = " + totalWinnings); //246163188 --> correct!
  }

  private static Map<CardType, List<CamelCardHand>> getHandsByIncreasingStrengthWithJokers(List<String> documentContents) {
    Map<CardType, List<CamelCardHand>> handsByType = getHandsByType();
    for (String line : documentContents) {
      CamelCardHand hand = new CamelCardHandWithJokers(line);
      List<CamelCardHand> handsForType = handsByType.get(hand.getType());
      //no diff
      if (!handsForType.contains(hand)) {
        handsForType.add(hand);
      }
    }
    sortByIncreasingStrength(handsByType);
    return handsByType;
  }

  private static Map<CardType, List<CamelCardHand>> getHandsByIncreasingStrength(List<String> documentContents) {
    Map<CardType, List<CamelCardHand>> handsByType = getHandsByType();
    for (String line : documentContents) {
      CamelCardHand hand = new CamelCardHand(line);
      handsByType.get(hand.getType()).add(hand);
    }
    sortByIncreasingStrength(handsByType);
    return handsByType;
  }

  private static void sortByIncreasingStrength(Map<CardType, List<CamelCardHand>> handsByType) {
    for (Map.Entry<CardType, List<CamelCardHand>> entry : handsByType.entrySet()) {
      entry.getValue().sort(Comparator.naturalOrder());
    }
  }

  private static Map<CardType, List<CamelCardHand>> getHandsByType() {
    Map<CardType, List<CamelCardHand>> handsByIncreasingRank = new TreeMap<>();
    handsByIncreasingRank.put(CardType.HIGH_CARD, new ArrayList<>());
    handsByIncreasingRank.put(CardType.ONE_PAIR, new ArrayList<>());
    handsByIncreasingRank.put(CardType.TWO_PAIR, new ArrayList<>());
    handsByIncreasingRank.put(CardType.THREE_OF_A_KIND, new ArrayList<>());
    handsByIncreasingRank.put(CardType.FULL_HOUSE, new ArrayList<>());
    handsByIncreasingRank.put(CardType.FOUR_OF_A_KIND, new ArrayList<>());
    handsByIncreasingRank.put(CardType.FIVE_OF_A_KIND, new ArrayList<>());
    return handsByIncreasingRank;
  }

  private static void displayComparison(CamelCardHand handOne, CamelCardHand handTwo) {
    int comparison = handOne.compareTo(handTwo);
    if (comparison == 0) {
      System.out.println("Hands are equal");
    } else if (comparison > 0) {
      System.out.println(handOne + " > " + handTwo);
    } else {
      System.out.println(handTwo + " > " + handOne);
    }
  }
}
