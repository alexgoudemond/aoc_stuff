package za.co.alexgoudemond.day_10.v2;

import java.util.Objects;

public class Position {

  private final int x;

  private final int y;

  private boolean isCorridor;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Position up() {
    return new Position(x - 1, y);
  }

  public Position down() {
    return new Position(x + 1, y);
  }

  public Position left() {
    return new Position(x, y - 1);
  }

  public Position right() {
    return new Position(x, y + 1);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
