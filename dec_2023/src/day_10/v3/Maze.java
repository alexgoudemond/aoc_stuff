package day_10.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
Not working - Try expanding the map to expose corridors...
* */
public class Maze {

  private static final String STAR = "*";

  private static final String OUT = "O";

  private static final String IN = ".";

  private final String[][] grid;

  private Position startingPosition;

  private final List<Position> connectedLoop;

  public Maze(String[][] grid) {
    this.grid = pad(grid);
    this.startingPosition = findStartingPosition();
    this.connectedLoop = findConnectedLoop();
    simplifyGrid();
    fillOutsideLoop();
//    display(this.grid);
    isolateNests();
    display(this.grid);
  }

  public String[][] getGrid() {
    return this.grid;
  }

  /*
  Credit to AOC Reddit user 3saster for pointing me in this direction
  */
  public int numNestsPicksTheorem() {
    int numNests = 0;
    // get area using Shoelace Algorithm
    int area = 0;
    Position previous, current;
    for (int i = 1; i < this.connectedLoop.size(); i++) {
      if (i != connectedLoop.size() - 1) {
        previous = connectedLoop.get(i - 1);
        current = connectedLoop.get(i);
      } else {
        previous = connectedLoop.get(i);
        current = connectedLoop.get(0);
      }
      area += (previous.getX() * current.getY()) - (current.getX() * previous.getY());
    }
    // get number nests using Picks Theorem
    numNests = area / 2 - (connectedLoop.size() / 2) + 1;
    return numNests;
  }

  private void fillOutsideLoop() {
    fillBorder();
    int increasingCounter = 1;
    int decreasingCounter = grid.length - 2;
    do {
      for (int j = 1; j < grid[0].length - 1; j++) {
        if (grid[increasingCounter][j].equals(STAR)
            && (grid[increasingCounter - 1][j].equals(OUT)
            || grid[increasingCounter][j - 1].equals(OUT)
            || grid[increasingCounter][j + 1].equals(OUT))) {
          grid[increasingCounter][j] = OUT;
        }
        if (grid[decreasingCounter][j].equals(STAR)
            && (grid[decreasingCounter + 1][j].equals(OUT)
            || grid[decreasingCounter][j - 1].equals(OUT)
            || grid[decreasingCounter][j + 1].equals(OUT))) {
          grid[decreasingCounter][j] = OUT;
        }
      }
      increasingCounter++;
      decreasingCounter--;
    } while (increasingCounter <= decreasingCounter);

    //repeat in other direction
    increasingCounter = 1;
    decreasingCounter = grid.length - 2;
    do {
      for (int j = grid[0].length - 1; j >= 1; j--) {
        if (grid[increasingCounter][j].equals(STAR)
            && (grid[increasingCounter - 1][j].equals(OUT)
            || grid[increasingCounter][j - 1].equals(OUT)
            || grid[increasingCounter][j + 1].equals(OUT))) {
          grid[increasingCounter][j] = OUT;
        }
        if (grid[decreasingCounter][j].equals(STAR)
            && (grid[decreasingCounter + 1][j].equals(OUT)
            || grid[decreasingCounter][j - 1].equals(OUT)
            || grid[decreasingCounter][j + 1].equals(OUT))) {
          grid[decreasingCounter][j] = OUT;
        }
      }
      increasingCounter++;
      decreasingCounter--;
    } while (increasingCounter <= decreasingCounter);
  }

  public int getNumberOfNests() {
    int count = 0;
    for (String[] strings : grid) {
      for (String entry : strings) {
        if (entry.equals(IN)) {
          count++;
        }
      }
    }
    return count;
  }

