package za.co.alexgoudemond.day_10.v1;

import za.co.alexgoudemond.util.Utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Bad attempt - ignore. Refer to Solution1V2
* */
public class PipeMaze {

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_10/squareMazeSimple.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
//    documentContents.forEach(System.out::println);

    /*
      Rules:
        | is a vertical pipe connecting north and south.
        - is a horizontal pipe connecting east and west.
        L is a 90-degree bend connecting north and east.
        J is a 90-degree bend connecting north and west.
        7 is a 90-degree bend connecting south and west.
        F is a 90-degree bend connecting south and east.
        . is ground; there is no pipe in this tile.
        S is the starting position.

      Algorithm:
      1) find 'S'
      2) move clockwise, to complete the loop of the pipe
         - Write this to a file, to aid in debugging
      3) Move in 2 different directions, until hit farthest point from start
    */

    String[][] maze = getMaze(documentContents);
    display(maze);
    Map<String, String> pipeSymbols = getPipeSymbols();
    int[] startingPosition = getStartingPosition(documentContents);
    System.out.println(Arrays.toString(startingPosition));
    int[] nextPosition = startingPosition;
    do {
      nextPosition = getNextPosition(nextPosition, maze);
      System.out.println("Arrays.toString(nextPosition) = " + Arrays.toString(nextPosition));
    } while (!equal(nextPosition, startingPosition));
  }

  private static boolean equal(int[] nextPosition, int[] startingPosition) {
    return nextPosition[0] == startingPosition[0] &&
        nextPosition[1] == startingPosition[1];
  }

  // to complicated...
  private static int[] getNextPosition(int[] startingPosition, String[][] maze) {
    int sX = startingPosition[0];
    int sY = startingPosition[1];
    int[] nextPosition = new int[] {sX, sY};
    String symbolAbove = maze[sX - 1][sY];
    String symbolDown = maze[sX + 1][sY];
    String symbolLeft = maze[sX][sY - 1];
    String symbolRight = maze[sX][sY + 1];
    while (symbolAbove.equals("|") ||
        symbolAbove.equals("7") ||
        symbolAbove.equals("F")
    ) {
      symbolAbove = maze[--nextPosition[0]][sY];
    }
    if (!equal(startingPosition, nextPosition)) {
      nextPosition[0]++;
      return nextPosition;
    }
    while (symbolRight.equals("-") ||
        symbolRight.equals("J") ||
        symbolRight.equals("7")
    ) {
      symbolRight = maze[sX][++nextPosition[1]];
    }
    if (!equal(startingPosition, nextPosition)) {
      nextPosition[1]--;
      return nextPosition;
    }
    while (symbolDown.equals("|") ||
        symbolDown.equals("J") ||
        symbolDown.equals("L")
    ) {
      symbolDown = maze[++nextPosition[0]][sY];
    }
    if (!equal(startingPosition, nextPosition)) {
      nextPosition[0]--;
      return nextPosition;
    }
    while (symbolLeft.equals("-") ||
        symbolLeft.equals("L") ||
        symbolLeft.equals("F")
    ) {
      symbolLeft = maze[sX][--nextPosition[1]];
    }
    nextPosition[1]++;
    return nextPosition;
  }

  private static String[][] getMaze(List<String> documentContents) {
    int numRows = documentContents.size();
    int numColumns = documentContents.get(0).length();
    String[][] maze = new String[numRows][numColumns];
    for (int i = 0; i < numColumns; i++) {
      maze[i] = documentContents.get(i).split("");
    }
    return maze;
  }

  private static int[] getStartingPosition(List<String> documentContents) {
    int[] startingPosition = new int[2];
    int startX = -1;
    for (String line : documentContents) {
      startX++;
      if (line.contains("S")) {
        startingPosition[0] = startX;
        startingPosition[1] = line.indexOf('S');
        break;
      }
    }
    return startingPosition;
  }

  private static <E> void display(E[][] maze) {
    String delimiter = " ";
    for (E[] row : maze) {
      for (E element : row) {
        System.out.print(element + delimiter);
      }
      System.out.println();
    }
  }

  private static Map<String, String> getPipeSymbols() {
    Map<String, String> symbolMeanings = new HashMap<>();
    symbolMeanings.put("|", "NS");
    symbolMeanings.put("-", "WE");
    symbolMeanings.put("L", "NE");
    symbolMeanings.put("J", "NW");
    symbolMeanings.put("7", "SW");
    symbolMeanings.put("F", "SE");
//    symbolMeanings.put('.', "Ground");
//    symbolMeanings.put('S', "StartingPosition");
    return symbolMeanings;
  }
}
