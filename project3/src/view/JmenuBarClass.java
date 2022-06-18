package view;

import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * create Jmenu Bar.
 */
public class JmenuBarClass extends JMenuBar {

  /**
   constructor.
   * @param settingButton setting JMenuItem
   * @param restartButton restart JMenuItem
   * @param quitButton quit JMenuItem
   * @param playerButton display player info button
   * @param pickButton pick treasure
   * @param shootButton shoot arrow
   * @param leaveButton leave at exit
   * @param helpButton display tutorial
   * @throws IllegalArgumentException if any parameter is null
   */
  public JmenuBarClass(JMenuItem settingButton, JMenuItem restartButton,
                       JMenuItem quitButton, JMenuItem playerButton, JMenuItem pickButton,
                       JMenuItem shootButton, JMenuItem leaveButton,
                       JButton helpButton) throws IllegalArgumentException {
    if (restartButton == null) {
      throw new IllegalArgumentException("restartButton is null");
    }
    if (quitButton == null) {
      throw new IllegalArgumentException("quitButton is null");
    }
    if (playerButton == null) {
      throw new IllegalArgumentException("playerButton is null");
    }
    if (helpButton == null) {
      throw new IllegalArgumentException("helpButton is null");
    }
    if (settingButton == null) {
      throw new IllegalArgumentException("settingButton is null");
    }
    JMenu menu = new JMenu("Setting");
    settingButton.setActionCommand("Setting Button");
    menu.add(settingButton);
    restartButton.setActionCommand("Restart Button");
    menu.add(restartButton);
    quitButton.setActionCommand("Quit Button");
    menu.add(quitButton);
    this.add(menu);

    menu = new JMenu("Game");
    pickButton.setActionCommand("Pick Button");
    shootButton.setActionCommand("Shoot Button");
    playerButton.setActionCommand("Player Button");
    leaveButton.setActionCommand("Leave Button");
    menu.add(pickButton);
    menu.add(shootButton);
    menu.add(playerButton);
    menu.add(leaveButton);
    this.add(menu);

    helpButton.setActionCommand("Tutorial Button");
    helpButton.setContentAreaFilled(false);
    this.add(helpButton);
  }


}
