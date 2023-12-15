package day_04;

import util.Utilities;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ScratchCardWinner {

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_04/scratchCards.txt";
    List<String> documentContents = Utilities.getDocumentContentsIo1(docName);
//    String temp = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
//        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
//        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
//        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
//        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
//        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
//    documentContents = temp.lines().collect(Collectors.toList());
    formatDocumentLines(documentContents);

    puzzle1(documentContents);
    puzzle2(documentContents);

  }

  private static void puzzle2(List<String> documentContents) {
    Map<Integer, List<String>> matchingCards = getMatchingCards(documentContents);
    int[] cardInstances = new int[documentContents.size()];
    Arrays.fill(cardInstances, 1);
    int currentPosition = 0;
    for (List<String> handOfMatchingCards : matchingCards.values()) {
      currentPosition++;
      int numCopies = handOfMatchingCards.size();
      for (int i = currentPosition; i < currentPosition + numCopies; i++) {
        cardInstances[i] += cardInstances[currentPosition - 1];
      }
    }
    int totalScratchCardsWithCopies = Arrays.stream(cardInstances).reduce(0, Integer::sum);
//    System.out.println("Arrays.toString(cardInstances) = " + Arrays.toString(cardInstances));
    System.out.println("totalScratchCardsWithCopies = " + totalScratchCardsWithCopies); //8570000 --> correct
  }

  private static void puzzle1(List<String> documentContents) {
    Map<Integer, List<String>> matchingCards = getMatchingCards(documentContents);
    List<Integer> cardPoints = getCardPoints(matchingCards);
//    System.out.println("cardPoints = " + cardPoints);
    Integer sumOfCardPoints = cardPoints.stream().reduce(0, Integer::sum);
    System.out.println("sumOfCardPoints = " + sumOfCardPoints); //23847 --> correct
  }

  private static List<Integer> getCardPoints(Map<Integer, List<String>> matchingCards) {
    List<Integer> cardPoints = new ArrayList<>();
    for (List<String> handOfMatchingCards : matchingCards.values()) {
      int cardScore = 0;
      for (int i = 0; i < handOfMatchingCards.size(); i++) {
        cardScore = (cardScore == 0 ? 1 : cardScore * 2);
      }
      cardPoints.add(cardScore);
    }
    return cardPoints;
  }

  private static Map<Integer, List<String>> getMatchingCards(List<String> documentContents) {
    Map<Integer, List<String>> matchingCards = new HashMap<>();
    for (int i = 0; i < documentContents.size(); i++) {
      String line = documentContents.get(i);
      String[] numbers = line.split(" \\| ");
      List<String> winningCardNumbers = List.of(numbers[0].split(" "));
      List<String> myCardNumbers = List.of(numbers[1].split(" "));
      List<String> cardPoints = new ArrayList<>();
      for (String myCardNumber : myCardNumbers) {
        if (winningCardNumbers.contains(myCardNumber)) {
          cardPoints.add(myCardNumber);
        }
      }
      matchingCards.put(i + 1, cardPoints);
    }
    return matchingCards;
  }

  public static void formatDocumentLines(List<String> documentContents) {
    for (int i = 0; i < documentContents.size(); i++) {
      String line = documentContents.get(i);
      int colonIndex = line.indexOf(": ");
      line = line.substring(colonIndex + 1).trim().replaceAll(" {2}", " "); //regex hack
//      System.out.println(line);
      documentContents.set(i, line);
    }
  }
}
