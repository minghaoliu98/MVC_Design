package player;

import treasure.Treasure;

import java.util.List;

/**
 * this is the player which play the dungeon game.
 */
public interface PlayerInterface {

  /**
   * player pick treasures.
   * @param treasureList the treasure list
   * @throws IllegalArgumentException when treasurelist is empty
   */
  void pickTreasure(List<Treasure> treasureList) throws IllegalArgumentException;

  /**
   * return the name and treasure of player.
   * @return String contains name and treasure
   */
  String toSign();

  /**
   * shoot an arrow.
   * @return if there is an arrow shooted
   */
  boolean shoot();

  /**
   * if there is an arrow.
   * @returnif there is an arrow
   */
  boolean hasArrow();

}