  private void simplifyGrid() {
    Position position;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        position = new Position(i, j);
        if (!connectedLoop.contains(position)) {
          grid[i][j] = STAR;
        }
      }
    }
    Position before, after;
    before = connectedLoop.get(connectedLoop.size() - 2);
    after = connectedLoop.get(1);
    String replacementSymbol = "";
    if (before.symbol().equals("-") && after.symbol().equals("-")) {
      replacementSymbol = "-";
    } else if (before.symbol().equals("|") && after.symbol().equals("|")) {
      replacementSymbol = "|";
    } else if (before.isCorner()) {
      // todo only coding what I need, as I could not be bothered anymore
      if (after.symbol().equals("|")) {
        switch (before.symbol()) {
          case "J":
            replacementSymbol = "L";
            break;
          case "7":
            replacementSymbol = "F";
            break;
          case "L":
            replacementSymbol = "J";
            break;
          case "F":
            replacementSymbol = "7";
            break;
        }
      }
    }
    //todo - code for more conditions
    grid[startingPosition.getX()][startingPosition.getY()] = replacementSymbol;
  }

  public int farthestDistanceInLoop() {
    return connectedLoop.size() / 2;
  }

  private void fillBorder() {
    int increasingCounter = 0;
    int decreasingCounter = grid.length - 1;
    do {
      for (int j = 0; j < grid[0].length; j++) {
        if (increasingCounter == 0) {
          grid[increasingCounter][j] = OUT;
        } else if (j == 0 || j == grid[0].length - 1) {
          grid[increasingCounter][j] = OUT;
        }
        if (decreasingCounter == grid.length - 1) {
          grid[decreasingCounter][j] = OUT;
        } else if (j == 0 || j == grid[0].length - 1) {
          grid[decreasingCounter][j] = OUT;
        }
      }
      increasingCounter++;
      decreasingCounter--;
    } while (increasingCounter <= decreasingCounter);
  }

  //todo - debug will all points and confirm
  private void isolateNests() {
    List<Position> innerPipes = getInnerPipePositions();
    int numInnerNests = 0;
    for (Position innerPipe : innerPipes) {
      if (getUpHorizontalPipeCount(innerPipe) % 2 == 1
          || getRightVerticalPipeCount(innerPipe) % 2 == 1
          || getDownHorizontalPipeCount(innerPipe) % 2 == 1
          || getLeftVerticalPipeCount(innerPipe) % 2 == 1) {
        grid[innerPipe.getX()][innerPipe.getY()] = IN;
        numInnerNests++;
      } else {
        grid[innerPipe.getX()][innerPipe.getY()] = OUT;
      }
    }

    //flood fill other symbols
    for (int i = innerPipes.size() - 1; i >= 0; i--) {
      Position position = innerPipes.get(i);
      if (position.right().symbol().equals(IN)
          || position.down().symbol().equals(IN)) {
        grid[position.getX()][position.getY()] = IN;
      }
    }
    for (Position position : innerPipes) {
      if (position.left().symbol().equals(IN)
          || position.up().symbol().equals(IN)) {
        grid[position.getX()][position.getY()] = IN;
      }
    }

    System.out.println("numInnerNests = " + numInnerNests);
  }

  private static int getUpHorizontalPipeCount(Position temp) {
    int upHorizontalPipeCount = 0;
    Position referenceCorner = temp;
    doWhileBlock:
    do {
      temp = temp.up();
      switch (temp.symbol()) {
        case "J":
        case "L":
          referenceCorner = temp;
          upHorizontalPipeCount++;
          break;
        case "F":
        case "7":
          if ((referenceCorner.symbol().equals("L") && temp.symbol().equals("F"))
              || (referenceCorner.symbol().equals("J") && temp.symbol().equals("7"))) {
            upHorizontalPipeCount++;
          }
          break;
        case "-":
          upHorizontalPipeCount++;
          break;
        case OUT:
          break doWhileBlock;
      }
    } while (temp.canMoveUp());
    return upHorizontalPipeCount;

//    do {
//      temp = temp.up();
//      if (!temp.symbol().equals("|")
//          && !temp.symbol().equals(IN)
//          && !temp.symbol().equals(OUT)
//          && !temp.symbol().equals(STAR)) {
//        upHorizontalPipeCount++;
//      }
//    } while (temp.canMoveUp());
//    return upHorizontalPipeCount;
  }

  private static int getDownHorizontalPipeCount(Position temp) {
    int downHorizontalPipeCount = 0;
    Position referenceCorner = temp;
    doWhileBlock:
    do {
      temp = temp.down();
      switch (temp.symbol()) {
        case "F":
        case "7":
          referenceCorner = temp;
          downHorizontalPipeCount++;
          break;
        case "L":
        case "J":
          if ((referenceCorner.symbol().equals("F") && temp.symbol().equals("L"))
              || (referenceCorner.symbol().equals("7") && temp.symbol().equals("J"))) {
            downHorizontalPipeCount++;
          }
          break;
        case "-":
          downHorizontalPipeCount++;
          break;
        case OUT:
          break doWhileBlock;
      }
    } while (temp.canMoveDown());
    return downHorizontalPipeCount;

//    do {
//      temp = temp.down();
//      if (!temp.symbol().equals("|")
//          && !temp.symbol().equals(IN)
//          && !temp.symbol().equals(OUT)
//          && !temp.symbol().equals(STAR)) {
//        downHorizontalPipeCount++;
//      }
//    } while (temp.canMoveDown());
//    return downHorizontalPipeCount;
  }

  private static int getLeftVerticalPipeCount(Position temp) {
    int leftVerticalPipeCount = 0;
    Position referenceCorner = temp;
    doWhileBlock:
    do {
      temp = temp.left();
      switch (temp.symbol()) {
        case "J":
        case "7":
          referenceCorner = temp;
          leftVerticalPipeCount++;
          break;
        case "L":
        case "F":
          if ((referenceCorner.symbol().equals("J") && temp.symbol().equals("L"))
              || (referenceCorner.symbol().equals("7") && temp.symbol().equals("F"))) {
            leftVerticalPipeCount++;
          }
          break;
        case "|":
          leftVerticalPipeCount++;
          break;
        case OUT:
          break doWhileBlock;
      }
    } while (temp.canMoveLeft());
    return leftVerticalPipeCount;


//    do {
//      temp = temp.left();
//      if (!temp.symbol().equals("-")
//          && !temp.symbol().equals(IN)
//          && !temp.symbol().equals(OUT)
//          && !temp.symbol().equals(STAR)) {
//        leftVerticalPipeCount++;
//      }
//    } while (temp.canMoveLeft());
//    return leftVerticalPipeCount;
  }

  private static int getRightVerticalPipeCount(Position temp) {
    int rightVerticalPipeCount = 0;
    Position referenceCorner = temp;
    doWhileBlock:
    do {
      temp = temp.right();
      switch (temp.symbol()) {
        case "L":
        case "F":
          referenceCorner = temp;
          rightVerticalPipeCount++;
          break;
        case "J":
        case "7":
          if ((referenceCorner.symbol().equals("L") && temp.symbol().equals("J"))
              || (referenceCorner.symbol().equals("F") && temp.symbol().equals("7"))) {
            rightVerticalPipeCount++;
          }
          break;
        case "|":
          rightVerticalPipeCount++;
          break;
        case OUT:
          break doWhileBlock;
      }
    } while (temp.canMoveRight());
    return rightVerticalPipeCount;

//    do {
//      temp = temp.right();
//      if (!temp.symbol().equals("-")
//          && !temp.symbol().equals(IN)
//          && !temp.symbol().equals(OUT)
//          && !temp.symbol().equals(STAR)) {
//        rightVerticalPipeCount++;
//      }
//    } while (temp.canMoveRight());
//    return rightVerticalPipeCount;
  }

  private List<Position> getInnerPipePositions() {
    List<Position> innerPipes = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].equals(STAR)) {
          innerPipes.add(new Position(i, j));
        }
      }
    }
    return innerPipes;
  }

  private List<Position> findConnectedLoop() {
    List<Position> loop = new ArrayList<>();
    Position currentPosition = startingPosition;
    loop.add(currentPosition);
    do {
      if (canMoveUp(currentPosition)) {
        while (canMoveUp(currentPosition)) {
          currentPosition = currentPosition.up();
          loop.add(currentPosition);
        }
      } else {
        while (canMoveDown(currentPosition)) {
          currentPosition = currentPosition.down();
          loop.add(currentPosition);
        }
      }
      if (pipeAt(currentPosition).equals("S")) break;
      if (canMoveRight(currentPosition)) {
        while ((canMoveRight(currentPosition))) {
          currentPosition = currentPosition.right();
          loop.add(currentPosition);
        }
      } else {
        while (canMoveLeft(currentPosition)) {
          currentPosition = currentPosition.left();
          loop.add(currentPosition);
        }
      }
    } while (!pipeAt(currentPosition).equals("S"));
    return loop;
  }

  private boolean canMoveUp(Position currentPosition) {
    if (unsafeToMove(currentPosition.up())) return false;
    String currentPipe = pipeAt(currentPosition);
    String upPipe = pipeAt(currentPosition.up());
    if (notSv7F(upPipe)) return false;
    if (notSvLJ(currentPipe)) return false;
    return true;
  }

  private boolean canMoveDown(Position currentPosition) {
    if (unsafeToMove(currentPosition.down())) return false;
    String currentPipe = pipeAt(currentPosition);
    String upPipe = pipeAt(currentPosition.down());
    if (notSv7F(currentPipe)) return false;
    if (notSvLJ(upPipe)) return false;
    return true;
  }

  private static boolean notSv7F(String currentPipe) {
    return !currentPipe.equals("S") && !currentPipe.equals("|") && !currentPipe.equals("7") & !currentPipe.equals("F");
  }

  private static boolean notSvLJ(String upPipe) {
    return !upPipe.equals("S") && !upPipe.equals("|") && !upPipe.equals("L") && !upPipe.equals("J");
  }

  private boolean canMoveLeft(Position currentPosition) {
    if (unsafeToMove(currentPosition.left())) return false;
    String currentPipe = pipeAt(currentPosition);
    String upPipe = pipeAt(currentPosition.left());
    if (notSdJ7(currentPipe)) return false;
    if (notSdFL(upPipe)) return false;
    return true;
  }


  private boolean canMoveRight(Position currentPosition) {
    if (unsafeToMove(currentPosition.right())) return false;
    String currentPipe = pipeAt(currentPosition);
    String upPipe = pipeAt(currentPosition.right());
    if (notSdJ7(upPipe)) return false;
    if (notSdFL(currentPipe)) return false;
    return true;
  }

  private static boolean notSdJ7(String upPipe) {
    return !upPipe.equals("S") && !upPipe.equals("-") && !upPipe.equals("J") && !upPipe.equals("7");
  }

  private static boolean notSdFL(String currentPipe) {
    return !currentPipe.equals("S") && !currentPipe.equals("-") && !currentPipe.equals("F") && !currentPipe.equals("L");
  }

  private String pipeAt(Position currentPosition) {
    return grid[currentPosition.getX()][currentPosition.getY()];
  }

  private boolean unsafeToMove(Position position) {
    if (position.getX() < 0 || position.getY() < 0) return true;
    if (position.getX() >= grid.length) return true;
    if (position.getY() >= grid[position.getX()].length) return true;
    return false;
  }

  private Position findStartingPosition() {
    Position start = null;
    for (int i = 0; i < grid.length; i++) {
      String row = String.join("", grid[i]);
      if (row.contains("S")) {
        start = new Position(i, row.indexOf("S"));
        break;
      }
    }
    if (start == null) {
      throw new RuntimeException("Maze does not contain a starting pipe");
    }
    return start;
  }

  private void display(String[][] grid) {
    for (String[] row : grid) {
      for (String entry : row) {
        System.out.print(entry + "");
      }
      System.out.println();
    }
  }

  private String[][] pad(String[][] grid) {
    String[][] paddedGrid = new String[grid.length + 2][];
    String repeat = STAR.repeat(grid[0].length + 2);
    for (int i = 0; i < paddedGrid.length; i++) {
      paddedGrid[i] = repeat.split("");
    }
    int x = 1, y = 1;
    for (String[] row : grid) {
      for (String entry : row) {
        paddedGrid[x][y++] = entry;
      }
      x++;
      y = 1;
    }
    return paddedGrid;
  }

  private class Position {

    private final int x;

    private final int y;

    public Position(int x, int y) {
      validate(x, y);
      this.x = x;
      this.y = y;
    }

    public Position(Position position) {
      this(position.getX(), position.getY());
    }

    public boolean isCorner() {
      return this.symbol().equals("F")
          || this.symbol().equals("7")
          || this.symbol().equals("L")
          || this.symbol().equals("J");
    }

    private void validate(int x, int y) {
      if (x < 0) {
        throw new RuntimeException("Negative value found for 'x': " + x);
      }
      if (y < 0) {
        throw new RuntimeException("Negative value found for 'y': " + y);
      }
      if (x >= grid.length) {
        throw new RuntimeException("Value for 'x' is too large: " + x);
      }
      if (y >= grid[x].length) {
        throw new RuntimeException("Value for 'y' is too large: " + y);
      }
    }

    public Position up() {
      if (canMoveUp()) {
        return new Position(x - 1, y);
      }
      throw new RuntimeException("Unable to move up: " + this);
    }

    public Position down() {
      if (canMoveDown()) {
        return new Position(x + 1, y);
      }
      throw new RuntimeException("Unable to move down: " + this);
    }

    public Position left() {
      if (canMoveLeft()) {
        return new Position(x, y - 1);
      }
      throw new RuntimeException("Unable to move left: " + this);
    }

    public Position right() {
      if (canMoveRight()) {
        return new Position(x, y + 1);
      }
      throw new RuntimeException("Unable to move right: " + this);
    }

    public boolean canMoveUp() {
      return x > 0;
    }

    public boolean canMoveDown() {
      return x < grid.length - 1;
    }

    public boolean canMoveLeft() {
      return y > 0;
    }

    public boolean canMoveRight() {
      return y < grid[x].length - 1;
    }

    public String symbol() {
      return grid[x][y];
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public String toString() {
      return "(x, y) = " +
          "(" + x +
          ", " + y +
          ')';
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
}

