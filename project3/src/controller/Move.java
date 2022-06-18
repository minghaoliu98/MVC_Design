package controller;

import game.GameInterface;

import java.util.Scanner;

/**
 * This represents the move function.
 */
public class Move extends CommandAbstract {

  private final String in;

  /**
   * constructor.
   * @param in the input string
   * @param out out put appendable
   * @param scan use for input is invalid
   */
  public Move(String in, Appendable out, Scanner scan) {
    super(out, scan);
    if (in == null) {
      throw new IllegalArgumentException("direction is null");
    }
    this.in = in.toUpperCase();
  }

  @Override
  public void excute(GameInterface g) {
    if (g == null) {
      throw new IllegalStateException("game is null");
    }
    if (!directionMap.containsKey(in)) {
      print("invalid direction !!! Please Enter a New Moving Direction Again \n");
      print("Where to Go?");
      new Move(scan.next(), out, scan).excute(g);
    } else {
      print(g.move(directionMap.get(in)) + "\n");
    }
  }
}
