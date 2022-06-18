package controller;

import game.GameInterface;

import java.util.Scanner;

/**
 * This represents the pick function.
 */
public class Leave extends CommandAbstract {

  /**
   * constructor.
   * @param out out put appendable
   * @param scan use for input is invalid
   */
  public Leave(Appendable out, Scanner scan) {
    super(out, scan);
  }

  @Override
  public void excute(GameInterface g) {
    if (g == null) {
      throw new IllegalStateException("game is null");
    }
    if (g.isEnd()) {
      print(g.win() + "\n");
    }
  }
}
