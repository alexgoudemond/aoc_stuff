package za.co.alexgoudemond.day_05;

import java.util.LinkedList;

public class AlmanacMapEntry {

  private final String name;

  private final LinkedList<Rule> rules = new LinkedList<>();

  public AlmanacMapEntry(String name) {
    this.name = name;
  }

  @SuppressWarnings ("UnusedReturnValue")
  public boolean addRule(Rule newRule) {
    if (rules.isEmpty()) {
      rules.addFirst(newRule);
      return true;
    }
    for (int i = 0; i < rules.size(); i++) {
      Rule currentRule = rules.get(i);
      if (currentRule.equals(newRule)) {
        return false;
      }
      if (newRule.getBeginning() < currentRule.getBeginning()) {
        rules.add(i, newRule);
        return true;
      }
    }
    rules.addLast(newRule);
    return true;
  }

  public Rule getLowestRule(){
    Rule firstRule = rules.getFirst();
    if (firstRule.getBeginning() == 0){
      return firstRule;
    }
    return Rule.defaultRule();
  }

  public Rule getAssociatedRule(long number){
    for (Rule rule : rules) {
      long invertedBeginning = rule.getBeginning() + rule.getShift();
      long invertedEnd = rule.getEnd() + rule.getShift();
      if(number >= invertedBeginning && number <= invertedEnd){
        return rule;
      }
    }
    return Rule.defaultRule();
  }

  @Override
  public String toString() {
    return "(name, rules): " +
        "(" + name +
        ", " + rules +
        ")";
  }
}
