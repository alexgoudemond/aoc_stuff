package aoc.dec_2024.helper;

public class Coordinate {

    private int x;

    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Coordinate right() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate left() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate down() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate up() {
        return new Coordinate(x - 1, y);
    }
}
