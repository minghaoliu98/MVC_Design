package controller;

import game.GameInterface;

/**
 * this represents the command of dungeon.
 */
public interface DungeonCommandInterface {

  /**
   * run the command.
   * @param g the game
   */
  void excute(GameInterface g);
}
