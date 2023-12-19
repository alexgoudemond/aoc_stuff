package day_08;

import util.Utilities;

import java.util.*;

public class HauntedWasteland {

  public static void main(String[] args) {
//    String docName = "dec_2023/resources/day_08/testMap1.txt";
//    String docName = "dec_2023/resources/day_08/testMap2.txt";
    String docName = "dec_2023/resources/day_08/map.txt";
//    String docName = "dec_2023/resources/day_08/testMap3.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
//    documentContents.forEach(System.out::println);

    char[] directionPrompts = documentContents.get(0).toCharArray();
    Map<String, Node> nodes = getNodeMap(documentContents);
//    puzzle1(nodes, directionPrompts);


    /*
     * Part 2:
     * todo: brute force? FWD not working...
     * Have 'x' currentNodes, where 'x' is the # of nodes that end with a char 'A'.
     * Each node navigates the direction at the same time.
     * the stopping condition is that all nodes have a 'Z' at the end
     * */
//    nodes.forEach((key, value) -> System.out.println(value + ", " + value.sourceEndsWithZ()));
    List<Node> startingNodes = getStartingNodes(nodes);
    startingNodes.forEach(System.out::println);
//    notWorking(directionPrompts, startingNodes, nodes);

    List<Long> lowestMapMovements = new ArrayList<>();
    for (Node currentNode : startingNodes) {
      long count = 0;
      int nextDirectionIndex = 0;
      char directionPrompt;
      while (!currentNode.sourceEndsWithZ()) {
        if (nextDirectionIndex >= directionPrompts.length) {
          nextDirectionIndex = 0;
        }
        count++;
        directionPrompt = directionPrompts[nextDirectionIndex++];
        String map = currentNode.map(directionPrompt);
        currentNode = nodes.get(map);
      }
      lowestMapMovements.add(count);
    }
    //find LCM amongst lowestMapMovements
    Collections.sort(lowestMapMovements);
    System.out.println("lowestMapMovements = " + lowestMapMovements);
//    Long lcm = lowestMapMovements.get(lowestMapMovements.size() - 1);
//    boolean foundLCM = false;
//    label:
//    while (!foundLCM) {
//      lcm++;
//      for (Long num : lowestMapMovements) {
//        if (lcm / num != 0) {
//          continue label;
//        }
//      }
//      foundLCM = true;
//    }
//    System.out.println("lcm = " + lcm);
    long lcm = lcm_of_array_elements(lowestMapMovements.toArray(new Long[0]));
    System.out.println("lcm = " + lcm);
  }

  /*
  * Source: https://www.geeksforgeeks.org/lcm-of-given-array-elements/
  * Accessed on 2023/12/19
  * credit to GFG - just wanted to get a simple LCM function
  * */
  public static long lcm_of_array_elements(Long[] element_array)
  {
    long lcm_of_array_elements = 1;
    int divisor = 2;
    while (true) {
      int counter = 0;
      boolean divisible = false;
      for (int i = 0; i < element_array.length; i++) {
        if (element_array[i] == 0) {
          return 0;
        }
        else if (element_array[i] < 0) {
          element_array[i] = element_array[i] * (-1);
        }
        if (element_array[i] == 1) {
          counter++;
        }
        if (element_array[i] % divisor == 0) {
          divisible = true;
          element_array[i] = element_array[i] / divisor;
        }
      }
      if (divisible) {
        lcm_of_array_elements = lcm_of_array_elements * divisor;
      }
      else {
        divisor++;
      }
      if (counter == element_array.length) {
        return lcm_of_array_elements;
      }
    }
  }

  private static void notWorking(char[] directionPrompts, List<Node> startingNodes, Map<String, Node> nodes) {
    int nextDirectionIndex = 0;
    long count = 0;
    char directionPrompt;
    boolean allNodesEndWithZ = false;
    label:
    while (!allNodesEndWithZ) {
      count++;
      directionPrompt = directionPrompts[nextDirectionIndex++];
      if (nextDirectionIndex >= directionPrompts.length) {
        nextDirectionIndex = 0;
      }
      for (Node node : startingNodes) {
        if (!node.mapEndsWithZ(directionPrompt)) {
          mapAllNodes(startingNodes, nodes, directionPrompt);
          continue label;
        }
      }
      allNodesEndWithZ = true;
    }
    System.out.println("count = " + count); //not working
  }

  private static void mapAllNodes(List<Node> currentNodes, Map<String, Node> nodes, char directionPrompt) {
    for (int i = 0; i < currentNodes.size(); i++) {
      String map = currentNodes.get(i).map(directionPrompt);
      currentNodes.set(i, nodes.get(map));
    }
  }

  private static List<Node> getStartingNodes(Map<String, Node> nodes) {
    List<Node> startingNodes = new ArrayList<>();
    for (Node node : nodes.values()) {
      if (node.sourceEndsWithA()) {
        startingNodes.add(node);
      }
    }
    return startingNodes;
  }

  private static void puzzle1(Map<String, Node> nodes, char[] directionPrompts) {
    int nextDirectionIndex = 0;
    char directionPrompt;
    Node currentNode = nodes.get("AAA");
    while (!currentNode.isEnd()) {
      directionPrompt = directionPrompts[nextDirectionIndex++ % directionPrompts.length];
      String map = currentNode.map(directionPrompt);
      currentNode = nodes.get(map);
    }
    System.out.println("numStepsNeeded = " + nextDirectionIndex); //16579 --> correct
  }

  private static Map<String, Node> getNodeMap(List<String> documentContents) {
    Map<String, Node> nodes = new HashMap<>(documentContents.size() - 2);
    for (int i = 2; i < documentContents.size(); i++) {
      Node node = new Node(documentContents.get(i));
      nodes.put(node.getSource(), node);
    }
    return nodes;
  }
}
