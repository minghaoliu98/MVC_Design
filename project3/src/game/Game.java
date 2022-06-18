package game;

import treasure.Direction;
import treasure.Treasure;
import dungeon.Dungeon;
import dungeon.DungeonInterface;
import player.Player;
import player.PlayerInterface;

import java.util.Stack;
import java.util.List;

/**
 * this is the game class which interact with driver.
 */
public class Game implements GameInterface {

  private final DungeonInterface temp;
  private final PlayerInterface p;
  private boolean gameOver;
  private final int row;
  private final int column;
  private final int interconnectivity;
  private final int numExtraMonster;
  private final int treasure;
  private final boolean wrapping;
  private final Stack<Integer> testMode;


  /**
   * constructor of the Dungeon with testMode option.
   * @param row number of row in dungeon
   * @param column number of column in dungeon
   * @param interconnectivity the interconnecticity of dungeon
   * @param wrapping if the dungeon is wrapped
   * @param numExtraMonster number of extra monster beside the one in exit
   * @param testMode if it is testMode
   * @param treasure percnetage of treasure
   * @param name name of player
   * @throws IllegalArgumentException when parameter is negative or below 1 or 2
   */
  public Game(int row, int column, int interconnectivity, boolean wrapping,
              int numExtraMonster, Stack<Integer> testMode, int treasure, String name)
      throws IllegalArgumentException {
    if (row < 1 || column < 1) {
      throw new IllegalArgumentException("row or column is smaller than 1");
    }
    if (row <= 2 && column <= 2) {
      throw new IllegalArgumentException("not enough distance to create a end with min 5 steps");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("interconnectivity is smaller than 0");
    }
    if (testMode == null) {
      throw new IllegalArgumentException("test mode is null");
    }
    this.temp = new Dungeon(row, column, interconnectivity, wrapping, testMode);
    temp.addTreasureAndMonster(treasure, numExtraMonster);
    this.p = new Player(name);
    gameOver = false;
    this.row = row;
    this.column = column;
    this.interconnectivity = interconnectivity;
    this.numExtraMonster = numExtraMonster;
    this.treasure = treasure;
    this.wrapping = wrapping;
    this.testMode = (Stack<Integer>) testMode.clone();
  }

  @Override
  public String move(Direction d) {
    if (gameOver) {
      throw new IllegalStateException("no move can be performed after game over");
    }
    if (d == null) {
      throw new IllegalArgumentException("null direction");
    }
    temp.move(d);
    String result = "";
    if (temp.getMonsterHealth() == 1) {
      if (1 == temp.randHelper(2)) {
        gameOver = true;
        result += "\n\nyou are eaten by an Otyugh!\n" +
            "Better luck next time";
      } else {
        result += "\n\nyou are hit by an Otyugh!\n" +
            "You are lucky and evades the attack\n";
      }
    } else if (temp.getMonsterHealth() == 2) {
      gameOver = true;
      result += "\n\nyou are eaten by an Otyugh!\n" +
          "Better luck next time\n";
    }
    return String.format("Move: " + d + " \n", temp.toSign()) + result;
  }

  @Override
  public String pick() {
    if (gameOver) {
      throw new IllegalStateException("no move can be performed after game over");
    }
    List<Treasure> result = temp.pickTreausre();
    if (result.size() == 0) {
      return "There is nothing to Pick Up \n";
    }
    p.pickTreasure(result);
    return String.format("You Pick Treasure: %s \n",  result.toString());
  }

  @Override
  public String shoot(Direction d, int distance) {
    if (gameOver) {
      throw new IllegalStateException("no move can be performed after game over");
    }
    if (d == null || distance < 0) {
      throw new IllegalArgumentException("invalid distance or direction");
    }
    if (!p.hasArrow()) {
      return "You are out of arrows, explore to find more\n";
    }
    if (!p.shoot()) {
      return "You are out of arrows, explore to find more\n";
    }
    String result = "You shoot an arrow into the darkness\n";
    if (temp.shoot(d, distance)) {
      result = "You hear a great howl in the distance\n";
    }
    if (!p.hasArrow()) {
      result += "You are out of arrows, explore to find more\n";
    }
    return result;
  }

  @Override
  public boolean gameOver() {
    return this.gameOver;
  }

  @Override
  public List<Direction> getPossibeMove() {
    return temp.getPossibeMove();
  }

  @Override
  public int getID() {
    return temp.getID();
  }

  @Override
  public boolean isEnd() {
    return temp.isEnd();
  }

  @Override
  public boolean isStart() {
    return temp.isStart();
  }

  @Override
  public String dungeonToSign() {
    return temp.toSign();
  }

  @Override
  public String playerToSign() {
    return p.toSign();
  }

  @Override
  public boolean hasMonster() {
    return temp.hasMonster();
  }

  @Override
  public boolean hasTreasure() {
    return temp.hasTreasure();
  }

  @Override
  public boolean hasWeakSmell() {
    return temp.hasWeakSmell();
  }

  @Override
  public boolean hasStrongSmell() {
    return temp.hasStrongSmell();
  }

  @Override
  public String win() {
    gameOver = true;
    return "You win the game and leave: \n" + p.toSign();
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public boolean getWrapping() {
    return this.wrapping;
  }

  @Override
  public Stack<Integer> getTestMode() {
    Stack<Integer> copy_Stack = (Stack<Integer>) testMode.clone();
    return copy_Stack;
  }

  @Override
  public int getInterconnectivity() {
    return this.interconnectivity;
  }

  @Override
  public int getTreasure() {
    return this.treasure;
  }

  @Override
  public int getNumExtraMonster() {
    return this.numExtraMonster;
  }

  @Override
  public int getColumn() {
    return column;
  }
}
