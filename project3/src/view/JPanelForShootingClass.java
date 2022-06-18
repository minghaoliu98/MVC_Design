package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;

/**
 * this is the Jpanel for shooting.
 */
public class JPanelForShootingClass extends JPanel {


  /**
   * constructor which takes a readonlyTTTmodel.
   * @param confirm the confirm shooting button
   * @param group the jradio group button
   * @param field the text input field
   * @throws IllegalArgumentException if any parameter is null
   */
  public JPanelForShootingClass(JButton confirm,
                                ButtonGroup group, JFormattedTextField field)
      throws IllegalArgumentException {
    if (confirm == null) {
      throw new IllegalArgumentException("confirm button is null");
    }
    if (group == null) {
      throw new IllegalArgumentException("direction button group is null");
    }
    if (field == null) {
      throw new IllegalArgumentException("input text field button is null");
    }
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(new JLabel("Select A Direction"));
    JRadioButton option1 = new JRadioButton("NORTH");
    JRadioButton option2 = new JRadioButton("SOUTH");
    JRadioButton option3 = new JRadioButton("WEST");
    JRadioButton option4 = new JRadioButton("EAST");
    option1.setActionCommand("N");
    option2.setActionCommand("S");
    option3.setActionCommand("W");
    option4.setActionCommand("E");
    group.add(option1);
    group.add(option2);
    group.add(option3);
    group.add(option4);
    option1.setSelected(true);


    add(option1);
    add(option2);
    add(option3);
    add(option4);
    this.add(new JLabel("Put A Distance"));
    confirm.setActionCommand("shoot arrow");
    this.add(field);
    this.add(confirm);
  }


}
