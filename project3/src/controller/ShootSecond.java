package controller;

import treasure.Direction;
import game.GameInterface;

import java.util.Scanner;

/**
 * this is the fire function as the second step of shoot.
 */
public class ShootSecond extends CommandAbstract {

  private final String in;
  private final Direction d;

  /**
   * constructor.
   * @param in the input string
   * @param out out put appendable
   * @param scan use for input is invalid
   * @param d direction
   */
  public ShootSecond(String in, Appendable out, Scanner scan, Direction d) {
    super(out, scan);
    if (d == null) {
      throw new IllegalStateException("Direction is missing");
    }
    if (in == null) {
      throw new IllegalArgumentException("direction is null");
    }
    this.in = in;
    this.d = d;
  }

  @Override
  public void excute(GameInterface g) {
    if (g == null) {
      throw new IllegalStateException("game is null");
    }
    try {
      int result = Integer.parseInt(in);
      print(g.shoot(d, result) + "\n");
    } catch (NumberFormatException e) {
      print("invalid shooting distance !!! Please Enter Again \n");
      print("No. of caves?");
      new ShootSecond(scan.next(), out, scan, d).excute(g);
    }
  }
}
