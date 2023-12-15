package day_05;

import java.util.Objects;

import static java.lang.Long.parseLong;

public class Rule {

  private final Long beginning;

  private final Long end;

  private final Long shift;

  //signature matches almanac rows provided
  public Rule(Long correspondingValue, Long startValue, Long range) {
    this.beginning = startValue;
    this.end = startValue + range - 1;
    this.shift = correspondingValue - startValue;
  }

  public Rule(String[] instruction) {
    this(parseLong(instruction[0]),
        parseLong(instruction[1]),
        parseLong(instruction[2]));
  }

  public Rule(String instruction) {
    this(instruction.split(" "));
  }

  public static Rule defaultRule() {
    return new Rule(0L, 0L, Long.MAX_VALUE);
  }

  public Long getBeginning() {
    return beginning;
  }

  public Long getEnd() {
    return end;
  }

  public Long getShift() {
    return shift;
  }

  @Override
  public String toString() {
    return "(beginning, range, shift): " +
        "(" + getBeginning() +
        ", " + getEnd() +
        ", " + getShift() +
        ')';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Rule rule = (Rule) o;
    return Objects.equals(beginning, rule.beginning) && Objects.equals(end, rule.end) && Objects.equals(shift, rule.shift);
  }

  @Override
  public int hashCode() {
    return Objects.hash(beginning, end, shift);
  }
}
