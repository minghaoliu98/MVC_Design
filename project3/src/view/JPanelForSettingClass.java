package view;

import game.ReadOnlyGameInterface;

import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

/**
 * this is the JPanel For setting dialog.
 */
public class JPanelForSettingClass extends JPanel {

  /**
   * constructor of the setting panel.
   * @param row field for number of row in dungeon
   * @param column field for number of column in dungeon
   * @param interconnectivity field for the interconnecticity of dungeon
   * @param wrapping field for if the dungeon is wrapped
   * @param numExtraMonster field for number of extra monster beside the one in exit
   * @param treasure field for percnetage of treasure
   * @param settingConfirmButton setting confirm button
   * @param game readOnlyGameInterface
   * @throws IllegalArgumentException when parameter is null
   */
  public JPanelForSettingClass(JFormattedTextField row, JFormattedTextField column,
                               JFormattedTextField interconnectivity, ButtonGroup wrapping,
                               JFormattedTextField numExtraMonster, JFormattedTextField treasure,
                               JButton settingConfirmButton, ReadOnlyGameInterface game) {
    if (row == null) {
      throw new IllegalArgumentException("row field of input is null");
    }
    if (column == null) {
      throw new IllegalArgumentException("column field of input is null");
    }
    if (interconnectivity == null) {
      throw new IllegalArgumentException("interconnectivity field of input is null");
    }
    if (numExtraMonster == null) {
      throw new IllegalArgumentException("numExtraMonster field of input is null");
    }
    if (treasure == null) {
      throw new IllegalArgumentException("treasure field of input is null");
    }
    if (wrapping == null) {
      throw new IllegalArgumentException("wrapping field of input is null");
    }
    if (settingConfirmButton == null) {
      throw new IllegalArgumentException("settingConfirmButton is null");
    }
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    row.setValue(game.getRow());
    column.setValue(game.getColumn());
    interconnectivity.setValue(game.getInterconnectivity());
    numExtraMonster.setValue(game.getNumExtraMonster());
    treasure.setValue(game.getTreasure());

    helper(row, "number of row");
    helper(column, "number of column");
    helper(interconnectivity, "interconnectivity");
    helper(numExtraMonster, "number of extra monster");
    helper(treasure, "Percentage of treasure");
    JPanel temp = new JPanel();
    temp.setLayout(new FlowLayout());
    temp.add(new JLabel("if wrapping"));

    JRadioButton option1 = new JRadioButton("True");
    JRadioButton option2 = new JRadioButton("False");
    option1.setActionCommand("T");
    option2.setActionCommand("F");
    wrapping.add(option1);
    wrapping.add(option2);
    option1.setSelected(true);
    temp.add(option1);
    temp.add(option2);
    this.add(temp);
    settingConfirmButton.setActionCommand("Confirm Button");
    this.add(settingConfirmButton);
  }

  private void helper(JFormattedTextField sample, String text) {
    JPanel temp = new JPanel();
    sample.setColumns(5);
    temp.setLayout(new FlowLayout());
    temp.add(new JLabel(text));
    temp.add(sample);
    this.add(temp);
  }

}
