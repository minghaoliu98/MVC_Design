package graphic;

import game.ReadOnlyGameInterface;
import treasure.Direction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this will handle the mouse click.
 */
public class MouseAdapterClass extends MouseAdapter {

  private final GraphicDungeonController listener;
  private final ReadOnlyGameInterface m;

  /**
   * constructor for mouseAdapater.
   * @param listener the controller class.
   * @param m the ReadOnlyGameInterface class.

   */
  public MouseAdapterClass(GraphicDungeonController listener,
                           ReadOnlyGameInterface m) {
    if (listener == null || m == null) {
      throw new IllegalArgumentException(
          "GraphicDungeonController or ReadOnlyGameInterface is null");
    }
    this.m = m;
    this.listener = listener;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e == null) {
      throw new IllegalArgumentException(
          "MouseEvent is null");
    }
    if (m.getID() / m.getRow() * 64 < e.getY()
        && (m.getID() / m.getRow() + 1 ) * 64 > e.getY()) {

      if (e.getX() < m.getID() % m.getRow() * 64) {
        listener.move(Direction.WEST);
      } else if (e.getX() > (m.getID() % m.getRow() + 1) * 64) {
        listener.move(Direction.EAST);
      }
    } else if (m.getID() % m.getRow() * 64 < e.getX() &&
        (m.getID() % m.getRow() + 1 ) * 64 > e.getX()) {
      if (e.getY() < m.getID() / m.getRow() * 64) {
        listener.move(Direction.NORTH);
      } else if (e.getY() > (m.getID() / m.getRow() + 1) * 64) {
        listener.move(Direction.SOUTH);
      }
    }

  }

}
