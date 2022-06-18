package controller;

import game.GameInterface;

import java.util.Scanner;

/**
 * This represents the move function.
 */
public class ShootFirst extends CommandAbstract {

  private final String in;

  /**
   * constructor.
   * @param in the input string
   * @param out out put appendable
   * @param scan use for input is invalid
   */
  public ShootFirst(String in, Appendable out, Scanner scan) {
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
      print("invalid direction !!! Please Enter a New Shooting Direction \n");
      print("Where to shoot?");
      new ShootFirst(scan.next(), out, scan).excute(g);
    } else {
      print("No. of caves?");
      new ShootSecond(scan.next(), out, scan, directionMap.get(in)).excute(g);
    }
  }
}
