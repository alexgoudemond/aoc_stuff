package za.co.alexgoudemond.day_10.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Todo - see if corridor is working now - extend
 *  Stop and redo! cant get working
 *  */
public class Maze {

  private static final String OUT = "o";

  public static final String IN = "i";

  private String[][] grid;

  private final Position startingPosition;

  private final List<Position> connectedLoop;

  public Maze(String[][] grid) {
    this.grid = grid;
    this.startingPosition = findStartingPosition();
    this.connectedLoop = walkThroughLoop();
//    this.grid = getOnlyTheLoop();
  }

  private String[][] getSimplifiedLoopWithNestsAndCorridors() {
    String[][] paddedPipeLoop = getPaddedPipeLoop(getOnlyTheLoop());
    Position position;
    for (int i = 0; i < paddedPipeLoop.length; i++) {
      for (int j = 0; j < paddedPipeLoop[i].length; j++) {
        position = new Position(i, j);
        if (paddedPipeLoop[position.getX()][position.getY()].equals("*")
            && (paddedPipeLoop[position.up().getX()][position.up().getY()].equals(OUT)
            || paddedPipeLoop[position.down().getX()][position.down().getY()].equals(OUT)
            || paddedPipeLoop[position.left().getX()][position.left().getY()].equals(OUT)
            || paddedPipeLoop[position.right().getX()][position.right().getY()].equals(OUT))) {
          paddedPipeLoop[i][j] = OUT;
        }
      }
    }
    String[][] reducedLoop = new String[paddedPipeLoop.length - 2][paddedPipeLoop[0].length - 2];
    int l = 0, m = 0;
    for (int i = 1; i < paddedPipeLoop.length - 1; i++) {
      for (int j = 1; j < paddedPipeLoop[i].length - 1; j++) {
        reducedLoop[l][m++] = paddedPipeLoop[i][j];
      }
      l++;
      m = 0;
    }
    return reducedLoop;
  }

  // BUTT UGLY, but hope it works
  public void completelySimplifiedLoop() {
    String[][] simplifiedLoop = getSimplifiedLoopWithNestsAndCorridors();
    Position position;
    for (int i = 0; i < simplifiedLoop.length; i++) {
      for (int j = 0; j < simplifiedLoop[i].length; j++) {
        position = new Position(i, j);
        if (simplifiedLoop[i][j].equals("*")) {
          if (simplifiedLoop[position.up().getX()][position.up().getY()].equals(IN)
              || simplifiedLoop[position.down().getX()][position.down().getY()].equals(IN)
              || simplifiedLoop[position.left().getX()][position.left().getY()].equals(IN)
              || simplifiedLoop[position.right().getX()][position.right().getY()].equals(IN)) {
            simplifiedLoop[i][j] = IN;
          } else if (inLoop(position.up()) && isACorridor(position.up())
              || inLoop(position.down()) && isACorridor(position.down())
              || inLoop(position.left()) && isACorridor(position.left())
              || inLoop(position.right()) && isACorridor(position.right())) {
            if (inLoop(position.up()) && !isACorridor(position.up())
                || inLoop(position.down()) && !isACorridor(position.down())
                || inLoop(position.left()) && !isACorridor(position.left())
                || inLoop(position.right()) && !isACorridor(position.right())) {
              simplifiedLoop[i][j] = IN;
            } else {
              simplifiedLoop[i][j] = OUT;
            }
          } else {
            simplifiedLoop[i][j] = IN;
          }
        }
      }
    }
    display(simplifiedLoop);
  }

  private boolean isACorridor(Position position) {
    boolean[] validCorridorPositions = getValidCorridorPosition();
    int i = connectedLoop.indexOf(position);
    return validCorridorPositions[i];
  }

  private static String[][] getPaddedPipeLoop(String[][] pipeLoop) {
    String[][] paddedPipeLoop = new String[pipeLoop.length + 2][pipeLoop[0].length + 2];
    // Pad Top and Bottom
    for (int i = 0; i < paddedPipeLoop.length; i += paddedPipeLoop.length - 1) {
      Arrays.fill(paddedPipeLoop[i], OUT);
    }
    // Pad sides
    for (int i = 1; i < paddedPipeLoop.length - 1; i++) {
      paddedPipeLoop[i][0] = OUT;
      paddedPipeLoop[i][paddedPipeLoop[i].length - 1] = OUT;
    }
    //insert the normal pipeLoop
    int pipeI = 0;
    int pipeJ = 0;
    for (int i = 1; i < paddedPipeLoop.length - 1; i++) {
      for (int j = 1; j < paddedPipeLoop[i].length - 1; j++) {
        paddedPipeLoop[i][j] = pipeLoop[pipeI][pipeJ++];
      }
      pipeI++;
      pipeJ = 0;
    }
    return paddedPipeLoop;
  }

