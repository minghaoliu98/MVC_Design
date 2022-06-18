package controller;

import game.Game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;


/**
 * This starter files is for students to implement a console controller for the
 * TicTacToe MVC assignment.
 */
public class DungeonController implements InterfaceDungeonController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   * 
   * @param in  the source to read from
   * @param out the target to print to
   */
  public DungeonController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(Game g) {
    if (g == null) {
      throw new IllegalArgumentException("Dungeon game is null");
    }
    Map<String, Function<Scanner, DungeonCommandInterface>> knownCommands;

    knownCommands = new HashMap<>();
    knownCommands.put("M",s -> {
      print("Where to Go? (N-S-E-W)");
      return new Move(s.next(), out, scan);
    });
    knownCommands.put("S",s -> {
      print("Where to Shoot? (N-S-E-W)");
      return new ShootFirst(s.next(), out, scan);
    });
    knownCommands.put("P", s -> new Pick(out, scan));
    knownCommands.put("L", s -> new Leave(out, scan));
    print("Welcome To Dungeon \n\n");
    print(g.dungeonToSign());
    print("\nMove, Pickup, or Shoot (M-P-S)?");
    while (scan.hasNext()) {
      String in = scan.next().toUpperCase();
      DungeonCommandInterface c;
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        return;
      }
      Function<Scanner, DungeonCommandInterface> cmd = knownCommands.getOrDefault(in, null);
      if (cmd == null || (!g.isEnd() && in.equals("L"))) {
        print("invalid command !!! Please Enter Again");
      } else {
        c = cmd.apply(scan);
        c.excute(g);
        if (g.gameOver()) {
          return;
        }
        print(g.dungeonToSign());
      }
      if (g.isEnd()) {
        print("\nMove, Pickup, Shoot or Leave Dungeon (M-P-S-L)?");
      } else {
        print("\nMove, Pickup, or Shoot (M-P-S)?");
      }
    }
  }


  private void print(String s) {
    try {
      out.append(s);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }


}
