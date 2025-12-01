package za.co.alexgoudemond.aoc.dec_2023.day_07;

import java.util.Map;

public class CamelCardHandWithJokers extends CamelCardHand {

  public static final char J_SYMBOL = 'J';

  public CamelCardHandWithJokers(String hand, int bid) {
    super(hand, bid);
  }

  public CamelCardHandWithJokers(String[] info) {
    super(info);
  }

  public CamelCardHandWithJokers(String line) {
    super(line);
  }

  //todo - build differently, replace jokers with character of largest frequency
  @Override
  protected CardType identifyHandType(String hand) {
    Map<Character, Integer> cardQuantities = getCardQuantities(hand);
    int size = cardQuantities.size();
    if (size == 1) {
//      if (cardQuantities.containsKey('J')) {
//        throw new RuntimeException("Hand Cannot be all jokers");
//      }
      return CardType.FIVE_OF_A_KIND;
    }
    if (size == 4) {
      if (cardQuantities.containsKey('J')) {
        return CardType.THREE_OF_A_KIND;
      }
      return CardType.ONE_PAIR;
    }
    if (size == 5) {
      if (cardQuantities.containsKey('J')) {
        return CardType.ONE_PAIR; // legend Yoshailen helped me here
      }
      return CardType.HIGH_CARD;
    }
    for (Map.Entry<Character, Integer> entry : cardQuantities.entrySet()) {
      Integer value = entry.getValue();
      if (size == 2) {
        if (cardQuantities.containsKey('J')) {
          return CardType.FIVE_OF_A_KIND;
        }
        if (value == 4 || value == 1) {
          return CardType.FOUR_OF_A_KIND;
        }
        if (value == 3 || value == 2) {
          return CardType.FULL_HOUSE;
        }
      } else {
        //size == 3
        if (value == 3) {
          if (cardQuantities.containsKey(J_SYMBOL)) {
            return CardType.FOUR_OF_A_KIND;
          }
          return CardType.THREE_OF_A_KIND;
        } else if (value == 2) {
          if (entry.getKey().equals(J_SYMBOL)) {
            return CardType.FOUR_OF_A_KIND;
          }
        }
      }
    }
    if (cardQuantities.containsKey(J_SYMBOL)) {
      return CardType.FULL_HOUSE;
    }
    return CardType.TWO_PAIR;
  }

  @Override
  public int compareTo(CamelCardHand otherHand) {
    String cardStrength = "J23456789TQKA"; // increasing order of strength with Joker
    return compareToWithCardStrength(otherHand, cardStrength);
  }
}
