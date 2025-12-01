package za.co.alexgoudemond.day_10.v2;

import za.co.alexgoudemond.util.Utilities;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_10/largerExample.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
    Maze maze = new Maze(getGrid(documentContents));
    maze.showOnlyTheLoop();
    int middleOfLoop = maze.getConnectedLoopSize() / 2;
    System.out.println("middleOfLoop = " + middleOfLoop);

    maze.completelySimplifiedLoop();
  }

  private static String[][] getGrid(List<String> documentContents) {
    String[][] grid = new String[documentContents.size()][0];
    char[][] array = documentContents
        .stream()
        .map(String::toCharArray)
        .collect(Collectors.toList())
        .toArray(new char[0][0]);
    for (int i = 0; i < array.length; i++) {
      grid[i] = new String[array[i].length];
      for (int j = 0; j < array[i].length; j++) {
        grid[i][j] = String.valueOf(array[i][j]);
      }
    }
    return grid;
  }
}
