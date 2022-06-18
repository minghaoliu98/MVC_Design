package view;

import game.ReadOnlyGameInterface;
import treasure.Direction;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * this is the concrete class to define panel size.
 */
public class JPanelForDungeonClass extends JPanel {

  private final ReadOnlyGameInterface game;
  private final ArrayList<JLabel> labelList;
  private JLabel previousVisited;
  private BufferedImage previousImage;



  /**
   * constructor which takes a readonlyTTTmodel.
   * @param game the TTTmodel.
   * @throws IllegalArgumentException if TTTmodel is null
   */
  public JPanelForDungeonClass(ReadOnlyGameInterface game) {
    if (game == null) {
      throw new IllegalArgumentException("Game Model is null");
    }
    this.game = game;
    this.setLayout(new GridLayout(game.getRow(), game.getColumn()));
    labelList = new ArrayList<>();
    BufferedImage myPicture = readImage("blank.png");
    for (int i = 0; i < game.getColumn() * game.getRow(); i ++) {
      JLabel label = new JLabel();
      label.setIcon(new ImageIcon(myPicture));
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setVerticalAlignment(JLabel.CENTER);
      this.add(label);
      labelList.add(label);
    }

  }

  @Override
  public void paintComponent(Graphics g) {
    if (g == null) {
      throw new IllegalArgumentException("graphic is null");
    }
    super.paintComponent(g);
    BufferedImage dungeon = readImage(toFileName());
    Graphics image = dungeon.getGraphics();
    if (game.hasWeakSmell()) {
      image.drawImage(readImage("stench01.png"), 0, 0, null);
    }
    if (game.hasStrongSmell()) {
      image.drawImage(readImage("stench02.png"), 0, 0, null);
    }
    if (game.isEnd()) {
      image.drawImage(readImage("door.png"), 30, 30, null);
    }
    if (game.hasMonster()) {
      image.drawImage(readImage("otyugh.png"), 17, 17, null);
    }
    if (game.hasTreasure()) {
      image.drawImage(readImage("treasure.png"), 10, 10, null);
    }
    removePreviousPlayerIcon(dungeon);
    image.drawImage(readImage("player.png"), 20, 20, null);
    labelList.get(game.getID()).setIcon(new ImageIcon(dungeon));

  }

  private BufferedImage readImage(String img) {
    try {
      return ImageIO.read(
          Objects.requireNonNull(getClass().getResource("/dungeon-images-bw/" + img)));
    } catch (IOException e) {
      System.out.println("Image is missing during game panel load");
    }
    return null;
  }

  private void removePreviousPlayerIcon(BufferedImage current) {
    ColorModel colorModel = current.getColorModel();
    BufferedImage temp = new BufferedImage(colorModel, current.copyData(null),
        colorModel.isAlphaPremultiplied(), null);
    if (previousVisited != null) {
      previousVisited.setIcon(new ImageIcon(previousImage));
    }
    previousVisited = labelList.get(game.getID());
    previousImage = temp;
  }


  private String toFileName() {
    String fileName = "";
    Set<Direction> moves = new HashSet<>(game.getPossibeMove());
    if (moves.contains(Direction.NORTH)) {
      fileName += Direction.NORTH.toString();
    }
    if (moves.contains(Direction.EAST)) {
      fileName += Direction.EAST.toString();
    }
    if (moves.contains(Direction.SOUTH)) {
      fileName += Direction.SOUTH.toString();
    }
    if (moves.contains(Direction.WEST)) {
      fileName += Direction.WEST.toString();
    }
    if (fileName.equals("")) {
      return "blank.png";
    }
    return fileName + ".png";
  }
}
