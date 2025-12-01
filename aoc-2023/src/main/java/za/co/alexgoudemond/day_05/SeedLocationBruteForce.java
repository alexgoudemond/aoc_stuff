package za.co.alexgoudemond.day_05;

import za.co.alexgoudemond.util.Utilities;

import java.util.ArrayList;
import java.util.List;

/*
 * Credit to Yoshailen - suggested going BACKWARDS for v2
 * */
public class SeedLocationBruteForce {

  public static final String COLON_SYMBOL = ":";

  public static void main(String[] args) {
//    test();

//    String docName = "resources/day_05/testAlmanac.txt";
    String docName = "dec_2023/resources/day_05/almanac.txt";
    List<String> documentContents = Utilities.getDocumentContents(docName);
    puzzle2(documentContents);
  }

  private static void puzzle2(List<String> documentContents) {
    List<String> instructions = parseDocumentContents(documentContents);
    InvertedAlmanac invertedAlmanac = getAlmanac(instructions);
//    System.out.println(invertedAlmanac);

    Rule lowestOverallRule = invertedAlmanac.getLowestRuleFromFirstEntry();
//    System.out.println("lowestOverallRule = " + lowestOverallRule);
    long walkNumber = lowestOverallRule.getBeginning();

    //works
//    long num = invertedAlmanac.traverseMapEntriesAndGetSeed(46);
//    System.out.println("num = " + num);

    long correspondingSeed = -1;
    while (correspondingSeed == -1) {
      correspondingSeed = invertedAlmanac.traverseMapEntriesAndGetSeed(walkNumber++);
    }
    System.out.println("walkNumber = " + walkNumber); // 69323688 --> Works!
  }

  private static InvertedAlmanac getAlmanac(List<String> instructions) {
    String[] seeds = instructions.get(0).replaceAll("\n", "").split(" ");
    InvertedAlmanac invertedAlmanac = new InvertedAlmanac(seeds);
    Rule tempRule;
    AlmanacMapEntry almanacMapEntry;
    for (int i = instructions.size() - 1; i > 0; i--) {
      almanacMapEntry = new AlmanacMapEntry("instruction " + (i + 1));
      for (String rule : instructions.get(i).split("\n")) {
        tempRule = new Rule(rule);
        almanacMapEntry.addRule(tempRule);
      }
      invertedAlmanac.add(almanacMapEntry);
    }
    return invertedAlmanac;
  }

  private static List<String> parseDocumentContents(List<String> documentContents) {
    List<String> instructions = new ArrayList<>();
    StringBuilder ruleString = new StringBuilder();
    for (int i = 0; i < documentContents.size(); i++) {
      String line = documentContents.get(i);
      if (line.contains(COLON_SYMBOL) && i == 0) {
        ruleString.append(line.substring(line.indexOf(COLON_SYMBOL) + 2));
        ruleString.append("\n");
      } else if (line.isEmpty()) {
        instructions.add(ruleString.toString());
        ruleString = new StringBuilder();
      } else if (!line.contains(COLON_SYMBOL)) {
        ruleString.append(line);
        ruleString.append("\n");
      }
    }
    instructions.add(ruleString.toString());
    return instructions;
  }
}