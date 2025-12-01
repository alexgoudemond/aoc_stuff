package za.co.alexgoudemond.aoc.dec_2023.day_10.v3;

import za.co.alexgoudemond.aoc.dec_2023.util.Utilities;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    String docName = "dec_2023/resources/day_10/mainMaze.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
    Maze maze = new Maze(getGrid(documentContents));
//    System.out.println(maze.farthestDistanceInLoop()); //6867 --> correct
    System.out.println(maze.getNumberOfNests()); // 599 wrong --> 595 is correct
    System.out.println("maze.numNestsPicksTheorem() = " + maze.numNestsPicksTheorem());

    System.out.println();

    String[][] grid = maze.getGrid();
    System.out.println();
    String memoName = "dec_2023/resources/day_10/modelAnswer.txt";
    List<String> memoContents = Utilities.getDocumentContents(memoName);
    for (int i = 1; i < grid.length - 1; i++) {
      String[] row = memoContents.get(i - 1).split("");
      for (int j = 1; j < grid[i].length - 1; j++) {
        if (!grid[i][j].equals(row[j - 1])) {
          System.out.println("Error @ i, j: " + i + ", " + j);
        }
      }
    }
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
