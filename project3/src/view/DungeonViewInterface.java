package view;

import graphic.ActionListenerClass;
import graphic.GraphicDungeonController;

import java.awt.event.KeyListener;

/**
 * the dungeon view interface.
 */
public interface DungeonViewInterface {

  /**
   * add click event to the dungeon.
   * @param graphicDungeonController the controller
   * @throws IllegalArgumentException if graphicDungeonController is null
   */
  void addClickListener(GraphicDungeonController graphicDungeonController)
      throws IllegalArgumentException;

  /**
   * refresh the dungeon map.
   * @param text text message
   * @throws IllegalArgumentException if text is null
   */
  void refresh(String text) throws IllegalArgumentException;

  /**
   * make the view visible.
   */
  void makeVisible();

  /**
   * make the view visible.
   */
  void makeInvisible();

  /**
   * make the view visible.
   */
  void makePopUpInvisible();

  /**
   * This is the same method signature to add a key
   * listener in Java Swing.
   * @param listener the listener to add
   * @throws IllegalArgumentException if listener is null
   */
  void addKeyListener(KeyListener listener) throws IllegalArgumentException;

  /**
   * This is the same method signature to add a button
   * listener in Java Swing.
   *
   * @param listener the listener to add
   */
  void addActionListener(ActionListenerClass listener);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard
   * listener attached to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * show player info.
   */
  void showPlayerInfo();

  /**
   * show player info.
   */
  void showTutorial();

  /**
   * show shooting info.
   */
  void showShooting();

  /**
   * show shooting info.
   */
  void showSetting();

  /**
   * return the shooting info panel direction and distance.
   * @return the shooting info
   */
  String[] getShootingInfo();

  /**
   * return the setting info panel for new Dungeon.
   * @return the setting info
   */
  String[] getSettingInfo();
}
