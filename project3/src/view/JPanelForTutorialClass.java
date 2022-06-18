package view;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import java.io.IOException;
import java.util.Objects;

/**
 * this is the concrete class to define tutorial panel size.
 */
public class JPanelForTutorialClass extends JPanel {

  /**
   * constructor.
   */
  public JPanelForTutorialClass() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    addTextHelper("Welcome to the dungeon: This is the tutorial", null);
    addTextHelper("Move by key: Press arrow key on the key board", null);
    addTextHelper("Move by Mouse: click the dungeon around player", null);
    addTextHelper("Press P:    Pick Up all Treasure", null);
    addTextHelper("Press S:    Shoot an arrow", null);
    addTextHelper("Press R:    Restart the game", null);
    addTextHelper("Press L:    Leave the dungeon and win the game at exit", null);
    addTextHelper("Player Location", "player.png");
    addTextHelper("Treasure with DIAMOND, RUBIE, SAPPHIRE, ARROW", "treasure.png");
    addTextHelper("The monster with can takes 2 hit and will kill you", "otyugh.png");
    addTextHelper("The exit to leave the dungeon", "door.png");
    addTextHelper("There is a strong smell from monster", "stench02.png");
    addTextHelper("There is a weak smell from monster", "stench01.png");
  }

  private void addTextHelper(String text, String img) {
    JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
    textLabel.setAlignmentX(CENTER_ALIGNMENT);
    if (img != null) {
      try {
        textLabel.setIcon(new ImageIcon(
            ImageIO.read(Objects.requireNonNull(
                getClass().getResource("/dungeon-images-bw/" + img)))));
      } catch (IOException e) {
        System.out.println("Image is missing during tutorial load");
      }
    }
    this.add(textLabel);
  }

}