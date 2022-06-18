package graphic;

import java.awt.event.KeyEvent;

import game.Game;
import game.GameInterface;
import treasure.Direction;
import view.DungeonView;
import view.DungeonViewInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * this is the controller of gui game.
 */
public class GraphicDungeonController implements InterfaceGraphicDungeonController {

  private GameInterface m;
  private DungeonViewInterface v;

  /**
   * constructor.
   * @param m game interface the model
   * @param v the view
   * @throws IllegalArgumentException if either view or model is null
   */
  public GraphicDungeonController(GameInterface m,
                                  DungeonViewInterface v) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Gameinterface is null");
    }
    if (v == null) {
      throw new IllegalArgumentException("DungeonViewInterface is null");
    }
    this.m = m;
    this.v = v;
  }


  @Override
  public void playGame() {
    try {
      configureKeyBoardListener();
      configureButtonListener();
      v.refresh("Welcome to Dungeon");
      v.addClickListener(this);
      v.makeVisible();
      v.resetFocus();
    } catch (IllegalArgumentException illegalArgumentException) {
      System.out.println(illegalArgumentException.getMessage());
    }
  }

  @Override
  public void move(Direction d) throws IllegalArgumentException {
    if (d == null) {
      throw new IllegalArgumentException("direction is null");
    }
    if (!m.gameOver()) {
      v.refresh(m.move(d));
    }
  }

  @Override
  public void shoot(String direction, String distanceText) throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("direction is null");
    }

    if (distanceText == null) {
      throw new IllegalArgumentException("distanceText is null");
    }
    try {
      int distance = Integer.parseInt(distanceText);
      for (Direction d : Direction.values()) {
        if (d.toString().equals(direction)) {
          v.refresh(m.shoot(d, distance));
        }
      }
    } catch (NumberFormatException e) {
      v.refresh("Invalid Distance Please Enter Again");
    }
    v.makePopUpInvisible();
  }

  @Override
  public void pick() {
    if (!m.gameOver()) {
      v.refresh(m.pick());
    }
  }

  @Override
  public void win() {
    if (m.isEnd() && !m.gameOver()) {
      v.refresh(m.win());
    } else {
      v.refresh("You can't leave the Dungeon until the exit");
    }
  }

  private void resizeGame(int row, int column, int interconnectivity, boolean wrapping,
                         int numExtraMonster, Stack<Integer> testMode, int treasure) {
    if (row < 1 || column < 1) {
      throw new IllegalArgumentException("row or column is smaller than 1");
    }
    if (row <= 2 && column <= 2) {
      throw new IllegalArgumentException("not enough distance to create a end with min 5 steps");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("interconnectivity is smaller than 0");
    }
    v.makeInvisible();
    m = new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode, treasure,"player 1");
    v = new DungeonView(m);
    playGame();
  }

  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    keyPresses.put(KeyEvent.VK_UP, () -> move(Direction.NORTH));
    keyPresses.put(KeyEvent.VK_DOWN, () -> move(Direction.SOUTH));
    keyPresses.put(KeyEvent.VK_LEFT, () -> move(Direction.WEST));
    keyPresses.put(KeyEvent.VK_RIGHT, () -> move(Direction.EAST));
    keyPresses.put(KeyEvent.VK_P, this::pick);
    keyPresses.put(KeyEvent.VK_S, v::showShooting);
    keyPresses.put(KeyEvent.VK_R, () -> resizeGame(m.getRow(), m.getColumn(),
        m.getInterconnectivity(), m.getWrapping(), m.getNumExtraMonster(),
        m.getTestMode(), m.getTreasure()));
    keyPresses.put(KeyEvent.VK_L, this::win);
    keyPresses.put(KeyEvent.VK_Q, () -> System.exit(0));

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyPressedMap(keyPresses);
    v.addKeyListener(kbd);
  }

  /**
   * Setting up the button listeners.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ActionListenerClass buttonListener = new ActionListenerClass();

    buttonClickedMap.put("Quit Button", () -> System.exit(0));
    buttonClickedMap.put("Player Button", v::showPlayerInfo);
    buttonClickedMap.put("Tutorial Button", v::showTutorial);
    buttonClickedMap.put("Restart Button", () -> {
      resizeGame(m.getRow(), m.getColumn(), m.getInterconnectivity(),
          m.getWrapping(), m.getNumExtraMonster(), m.getTestMode(), m.getTreasure());
    });
    buttonClickedMap.put("Setting Button", v::showSetting);
    buttonClickedMap.put("Confirm Button", () -> {
      String[] result = v.getSettingInfo();
      try {
        resizeGame(Integer.parseInt(result[0]), Integer.parseInt(result[1]),
            Integer.parseInt(result[2]), result[3].equals("T"),
            Integer.parseInt(result[4]), new Stack<>(), Integer.parseInt(result[5]));

      } catch (NumberFormatException e) {
        v.refresh("input is not valid");
      } catch (IllegalArgumentException e) {
        v.refresh(e.getMessage());
      }
    });
    buttonClickedMap.put("Shoot Button", v::showShooting);
    buttonClickedMap.put("Leave Button", this::win);
    buttonClickedMap.put("Pick Button", this::pick);

    buttonClickedMap.put("shoot arrow", () -> {
      String[] result = v.getShootingInfo();
      this.shoot(result[0], result[1]);
    });


    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    v.addActionListener(buttonListener);
  }
}
