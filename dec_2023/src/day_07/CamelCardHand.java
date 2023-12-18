package day_07;

import java.util.HashMap;
import java.util.Map;

public class CamelCardHand implements Comparable<CamelCardHand> {

  private final String hand;

  private final int bid;

  private final CardType type;

  public CamelCardHand(String hand, int bid) {
    if (hand.length() != 5) {
      throw new RuntimeException("Hand must have exactly 5 cards");
    }
    this.hand = hand;
    this.bid = bid;
    this.type = identifyHandType(hand);
  }

  public CamelCardHand(String[] info){
    this(info[0], Integer.parseInt(info[1]));
  }

  public CamelCardHand(String line){
    this(line.split(" "));
  }

  protected CardType identifyHandType(String hand) {
    Map<Character, Integer> cardQuantities = getCardQuantities(hand);
    int size = cardQuantities.size();
    if (size == 1) {
      return CardType.FIVE_OF_A_KIND;
    }
    if (size == 4) {
      return CardType.ONE_PAIR;
    }
    if (size == 5) {
      return CardType.HIGH_CARD;
    }
    for (Map.Entry<Character, Integer> entry : cardQuantities.entrySet()) {
      Integer value = entry.getValue();
      if (size == 2) {
        if (value == 4 || value == 1) {
          return CardType.FOUR_OF_A_KIND;
        }
        if (value == 3 || value == 2) {
          return CardType.FULL_HOUSE;
        }
      } else {
        if (value == 3) {
          return CardType.THREE_OF_A_KIND;
        }
      }
    }
    return CardType.TWO_PAIR;
  }

  protected static Map<Character, Integer> getCardQuantities(String hand) {
    Map<Character, Integer> cardQuantities = new HashMap<>();
    String reducedHand = hand;
    char currentCard = ' ';
    int previousLength = 0;
    while (!reducedHand.isEmpty()) {
      previousLength = reducedHand.length();
      currentCard = reducedHand.charAt(0);
      reducedHand = reducedHand.replaceAll(String.valueOf(currentCard), "");
      cardQuantities.put(currentCard, previousLength - reducedHand.length());
    }
    return cardQuantities;
  }

  public String getHand() {
    return hand;
  }

  public CardType getType() {
    return type;
  }

  public int getBid() {
    return bid;
  }

  @Override
  public String toString() {
    return "CamelCardHand{" +
        "(hand, type, bid): (" + hand +
        ", " + type +
        ", " + bid +
        ")}";
  }

  @Override
  public int compareTo(CamelCardHand otherHand) {
    String cardStrength = "23456789TJQKA"; // increasing order of strength
    return compareToWithCardStrength(otherHand, cardStrength);
  }

  protected int compareToWithCardStrength(CamelCardHand otherHand, String cardStrength){
    int typeComparison = this.getType().compareTo(otherHand.getType());
    if (typeComparison != 0) {
      return typeComparison;
    }
    for (int i = 0; i < 5; i++) {
      Character thisCurrentChar = this.getHand().charAt(i);
      Character otherCurrentChar = otherHand.getHand().charAt(i);
      if (!thisCurrentChar.equals(otherCurrentChar)) {
        int thisCardStrength = cardStrength.indexOf(thisCurrentChar);
        int otherCardStrength = cardStrength.indexOf(otherCurrentChar);
        return thisCardStrength - otherCardStrength;
      }
    }
    return 0;
  }
}
