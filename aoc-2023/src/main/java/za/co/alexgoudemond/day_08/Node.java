package za.co.alexgoudemond.day_08;

import java.util.Arrays;
import java.util.Objects;

public class Node {

  public static final char R_SYMBOL = 'R';

  public static final char L_SYMBOL = 'L';

  private final String source;

  private final String[] destinations;

  public Node(String input) {
    String formattedString = input.replaceAll(" ", "").replace("(", "").replace(")", "");
    String[] instructions = formattedString.split("=");
    this.source = instructions[0];
    this.destinations = instructions[1].split(",");
  }

  public String getSource() {
    return source;
  }

  public String[] getDestinations() {
    return destinations;
  }

  public boolean sourceEndsWithA() {
    return endsWith(source, 'A');
  }

  public boolean sourceEndsWithZ() {
    return endsWith(source, 'Z');
  }

  private boolean endsWith(String item, char desiredEnding) {
    return item.charAt(item.length() - 1) == desiredEnding;
  }

  public boolean mapEndsWithZ(char direction) {
    String map = map(direction);
    return endsWith(map, 'Z');
  }

  public String map(char direction) {
    if (direction != L_SYMBOL && direction != R_SYMBOL) {
      throw new RuntimeException("Invalid direction given. Only \'L\' and \'R\' are allowed");
    }
    if (direction == L_SYMBOL) {
      return destinations[0];
    }
    return destinations[1];
  }

  public boolean isEnd() {
    return this.source.equals("ZZZ");
  }

  @Override
  public String toString() {
    return source + " = " + Arrays.toString(destinations);
  }

}
