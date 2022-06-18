package view;

import game.ReadOnlyGameInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Graphics;

/**
 * this is the concrete class to define player info panel.
 */
public class JPanelForPlayerInfoClass extends JPanel {

  private final JLabel text;
  private final ReadOnlyGameInterface game;

  /**
   * constructor which takes a readonlyTTTmodel.
   * @param game ReadOnlyGameInterface
   */
  public JPanelForPlayerInfoClass(ReadOnlyGameInterface game) {
    if (game == null) {
      throw new IllegalArgumentException("Game Model is null");
    }
    this.game = game;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.text = new JLabel("To be display", SwingConstants.CENTER);
    JLabel header = new JLabel("Player Info");
    header.setAlignmentX(CENTER_ALIGNMENT);
    text.setAlignmentX(CENTER_ALIGNMENT);
    this.add(header, SwingConstants.CENTER);
    this.add(text);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (g == null) {
      throw new IllegalArgumentException("graphic is null");
    }
    text.setText(game.playerToSign());
  }
}