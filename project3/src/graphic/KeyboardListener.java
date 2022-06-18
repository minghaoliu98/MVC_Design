package graphic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * This class represents a keyboard listener. It is configurable by the
 * controller that instantiates it.
 */
public class KeyboardListener implements KeyListener {
  private Map<Integer, Runnable> keyPressedMap;

  /**
   * Default constructor.
   */
  public KeyboardListener() {
    keyPressedMap = null;
  }


  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are
   * integer codes.
   * 
   * @param map the actions for keys pressed
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    if (map == null) {
      throw new IllegalArgumentException("map is null");
    }
    keyPressedMap = map;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //this is required to override in listener
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e == null) {
      throw new IllegalArgumentException("KeyEvent is null");
    }
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //this is required to override in listener

  }

}
