package za.co.alexgoudemond.aoc.dec_2023.day_10.v1;


import za.co.alexgoudemond.aoc.dec_2023.util.Utilities;

import java.util.Arrays;
import java.util.List;

/*
Yoshailen and I solved puzzle 1 together live

For puzzle 2, I need to complete the grid like before, but this time I
need to conduct a check on all the pipes NOT in the main loop:
1) pad the grid with zeroes all around
Scan in each compass direction if I can squeeze through 2 pipes:
- Scanning North and South:
  - If all symbols above are 'J', '|', '7', 'O'
  - If all symbols above are 'L', '|', 'F', 'O'
  - Can squeeze from outside
- Scanning West and East:
  - If all symbols are '7', '-', 'F', 'O'
  - If all symbols are 'J', '-', 'L', 'O'
  - Can squeeze from outside
*/
public class Solution1V2 {

  public static final String OUT = "O";

  public static final String J = "J";

  public static final String VERTICAL_BAR = "|";

  public static final String SEVEN = "7";

  public static final String L = "L";

  public static final String F = "F";

  public static final String IN = "in";

  public static final String DASH = "-";

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_10/mixedPipeSqueeze.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
    char[][] grid = getGrid(documentContents);
    puzzle1(grid);
    String[][] pipeLoop = revealPipeLoop(grid, false);
    String[][] paddedPipeLoop = getPaddedPipeLoop(pipeLoop);
    fillMostOutsidePipes(paddedPipeLoop);
    findCorridorOpenings(paddedPipeLoop);
    floodFillCorridorOpenings(paddedPipeLoop);
    display(paddedPipeLoop);
    int numberOfNests = getNumBlanks(paddedPipeLoop);
    System.out.println("numberOfNests = " + numberOfNests);
  }

  private static void fillMostOutsidePipes(String[][] paddedPipeLoop) {
    for (int i = 1; i < paddedPipeLoop.length - 1; i++) {
      for (int j = 1; j < paddedPipeLoop[i].length - 1; j++) {
        if (isOutsideMainLoop(new Position(i, j), paddedPipeLoop)) {
          paddedPipeLoop[i][j] = OUT;
        }
      }
    }
  }

  private static void floodFillCorridorOpenings(String[][] paddedPipeLoop) {
    Position position;
    int currentNumBlanks= 0;
    int newNumBlanks = 0;
    do {
      currentNumBlanks = getNumBlanks(paddedPipeLoop);
      for (int i = 0; i < paddedPipeLoop.length; i++) {
        for (int j = 0; j < paddedPipeLoop[i].length; j++) {
          String element = paddedPipeLoop[i][j];
          if (element.isEmpty()) {
            position = new Position(i, j);
            if (hasOutsidePipeConnected(position, paddedPipeLoop)) {
              paddedPipeLoop[i][j] = OUT;
            }
          }
        }
      }
      newNumBlanks = getNumBlanks(paddedPipeLoop);
    } while (newNumBlanks != currentNumBlanks);
  }

  private static int getNumBlanks(String[][] paddedPipeLoop) {
    int numBlanks = 0;
    for (String[] strings : paddedPipeLoop) {
      for (String element : strings) {
        if (element.isEmpty()) {
          numBlanks++;
        }
      }
    }
    return numBlanks;
  }

  private static void findCorridorOpenings(String[][] paddedPipeLoop) {
    Position position;
    Position nextPosition;
    for (int i = 1; i < paddedPipeLoop.length - 1; i++) {
      for (int j = 1; j < paddedPipeLoop[i].length - 1; j++) {
        position = new Position(i, j);
        String element = paddedPipeLoop[i][j];
        if (isACorner(elementAt(position, paddedPipeLoop))
            && hasOutsidePipeConnected(position, paddedPipeLoop)
        ) {
          nextPosition = position;
          //todo - these need to turn direction also, incase corridor twists
          tryWalkUpCorridor(element, nextPosition, paddedPipeLoop);
          tryWalkDownCorridor(element, nextPosition, paddedPipeLoop);
          tryWalkLeftCorridor(element, nextPosition, paddedPipeLoop);
          tryWalkRightCorridor(element, nextPosition, paddedPipeLoop);
        }
      }
    }
  }

  private static void tryWalkRightCorridor(String element, Position nextPosition, String[][] paddedPipeLoop) {
    if (element.equals(L)) {
      nextPosition = nextPosition.right();
      while (elementAt(nextPosition, paddedPipeLoop).equals(DASH)
          || elementAt(nextPosition, paddedPipeLoop).equals(J)
      ) {
        nextPosition = nextPosition.right();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
    if (element.equals(F)) {
      nextPosition = nextPosition.right();
      while (elementAt(nextPosition, paddedPipeLoop).equals(DASH)
          || elementAt(nextPosition, paddedPipeLoop).equals(SEVEN)
      ) {
        nextPosition = nextPosition.right();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
  }

  private static void tryWalkLeftCorridor(String element, Position nextPosition, String[][] paddedPipeLoop) {
    if (element.equals(J)) {
      nextPosition = nextPosition.left();
      while (elementAt(nextPosition, paddedPipeLoop).equals(DASH)
          || elementAt(nextPosition, paddedPipeLoop).equals(L)
      ) {
        nextPosition = nextPosition.left();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
    if (element.equals(SEVEN)) {
      nextPosition = nextPosition.left();
      while (elementAt(nextPosition, paddedPipeLoop).equals(DASH)
          || elementAt(nextPosition, paddedPipeLoop).equals(F)
      ) {
        nextPosition = nextPosition.left();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
  }

  private static void tryWalkDownCorridor(String element, Position nextPosition, String[][] paddedPipeLoop) {
    if (element.equals(SEVEN)) {
      nextPosition = nextPosition.downwards();
      while (elementAt(nextPosition, paddedPipeLoop).equals(VERTICAL_BAR)
          || elementAt(nextPosition, paddedPipeLoop).equals(J)
      ) {
        nextPosition = nextPosition.downwards();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
    if (element.equals(F)) {
      nextPosition = nextPosition.downwards();
      while (elementAt(nextPosition, paddedPipeLoop).equals(VERTICAL_BAR)
          || elementAt(nextPosition, paddedPipeLoop).equals(L)
      ) {
        nextPosition = nextPosition.downwards();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
  }

  private static void tryWalkUpCorridor(String element, Position nextPosition, String[][] paddedPipeLoop) {
    if (element.equals(J)) {
      nextPosition = nextPosition.upwards();
      while (elementAt(nextPosition, paddedPipeLoop).equals(VERTICAL_BAR)
          || elementAt(nextPosition, paddedPipeLoop).equals(SEVEN)
      ) {
        nextPosition = nextPosition.upwards();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
    if (element.equals(L)) {
      nextPosition = nextPosition.upwards();
      while (elementAt(nextPosition, paddedPipeLoop).equals(VERTICAL_BAR)
          || elementAt(nextPosition, paddedPipeLoop).equals(F)
      ) {
        nextPosition = nextPosition.upwards();
      }
      if (elementAt(nextPosition, paddedPipeLoop).isEmpty()) {
        paddedPipeLoop[nextPosition.getX()][nextPosition.getY()] = OUT;
      }
    }
  }

  private static boolean isACorridor(Position position, String[][] grid) {
    if (!isACorner(elementAt(position, grid))) {
      return false;
    }
    int count = 0;
    if (elementAt(position.upwards(), grid).equals(OUT)) {
      count++;
    }
    if (elementAt(position.downwards(), grid).equals(OUT)) {
      count++;
    }
    if (elementAt(position.left(), grid).equals(OUT)) {
      count++;
    }
    if (elementAt(position.right(), grid).equals(OUT)) {
      count++;
    }
    return count == 1;
  }

  private static boolean isACorner(String element) {
    return element.equals(F)
        || element.equals(SEVEN)
        || element.equals(L)
        || element.equals(J);
  }

  private static boolean isOutsideMainLoop(Position position, String[][] paddedPipeLoop) {
    String elementAtPosition = elementAt(position, paddedPipeLoop);
    return elementAtPosition.isEmpty()
        && hasOutsidePipeConnected(position, paddedPipeLoop);
  }

  private static boolean hasOutsidePipeConnected(Position position, String[][] paddedPipeLoop) {
    return elementAt(position.upwards(), paddedPipeLoop).equals(OUT)
        || elementAt(position.downwards(), paddedPipeLoop).equals(OUT)
        || elementAt(position.left(), paddedPipeLoop).equals(OUT)
        || elementAt(position.right(), paddedPipeLoop).equals(OUT);
  }

  private static String elementAt(Position current, String[][] paddedPipeLoop) {
    return paddedPipeLoop[current.getX()][current.getY()];
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

  private static void display(String[][] pipeLoop) {
    int length = 5;
    for (String[] columnValues : pipeLoop) {
      for (String entry : columnValues) {
        System.out.printf("%1$" + length + "s", entry);
      }
      System.out.println();
    }
  }

  private static char[][] getGrid(List<String> documentContents) {
    return documentContents
        .stream()
        .map(String::toCharArray)
        .toArray(x -> new char[documentContents.size()][]);
  }

  // non loop pipes are empty strings
  private static String[][] revealPipeLoop(char[][] grid, boolean showCounter) {
    String[][] pipeLoop = new String[grid.length][grid[0].length];
    for (String[] strings : pipeLoop) {
      Arrays.fill(strings, "");
    }
    Position currentPosition = getStartingPosition(grid);
    int counter = 0;
    addStep(grid, pipeLoop, currentPosition, counter, showCounter);
    do {
//      System.out.println(currentPosition);
      if (canMoveUp(currentPosition, grid)) {
        while (canMoveUp(currentPosition, grid)) {
          currentPosition = currentPosition.upwards();
          addStep(grid, pipeLoop, currentPosition, counter, showCounter);
          counter++;
        }
      } else {
        while (canMoveDown(currentPosition, grid)) {
          currentPosition = currentPosition.downwards();
          addStep(grid, pipeLoop, currentPosition, counter, showCounter);
          counter++;
        }
      }
      if (getPipe(currentPosition, grid) == 'S') break;
      if (canMoveRight(currentPosition, grid)) {
        while ((canMoveRight(currentPosition, grid))) {
          currentPosition = currentPosition.right();
          addStep(grid, pipeLoop, currentPosition, counter, showCounter);
          counter++;
        }
      } else {
        while (canMoveLeft(currentPosition, grid)) {
          currentPosition = currentPosition.left();
          addStep(grid, pipeLoop, currentPosition, counter, showCounter);
          counter++;
        }
      }
    } while (getPipe(currentPosition, grid) != 'S');
    return pipeLoop;
  }

  private static void addStep(char[][] originalGrid, String[][] pipeLoop, Position currentPosition, int counter, boolean showCounter) {
    String result = "";
    if (showCounter) {
      result = Integer.toString(counter);
    } else {
      result = Character.toString(originalGrid[currentPosition.getX()][currentPosition.getY()]);
    }
    pipeLoop[currentPosition.getX()][currentPosition.getY()] = result;
  }

  private static void puzzle1(char[][] grid) {
    Position currentPosition = getStartingPosition(grid);
    int counter = 0;
    do {
//      System.out.println(currentPosition);
      if (canMoveUp(currentPosition, grid)) {
        while (canMoveUp(currentPosition, grid)) {
          currentPosition = currentPosition.upwards();
          counter++;
        }
      } else {
        while (canMoveDown(currentPosition, grid)) {
          currentPosition = currentPosition.downwards();
          counter++;
        }
      }
      if (getPipe(currentPosition, grid) == 'S') break;
      if (canMoveRight(currentPosition, grid)) {
        while ((canMoveRight(currentPosition, grid))) {
          currentPosition = currentPosition.right();
          counter++;
        }
      } else {
        while (canMoveLeft(currentPosition, grid)) {
          currentPosition = currentPosition.left();
          counter++;
        }
      }
//      if (getPipe(currentPosition, grid) == 'S') {
//        System.out.println("found it");
//      }
    } while (getPipe(currentPosition, grid) != 'S');
    System.out.println("Farthest position: " + (counter / 2)); // 6867 --> correct
  }

  private static boolean canMoveUp(Position currentPosition, char[][] grid) {
    if (!safeToMove(currentPosition.upwards(), grid)) return false;
    char currentPipe = getPipe(currentPosition, grid);
    char upPipe = getPipe(currentPosition.upwards(), grid);
    if (currentPipe != 'S' && currentPipe != 'L' && currentPipe != 'J' & currentPipe != '|') return false;
    if (upPipe != 'S' && upPipe != '7' && upPipe != 'F' && upPipe != '|') return false;
    return true;
  }

  private static boolean canMoveDown(Position currentPosition, char[][] grid) {
    if (!safeToMove(currentPosition.downwards(), grid)) return false;
    char currentPipe = getPipe(currentPosition, grid);
    char upPipe = getPipe(currentPosition.downwards(), grid);
    if (currentPipe != 'S' && currentPipe != '|' && currentPipe != '7' & currentPipe != 'F') return false;
    if (upPipe != 'S' && upPipe != 'J' && upPipe != 'L' && upPipe != '|') return false;
    return true;
  }

  private static boolean canMoveLeft(Position currentPosition, char[][] grid) {
    if (!safeToMove(currentPosition.left(), grid)) return false;
    char currentPipe = getPipe(currentPosition, grid);
    char upPipe = getPipe(currentPosition.left(), grid);
    if (currentPipe != 'S' && currentPipe != '-' && currentPipe != 'J' & currentPipe != '7') return false;
    if (upPipe != 'S' && upPipe != '-' && upPipe != 'F' && upPipe != 'L') return false;
    return true;
  }


  private static boolean canMoveRight(Position currentPosition, char[][] grid) {
    if (!safeToMove(currentPosition.right(), grid)) return false;
    char currentPipe = getPipe(currentPosition, grid);
    char upPipe = getPipe(currentPosition.right(), grid);
    if (currentPipe != 'S' && currentPipe != '-' && currentPipe != 'F' & currentPipe != 'L') return false;
    if (upPipe != 'S' && upPipe != '-' && upPipe != 'J' && upPipe != '7') return false;
    return true;
  }


  private static char getPipe(Position currentPosition, char[][] grid) {
    return grid[currentPosition.getX()][currentPosition.getY()];
  }

  private static boolean safeToMove(Position position, char[][] grid) {
    if (position.getX() < 0 || position.getY() < 0) return false;
    if (position.getX() >= grid.length) return false;
    if (position.getY() >= grid[position.getX()].length) return false;
    return true;
  }

  private static Position getStartingPosition(char[][] grid) {
    int[] startingPosition = new int[2];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == 'S') {
          startingPosition[0] = i;
          startingPosition[1] = j;
          i = grid.length - 1;
          j = grid[i].length;
        }
      }
    }
    return new Position(startingPosition[0], startingPosition[1]);
  }
}
