package aoc.dec_2024.helper;

import java.util.Objects;

public class Coordinate {

    private int x;

    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate dummyCoordinate() {
        return new Coordinate(-1, -1);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
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
