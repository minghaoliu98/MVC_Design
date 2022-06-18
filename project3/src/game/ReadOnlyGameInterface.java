package game;

import treasure.Direction;

import java.util.List;
import java.util.Stack;

/**
 * the read only represents a game in dungeon.
 */
public interface ReadOnlyGameInterface {

  /**
   * return the number of rows.
   * @return number of row
   */
  int getRow();

  /**
   * return Wrapping.
   * @return Wrapping
   */
  boolean getWrapping();

  /**
   * return testMode.
   * @return testMode
   */
  Stack<Integer> getTestMode();

  /**
   * return the number of interconnectivity.
   * @return number of interconnectivity
   */
  int getInterconnectivity();

  /**
   * return the number of treasure.
   * @return number of treasure
   */
  int getTreasure();

  /**
   * return the number of NumExtraMonster.
   * @return number of NumExtraMonster
   */
  int getNumExtraMonster();

  /**
   * return the number of columns.
   * @return number of columns
   */
  int getColumn();

  /**
   * return if game is over.
   * @return game over
   */
  boolean gameOver();

  /**
   * get Possible move.
   * @return the possible move
   */
  List<Direction> getPossibeMove();

  /**
   * return the id of current.
   */
  int getID();

  /**
   * return if the player reach the end.
   * @return if player reach the end
   */
  boolean isEnd();

  /**
   * return if the player reach the start.
   * @return if player reach the start
   */
  boolean isStart();

  /**
   * return the dungeon sign.
   * @return player sign
   */
  String dungeonToSign();

  /**
   * return the dungeon sign.
   * @return player sign
   */
  String playerToSign();

  /**
   * return if room has monster.
   * @return if has monster
   */
  boolean hasMonster();

  /**
   * return if room has treasure.
   * @return if has monster
   */
  boolean hasTreasure();

  /**
   * if there are least 2 montser in distance of 2.
   * @return monster
   */
  boolean hasWeakSmell();

  /**
   * if there is monster in distance of 1.
   * @return monster
   */
  boolean hasStrongSmell();
}
