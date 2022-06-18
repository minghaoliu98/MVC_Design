package graphic;

import treasure.Direction;


/**
 * this is the interface for graphic controller.
 */
public interface InterfaceGraphicDungeonController {

  /**
   * initialize the view and model.
   */
  void playGame();

  /**
   * move in the given direction.
   * @param d the direction
   * @throws IllegalArgumentException if any of parameter is null
   */
  void move(Direction d) throws IllegalArgumentException;

  /**
   * shoot in direction.
   * @param direction direction in text
   * @param distanceText distance in text
   * @throws IllegalArgumentException if any of parameter is null
   */
  void shoot(String direction, String distanceText) throws IllegalArgumentException;

  /**
   * pick treasure from dungeon.
   */
  void pick();

  /**
   * win the game at exit.
   */
  void win();

}

