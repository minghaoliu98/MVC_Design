package dungeon;

import treasure.Direction;
import treasure.Treasure;

import java.util.List;

/**
 * This represents the dungeon interface in dungeon.
 */
public interface DungeonInterface {

  /**
   * add treasure to room base on chance.
   * @param percentage chance of treasure
   * @param monster number of monster
   * @throws IllegalArgumentException when percentage or monster is invalid
   */
  void addTreasureAndMonster(int percentage, int monster) throws IllegalArgumentException;

  /**
   * return the current location of player.
   * @return the location
   */
  String toSign();

  /**
   * move the player in direction.
   * @param d the direction
   */
  void move(Direction d);

  /**
   * pick treasure from dungeon.
   * @return the treasure
   */
  List<Treasure> pickTreausre();

  /**
   * get Possible move.
   * @return the possible move
   */
  List<Direction> getPossibeMove();

  /**
   * if the location reach the end.
   * @return if the current reach the end
   */
  boolean isEnd();

  /**
   * if the location reach the start.
   * @return if the current reach the start
   */
  boolean isStart();

  /**
   * return the id of current.
   */
  int getID();

  /**
   * return a copy of dungeon.
   * @return a copy
   */
  DungeonInterface copy();

  /**
   * return if current is tunnel.
   * @return if tunnel
   */
  boolean isCave();

  /**
   * return if the dungeon has monster.
   * @return monster's health
   */
  int getMonsterHealth();

  /**
   * shoot an arrow in given direction.
   * @param d direction of arrow
   * @param distance distance of arrow
   * @return if monster got hit is monster
   * @throws IllegalArgumentException when distance is invalid
   */
  boolean shoot(Direction d, int distance) throws IllegalArgumentException;

  /**
   * return a random int from 0 to max.
   * @param result max value
   * @return the random value
   */
  int randHelper(int result);

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
