package controller;

import game.Game;

/**
 * Represents a Controller for Game: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface InterfaceDungeonController {

  /**
   * Execute a single game of Game given a Game Model. When the game is over,
   * the playGame method ends.
   *
   * @param g a non-null Game Model
   */
  void playGame(Game g);
}
