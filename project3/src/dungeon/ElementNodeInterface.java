package dungeon;

import treasure.Direction;
import treasure.Treasure;

import java.util.List;

/**
 * this is the interface for dungeon room.
 */
public interface ElementNodeInterface {

  /**
   * add new element node to current one.
   * @param temp the new room
   * @param d the direction
   * @throws IllegalArgumentException if temp or direction is null
   */
  void add(ElementNode temp, Direction d) throws IllegalArgumentException;

  /**
   * return the current location of player.
   * @return the location
   */
  String toSign();

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
   * return if room has monster.
   * @return if has monster
   */
  boolean hasMonster();

  /**
   * put a monster in the current room.
   */
  void putMonster();

  /**
   * put treasure in the current room.
   * @param treasures those treasure
   * @throws IllegalArgumentException if treasure is null
   */
  void putTreasure(List<Treasure> treasures) throws IllegalArgumentException;

  /**
   * move in the given direction.
   * @param d the direction.
   * @return the new room
   * @throws IllegalArgumentException if d is null
   */
  ElementNode move(Direction d) throws IllegalArgumentException;

  /**
   * get all treasure.
   * @return those treasure
   */
  List<Treasure> getTreasure();

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

  /**
   * shoot in the given direction and distance.
   * @param d direction
   * @param distance distance
   * @return if anything get hit
   * @throws IllegalArgumentException d or distance is invalid or null
   */
  boolean shoot(Direction d, int distance) throws IllegalArgumentException;

  /**
   * return if room has treasure.
   * @return if has monster
   */
  boolean hasTreasure();
}
