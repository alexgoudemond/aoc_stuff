package za.co.alexgoudemond.aoc.dec_2023.day_10.v1;

//todo
public class Position {

  private final int x;

  private final int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Position(Position position) {
    this(position.getX(), position.getY());
  }

  public Position coOrds() {
    return new Position(this);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Position upwards() {
    return new Position(getX() - 1, getY());
  }

  public Position downwards() {
    return new Position(getX() + 1, getY());
  }

  public Position left() {
    return new Position(getX(), getY() - 1);
  }

  public Position right() {
    return new Position(getX(), getY() + 1);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
