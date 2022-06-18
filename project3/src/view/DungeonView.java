package view;

import graphic.ActionListenerClass;
import graphic.GraphicDungeonController;
import game.ReadOnlyGameInterface;
import graphic.MouseAdapterClass;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * the jframe view class to display the entire gui.
 */
public class DungeonView extends JFrame implements DungeonViewInterface {

  private final ReadOnlyGameInterface readOnlyGameInterface;
  private final JPanel panel;
  private final JPanel tutorialPanel;
  private final JPanel playerInfoPanel;
  private final JPanel shootingPanel;
  private final JPanel settingPanel;

  private final JLabel textLabel;


  private final JButton helpButton;
  private final JButton shootingConfirmButton;
  private final JButton settingConfirmButton;

  private final JMenuItem settingButton;
  private final JMenuItem restartButton;
  private final JMenuItem quitButton;
  private final JMenuItem shootButton;
  private final JMenuItem pickButton;
  private final JMenuItem leaveButton;
  private final JMenuItem playerButton;

  private final JDialog playerDialog;
  private final ButtonGroup directionButtonGroup;
  private final ButtonGroup trueFalseButtonGroup;
  private final JFormattedTextField shootingDistanceField;
  private final JFormattedTextField rowField;
  private final JFormattedTextField columnField;
  private final JFormattedTextField interconnectivietyField;
  private final JFormattedTextField numOfExtraMonsterField;
  private final JFormattedTextField percentageOfTreasureField;



  /**
   * constructor.
   * @param model the readOnly interface
   * @throws IllegalArgumentException when model is null
   */
  public DungeonView(ReadOnlyGameInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("ReadOnlyGameInterface is null");
    }
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    this.readOnlyGameInterface = model;
    this.settingConfirmButton = new JButton("Start a new Game");
    this.shootingConfirmButton = new JButton("Shoot An Arrow");
    this.tutorialPanel = new JPanelForTutorialClass();
    this.playerInfoPanel = new JPanelForPlayerInfoClass(model);
    this.directionButtonGroup = new ButtonGroup();
    this.shootingDistanceField = new JFormattedTextField();
    this.shootingPanel = new JPanelForShootingClass(shootingConfirmButton,
        directionButtonGroup, shootingDistanceField);

    this.panel = new JPanelForDungeonClass(readOnlyGameInterface);

    this.textLabel = new JLabel("To Be Display", SwingConstants.CENTER);
    this.settingButton = new JMenuItem("Game Setting");
    this.restartButton = new JMenuItem("Restart");
    this.quitButton = new JMenuItem("Quit");
    this.shootButton = new JMenuItem("Shoot");
    this.pickButton = new JMenuItem("Pick");
    this.leaveButton = new JMenuItem("leave");
    this.playerButton = new JMenuItem("Player Info");
    this.helpButton = new JButton("Tutorial");
    this.playerDialog = new JDialog(this);

    this.rowField = new JFormattedTextField();
    this.columnField = new JFormattedTextField();
    this.interconnectivietyField = new JFormattedTextField();
    this.percentageOfTreasureField = new JFormattedTextField();
    this.numOfExtraMonsterField = new JFormattedTextField();
    this.trueFalseButtonGroup = new ButtonGroup();



    textLabel.setAlignmentX(CENTER_ALIGNMENT);
    playerDialog.setResizable(false);
    this.add(textLabel);

    this.settingPanel = new JPanelForSettingClass(rowField,
        columnField, interconnectivietyField, trueFalseButtonGroup,
        numOfExtraMonsterField, percentageOfTreasureField, settingConfirmButton, model);

    JScrollPane scrPaneDungeon = new JScrollPane(panel);
    scrPaneDungeon.setBorder(new EmptyBorder(0, 8, 8, 8));
    this.add(scrPaneDungeon);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Dungeon Game");
    this.setJMenuBar(new JmenuBarClass(settingButton, restartButton, quitButton,
        playerButton, pickButton, shootButton, leaveButton, helpButton));
    this.pack();
    this.setResizable(false);
    this.setLocation(300, 300);
  }

  @Override
  public void addClickListener(GraphicDungeonController graphicDungeonController) {
    if (graphicDungeonController == null) {
      throw new IllegalArgumentException("controller is null");
    }
    panel.addMouseListener(
        new MouseAdapterClass(graphicDungeonController, readOnlyGameInterface));
  }

  @Override
  public void refresh(String text) {
    if (text == null) {
      throw new IllegalArgumentException("text is null");
    }
    textLabel.setText(text);
    panel.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void makeInvisible() {
    this.setVisible(false);
  }

  @Override
  public void makePopUpInvisible() {
    playerDialog.setVisible(false);
    resetFocus();
  }

  @Override
  public void addActionListener(ActionListenerClass listener) {
    if (listener == null) {
      throw new IllegalArgumentException("actionListenerClass is null");
    }
    this.helpButton.addActionListener(listener);
    this.settingButton.addActionListener(listener);
    this.playerButton.addActionListener(listener);
    this.restartButton.addActionListener(listener);
    this.quitButton.addActionListener(listener);
    this.shootingConfirmButton.addActionListener(listener);
    this.settingConfirmButton.addActionListener(listener);
    this.shootButton.addActionListener(listener);
    this.pickButton.addActionListener(listener);
    this.leaveButton.addActionListener(listener);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showPlayerInfo() {
    playerDialog.setTitle("Player Info");
    playerInfoPanel.repaint();
    dialogHelper(400, 100, playerInfoPanel);
  }

  @Override
  public void showTutorial() {
    playerDialog.setTitle("Tutorial");
    dialogHelper(400, 400, tutorialPanel);
  }

  private void dialogHelper(int width, int height, JPanel panel) {

    playerDialog.getContentPane().removeAll();
    playerDialog.setSize(width, height);
    playerDialog.setLocation(100, 100);
    playerDialog.add(panel);
    playerDialog.setVisible(true);
    resetFocus();
  }

  @Override
  public void showShooting() {
    playerDialog.setTitle("Shooting Panel");
    dialogHelper(100, 220, shootingPanel);
  }

  @Override
  public void showSetting() {
    playerDialog.setTitle("Setting Panel");
    dialogHelper(400, 400, settingPanel);
  }

  @Override
  public String[] getShootingInfo() {
    return new String[]{directionButtonGroup.getSelection().getActionCommand()
        , shootingDistanceField.getText()};
  }

  @Override
  public String[] getSettingInfo() {
    return new String[]{rowField.getText(), columnField.getText(),
        interconnectivietyField.getText(), trueFalseButtonGroup.getSelection().getActionCommand(),
        numOfExtraMonsterField.getText(),
        percentageOfTreasureField.getText()
        };

  }

}