  private boolean[] getValidCorridorPosition() {
    boolean[] validCorridorPosition = new boolean[connectedLoop.size()];
    for (int i = 0; i < connectedLoop.size(); i++) {
      Position position = connectedLoop.get(i);
      if (isCorridor(position)) {
        CorridorEntrance corridorEntrance = getCorridorEntrance(position);
        int endIndex = connectedLoop.indexOf(corridorEntrance.getEntrance2());
        for (int j = i; j < endIndex; j++) {
          validCorridorPosition[j] = true;
        }
        System.out.println("Corridor @ position = " + corridorEntrance);
        i = endIndex;
      }
    }
    return validCorridorPosition;
  }

  public boolean isCorridor(Position position) {
    if (!isCorner(position)) {
      return false;
    }
    Position temp;
    temp = position.up();
    if (inLoop(temp) && notAdjacent(temp, position) && !unsafeToMove(position.up()) && !"F7LJ_|".contains(pipeAt(position.left()))) {
      return true;
    }
    temp = position.down();
    if (inLoop(temp) && notAdjacent(temp, position) && !unsafeToMove(position.up()) && !"F7LJ_|".contains(pipeAt(position.right()))) {
      return true;
    }
    temp = position.left();
    if (inLoop(temp) && notAdjacent(temp, position) && !unsafeToMove(position.up()) && !"F7LJ_|".contains(pipeAt(position.up()))) {
      return true;
    }
    temp = position.right();
    if (inLoop(temp) && notAdjacent(temp, position) && !unsafeToMove(position.up()) && !"F7LJ_|".contains(pipeAt(position.down()))) {
      return true;
    }
    return false;
  }

  public CorridorEntrance getCorridorEntrance(Position position) {
    CorridorEntrance corridorEntrance = null;
    if (!isCorridor(position)) {
      throw new RuntimeException(position + " is not a corridor!");
    }
    if (inLoop(position.up()) && notAdjacent(position.up(), position)) {
      corridorEntrance = new CorridorEntrance(position, position.up());
    } else if (inLoop(position.down()) && notAdjacent(position.down(), position)) {
      corridorEntrance = new CorridorEntrance(position, position.down());
    } else if (inLoop(position.left()) && notAdjacent(position.left(), position)) {
      corridorEntrance = new CorridorEntrance(position, position.left());
    } else {
      corridorEntrance = new CorridorEntrance(position, position.right());
    }
    return corridorEntrance;
  }

  private boolean notAdjacent(Position pos1, Position pos2) {
    if (notInLoop(pos1) || notInLoop(pos2)) {
      return true;
    }
    return Math.abs(connectedLoop.indexOf(pos1) - connectedLoop.indexOf(pos2)) != 1;
  }

  public boolean isCorner(Position position) {
    String pipe = pipeAt(position);
    return pipe.equals("F")
        || pipe.equals("7")
        || pipe.equals("L")
        || pipe.equals("J");
  }

  public void showOnlyTheLoop() {
    display(getOnlyTheLoop());
  }

  public String[][] getOnlyTheLoop() {
    String[][] simpleLoop = new String[grid.length][grid[0].length];
    Position current;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        current = new Position(i, j);
        if (connectedLoop.contains(current)) {
          simpleLoop[i][j] = grid[i][j];
        } else {
          simpleLoop[i][j] = "*";
        }
      }
    }
    return simpleLoop;
  }

  public int getConnectedLoopSize() {
    return connectedLoop.size();
  }

  private void display(String[][] simpleLoop) {
    for (String[] strings : simpleLoop) {
      for (String entry : strings) {
        System.out.print(entry + " ");
      }
      System.out.println();
    }
  }

  private List<Position> walkThroughLoop() {
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

  private Position findStartingPosition() {
    Position start = null;
    for (int i = 0; i < grid.length; i++) {
      String row = String.join("", grid[i]);
      if (row.contains("S")) {
        start = new Position(i, row.indexOf("S"));
      }
    }
    if (start == null) {
      throw new RuntimeException("Maze does not contain a starting pipe");
    }
    return start;
  }

  public Position next(Position position) {
    if (notInLoop(position)) {
      throw new RuntimeException("Position " + position + " is not in the connected loop");
    }
    return connectedLoop.get(connectedLoop.indexOf(position) + 1);
  }

  public Position previous(Position position) {
    if (notInLoop(position)) {
      throw new RuntimeException("Position " + position + " is not in the connected loop");
    }
    return connectedLoop.get(connectedLoop.indexOf(position) - 1);
  }

  private boolean notInLoop(Position position) {
    return !inLoop(position);
  }

  private boolean inLoop(Position position) {
    return connectedLoop.contains(position);
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
}
