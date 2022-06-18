import controller.DungeonController;
import graphic.GraphicDungeonController;
import graphic.InterfaceGraphicDungeonController;
import game.Game;
import game.GameInterface;
import view.DungeonView;
import view.DungeonViewInterface;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Stack;


/**
 * the driver class to present the game which take command line argument.
 * if only 6 or 0 input, the system will let user player the game.
 * if there are more than 6 input, it will read random helper and command.
 */
public class Driver {

  /**
   * run the driver with command-line input.
   * @param args the args input
   */
  public static void main(String[] args) {

    if (args.length == 0 || args.length == 1 && args[0].equals("GUI")) {
      int column = 6;
      int row = 6;
      int interconnectivity = 0;
      int treasure = 100;
      int numExtraMonster = 0;
      boolean wrapping = false;
      try {
        GameInterface model = new Game(row, column, interconnectivity,
            wrapping, numExtraMonster, new Stack<>(), treasure,"player 1");
        DungeonViewInterface view = new DungeonView(model);
        InterfaceGraphicDungeonController controller = new GraphicDungeonController(model, view);
        controller.playGame();
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    if (args.length == 1 && args[0].equals("textGame")) {
      int column = 3;
      int row = 3;
      int interconnectivity = 0;
      int treasure = 100;
      int numExtraMonster = 10;
      boolean wrapping = false;
      try {
        new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
            wrapping, numExtraMonster, new Stack<>(), treasure,"player 1"));
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    if (args.length >= 6) {
      int row = Integer.parseInt(args[0]);
      int column = Integer.parseInt(args[1]);
      int interconnectivity = Integer.parseInt(args[2]);
      boolean wrapping = "true".equals(args[3].toLowerCase());
      int treasure = Integer.parseInt(args[4]);
      int numExtraMonster = Integer.parseInt(args[5]);
      int index = 6;
      Stack<Integer> randomHelper = new Stack<>();
      String command = "";
      for (int i = 6; i < args.length; i ++) {
        try {
          randomHelper.add(Integer.parseInt(args[i]));
        } catch (NumberFormatException e) {
          index = i;
          break;
        }
        if (i == args.length - 1) {
          index = args.length;
        }
      }
      for (int i = index; i < args.length; i ++) {
        command += args[i] + " ";
      }
      StringReader inputSample = new StringReader(command);
      if (command.length() == 0) {
        try {
          new DungeonController(input, output).playGame(
              new Game(row, column, interconnectivity,
              wrapping, numExtraMonster, randomHelper, treasure,"player 1"));
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
      } else {
        try {
          new DungeonController(inputSample, output).playGame(
              new Game(row, column, interconnectivity,
              wrapping, numExtraMonster, randomHelper, treasure,"player 1"));
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  private static Stack<Integer> testMode() {
    Stack<Integer> result = new Stack<>();
    for (int i = 0; i < 1000; i ++) {
      result.add(0);
    }
    return result;
  }

}
