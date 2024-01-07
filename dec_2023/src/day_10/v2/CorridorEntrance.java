package day_10.v2;

public class CorridorEntrance {

  private final Position entrance1;

  private final Position entrance2;

  public CorridorEntrance(Position entrance1, Position entrance2) {
    this.entrance1 = entrance1;
    this.entrance2 = entrance2;
  }

  public Position getEntrance1() {
    return entrance1;
  }

  public Position getEntrance2() {
    return entrance2;
  }

  @Override
  public String toString() {
    return "CorridorEntrance{" +
        "entrance1=" + entrance1 +
        ", entrance2=" + entrance2 +
        '}';
  }
}
