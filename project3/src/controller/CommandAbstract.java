package controller;

import treasure.Direction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * this is the abstract class for command.
 */
abstract class CommandAbstract implements DungeonCommandInterface {

  protected final Appendable out;
  protected final Scanner scan;
  protected final Map<String, Direction> directionMap;

  /**
   * constructor.
   * @param out out put appendable
   * @param scan use for input is invalid
   */
  public CommandAbstract(Appendable out, Scanner scan) {
    if (out == null) {
      throw new IllegalStateException("appendable is missing");
    }
    if (scan == null) {
      throw new IllegalStateException("Scanner is missing");
    }
    this.out = out;
    this.scan = scan;
    this.directionMap = new HashMap<>();
    for (Direction d :Direction.values()) {
      directionMap.put(d.toString(), d);
    }
  }

  protected void print(String s) {
    try {
      out.append(s);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

}
