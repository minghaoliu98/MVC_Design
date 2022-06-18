package game;

import treasure.Direction;

/**
 * This represents a game in dungeon.
 */
public interface GameInterface extends ReadOnlyGameInterface {


  /**
   * move player around the dungeon.
   * @param d the direction
   * @return the status after move
   * @throws IllegalStateException move when game is over
   */
  String move(Direction d) throws IllegalStateException;

  /**
   * pick treasure from dungeon to user.
   * @return the status after pick
   * @throws IllegalStateException pick when game is over
   */
  String pick() throws IllegalStateException;

  /**
   * shoot an arrow in given direction.
   * @param d direction of arrow
   * @param distance distance of arrow
   * @return String after shoot
   * @throws IllegalStateException shoot when game is over
   */
  String shoot(Direction d, int distance) throws IllegalStateException;

  /**
   * win the game.
   * @return winning text
   */
  String win();

}
