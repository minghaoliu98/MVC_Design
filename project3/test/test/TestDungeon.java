package test;

import controller.DungeonController;
import graphic.GraphicDungeonController;
import graphic.InterfaceGraphicDungeonController;
import treasure.Direction;
import dungeon.Dungeon;
import dungeon.DungeonInterface;
import game.Game;
import game.GameInterface;
import org.junit.Assert;
import org.junit.Test;
import view.DungeonView;
import view.DungeonViewInterface;

import java.io.StringReader;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * this is the testing class of dungeon.
 *
 */
public class TestDungeon {

  @Test
  public void controllerTestMoveAndWin() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 0;
    boolean wrapping = false;
    GameInterface model = new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1");
    DungeonViewInterface view = new DungeonView(model);
    InterfaceGraphicDungeonController controller = new GraphicDungeonController(model, view);
    controller.move(Direction.NORTH);
    controller.move(Direction.NORTH);
    controller.move(Direction.EAST);
    assertEquals(1,model.getID());
    controller.move(Direction.EAST);
    assertEquals(2,model.getID());
    controller.move(Direction.WEST);
    assertEquals(1,model.getID());
    controller.move(Direction.SOUTH);
    assertEquals(4,model.getID());

    controller.shoot("S", "1");
    controller.shoot("S", "1");
    controller.move(Direction.SOUTH);
    assertEquals(7,model.getID());
    assertTrue(model.isEnd());
    assertFalse(model.gameOver());
    model.win();
    assertTrue(model.gameOver());
    assertFalse(model.hasMonster());
  }

  @Test
  public void controllerTestMoveAndDie() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    GameInterface model = new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1");
    DungeonViewInterface view = new DungeonView(model);
    InterfaceGraphicDungeonController controller = new GraphicDungeonController(model, view);
    assertEquals(6,model.getID());
    controller.move(Direction.NORTH);
    assertEquals(3,model.getID());
    controller.move(Direction.SOUTH);
    assertEquals(6,model.getID());
    controller.move(Direction.NORTH);
    controller.move(Direction.NORTH);
    assertEquals(0,model.getID());
    controller.move(Direction.EAST);
    assertEquals(1,model.getID());
    controller.move(Direction.EAST);
    assertEquals(1,model.getID());
    assertTrue(model.gameOver());
    assertTrue(model.hasMonster());
  }


  @Test
  public void controllerTestShootAndPick() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    GameInterface model = new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1");
    DungeonViewInterface view = new DungeonView(model);
    InterfaceGraphicDungeonController controller = new GraphicDungeonController(model, view);
    controller.move(Direction.NORTH);
    assertEquals("player 1: {ARROW=3}", model.playerToSign());
    controller.shoot("N", "2");
    assertEquals("player 1: {ARROW=2}", model.playerToSign());
    controller.move(Direction.NORTH);
    assertTrue(model.hasStrongSmell());
    controller.shoot("adsfsafsfdsE", "-1");
    assertEquals("player 1: {ARROW=2}", model.playerToSign());
    controller.shoot("E", "-1.234");
    assertEquals("player 1: {ARROW=2}", model.playerToSign());
    controller.shoot("E", "dadfsdf");
    assertEquals("player 1: {ARROW=2}", model.playerToSign());

    controller.shoot("E", "1");
    assertFalse(model.hasStrongSmell());
    assertEquals("player 1: {ARROW=1}", model.playerToSign());

    controller.shoot("W", "1");
    assertEquals("player 1: {ARROW=0}", model.playerToSign());
    controller.shoot("W", "1");
    assertEquals("player 1: {ARROW=0}", model.playerToSign());

    controller.move(Direction.EAST);
    controller.pick();
    controller.shoot("S", "0");
    assertEquals("player 1: {ARROW=0, RUBIE=1, DIAMOND=1, SAPPHIRE=1}", model.playerToSign());

    controller.pick();
    assertEquals("player 1: {ARROW=0, RUBIE=1, DIAMOND=1, SAPPHIRE=1}", model.playerToSign());

  }

  @Test
  public void shootInFourDirection() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 0;
    boolean wrapping = false;
    StringReader input = new StringReader("S N 1 S S 1 S W 1 S E 1 P S E 1");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)No. of " +
        "caves?You shoot an arrow into the darkness\n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)No. of " +
        "caves?You shoot an arrow into the darkness\n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)No. " +
        "of caves?You shoot an arrow into the darkness\n" +
        "You are out of arrows, explore to find more\n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)No. of caves?" +
        "You are out of arrows, explore to find more\n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?You Pick Treasure: [RUBIE, SAPPHIRE, DIAMOND, ARROW] \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)" +
        "No. of caves?You shoot an arrow into the darkness\n" +
        "You are out of arrows, explore to find more\n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?", output.toString());
  }

  @Test
  public void moveInFourDirection() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 0;
    boolean wrapping = false;
    StringReader input = new StringReader("M N M S M N M E M W M E");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: S \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: E \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: W \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: E \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?", output.toString());
  }


  @Test
  public void testInvalidShoot() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    StringReader input = new StringReader("S 134214 ---- p zcvxz @#$%^ NORTH 1 " +
        "Move -1 N ---- p zcvxz @#$%^ NORTH 1 Move");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)invalid direction " +
        "!!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?invalid direction !!! Please Enter a New Shooting Direction \n" +
        "Where to shoot?No. of caves?invalid shooting distance !!! Please Enter Again \n" +
        "No. of caves?invalid shooting distance !!! Please Enter Again \n" +
        "No. of caves?invalid shooting distance !!! Please Enter Again \n" +
        "No. of caves?invalid shooting distance !!! Please Enter Again \n" +
        "No. of caves?invalid shooting distance !!! Please Enter Again \n" +
        "No. of caves?You shoot an arrow into the darkness\n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?" +
        "invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?", output.toString());

  }

  @Test
  public void testInvalidOperation() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    StringReader input = new StringReader(" 134214 ---- p zcvxz " +
        "@#$%^ NORTH 1 Move L M N");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?You Pick Treasure: " +
        "[RUBIE, SAPPHIRE, DIAMOND, ARROW] \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?invalid command !!! Please Enter Again\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?", output.toString());
  }

  @Test
  public void testInvalidMove() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    StringReader input = new StringReader("M 134214 ---- p q zcvxz @#$%^ NORTH N");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)invalid direction " +
        "!!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?invalid direction !!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?invalid direction !!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?invalid direction !!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?invalid direction !!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?invalid direction !!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?invalid direction !!! Please Enter a New Moving Direction Again \n" +
        "Where to Go?Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?", output.toString());
  }

  @Test
  public void testQuit() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    StringReader input = new StringReader("Q M N");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?", output.toString());
  }

  @Test
  public void testLoseInController() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 100;
    boolean wrapping = false;
    StringReader input = new StringReader("P M N M N M E");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?You Pick Treasure: [RUBIE, SAPPHIRE, DIAMOND, ARROW] \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 0 || possible moves: [E, S] \n" +
        "Treasures: []You smell something terrible nearby\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: E \n" +
        "\n" +
        "\n" +
        "you are eaten by an Otyugh!\n" +
        "Better luck next time\n" +
        "\n", output.toString());
  }

  @Test
  public void testWinInControllerEscapeMonsterAttack() {
    int column = 3;
    int row = 3;
    int interconnectivity = 0;
    int treasure = 100;
    int numExtraMonster = 0;
    boolean wrapping = false;
    StringReader input = new StringReader("M N M N M E M S S S 1 M S L");
    StringBuffer output = new StringBuffer();
    new DungeonController(input, output).playGame(new Game(row, column, interconnectivity,
        wrapping, numExtraMonster, testMode(), treasure,"player 1"));
    assertEquals("Welcome To Dungeon \n" +
        "\n" +
        "You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: N \n" +
        "\n" +
        "You are in a tunnel ID: 0 || possible moves: [E, S] \n" +
        "Treasures: []\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: E \n" +
        "\n" +
        "You are in a cave ID: 1 || possible moves: [E, S, W] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: S \n" +
        "\n" +
        "You are in a tunnel ID: 4 || possible moves: [N, S] \n" +
        "Treasures: []You smell something terrible nearby\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Shoot? (N-S-E-W)" +
        "No. of caves?You hear a great howl in the distance\n" +
        "\n" +
        "You are in a tunnel ID: 4 || possible moves: [N, S] \n" +
        "Treasures: []You smell something terrible nearby\n" +
        "Move, Pickup, or Shoot (M-P-S)?Where to Go? (N-S-E-W)Move: S \n" +
        "\n" +
        "\n" +
        "you are hit by an Otyugh!\n" +
        "You are lucky and evades the attack\n" +
        "\n" +
        "You are in a cave ID: 7 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]You smell something terrible nearby\n" +
        "You reach the end\n" +
        "Move, Pickup, Shoot or Leave Dungeon (M-P-S-L)?You win the game and leave: \n" +
        "player 1: {ARROW=2}\n", output.toString());
  }



  @Test
  public void unWrappedDungeonGameOver() {
    GameInterface g = new Game(3, 3, 1,
        true, 0,testMode(), 0,"player 1");
    assertTrue(g.isStart());
    g.move(Direction.NORTH);
    g.move(Direction.NORTH);
    g.move(Direction.EAST);
    g.move(Direction.SOUTH);
    g.move(Direction.SOUTH);
    assertTrue(g.gameOver());
    assertTrue(g.isEnd());
  }

  @Test(expected = IllegalStateException.class)
  public void moveAfterGameOver() {
    GameInterface g = new Game(3, 3, 1,
        true, 0,testMode(), 0,"player 1");
    assertTrue(g.isStart());
    g.move(Direction.NORTH);
    g.move(Direction.NORTH);
    g.move(Direction.EAST);
    g.move(Direction.SOUTH);
    g.move(Direction.SOUTH);
    assertTrue(g.gameOver());
    assertTrue(g.isEnd());
    g.move(Direction.SOUTH);
  }

  @Test
  public void reachable() {
    for (int i = 10; i < 20; i++) {
      DungeonInterface temp1 = new Dungeon(i, i, 3, true, testMode());
      DungeonInterface temp2 = new Dungeon(i, i, 3, false, testMode());
      HashSet<Integer> r1 = new HashSet<>();
      HashSet<Integer> r2 = new HashSet<>();
      numberOfTreasure(temp1, r1);
      numberOfTreasure(temp2, r2);
      HashSet<Integer> sample = new HashSet<>();
      for (int j = 0; j < i * i; j ++) {
        sample.add(j);
      }
      Assert.assertEquals(sample, r1);
      Assert.assertEquals(sample, r2);
    }
  }

  @Test
  public void testChanceEvadeDamagedMonster() {
    int count = 0;
    for (int i = 0; i <= 1000; i ++) {
      Stack<Integer> testMode = new Stack<>();
      for (int j = 0; j < 35; j ++) {
        testMode.add(0);
      }
      GameInterface g = new Game(3, 3, 1,
          false, 3, testMode, 0,"player 1");
      g.shoot(Direction.EAST, 1);
      g.move(Direction.EAST);
      if (g.gameOver()) {
        count += 1;
      }
    }
    assertTrue(count < 550);
    assertTrue(count > 450);


  }

  @Test
  public void testSmell() {
    DungeonInterface temp = new Dungeon(4, 4, 4, false, testMode());
    temp.addTreasureAndMonster(100, 3);
    assertTrue(temp.hasStrongSmell());
    assertFalse(temp.hasWeakSmell());

    temp.move(Direction.SOUTH);
    assertTrue(temp.hasWeakSmell());
    assertFalse(temp.hasStrongSmell());

    temp.move(Direction.SOUTH);
    assertFalse(temp.hasWeakSmell());
    assertFalse(temp.hasStrongSmell());

  }

  @Test
  public void testShootInDungeon() {
    DungeonInterface temp = new Dungeon(3, 3, 0, false, testMode());
    temp.addTreasureAndMonster(100, 100);
    assertEquals("You are in a cave ID: 6 || possible moves: [N] \n" +
        "Treasures: [RUBIE, SAPPHIRE, DIAMOND, ARROW]", temp.toSign());
    temp.move(Direction.NORTH);
    assertEquals("You are in a tunnel ID: 3 || possible moves: [N, S] \n" +
        "Treasures: []", temp.toSign());
    assertTrue(temp.shoot(Direction.NORTH, 2));
    temp.move(Direction.NORTH);
    assertTrue(temp.hasStrongSmell());
    assertEquals("You are in a tunnel ID: 0 || possible moves: [E] \n" +
        "Treasures: []You smell something terrible nearby" , temp.toSign());
    assertFalse(temp.shoot(Direction.SOUTH, 100));
    assertFalse(temp.shoot(Direction.EAST, 2000));
    assertFalse(temp.shoot(Direction.WEST, 2000));
    assertFalse(temp.shoot(Direction.NORTH, 100));

    assertTrue(temp.shoot(Direction.EAST, 1));

    assertEquals("You are in a tunnel ID: 0 || possible moves: [E] \n" +
        "Treasures: []", temp.toSign());
  }

  @Test
  public void testTreasureAndMonster() {
    for (int i = 10; i < 20; i++) {
      DungeonInterface temp = new Dungeon(i, i, 3, true, testMode());
      Random rand = new Random();
      int chance = rand.nextInt(101);
      temp.addTreasureAndMonster(chance, 10);
      assertTrue(1.0 * numberOfCave(temp, new HashSet<>()) * chance / 100
          <= numberOfTreasure(temp, new HashSet<>()));
      assertEquals(11, numberOfMonster(temp, new HashSet<>()));
    }
  }

  private int numberOfTreasure(DungeonInterface temp, Set<Integer> visited) {
    if (visited.contains(temp.getID())) {
      return 0;
    }
    int count = 0;
    if (temp.pickTreausre().size() > 0) {
      count ++;
    }
    visited.add(temp.getID());
    DungeonInterface a = temp.copy();
    a.move(Direction.NORTH);
    DungeonInterface b = temp.copy();
    b.move(Direction.SOUTH);
    DungeonInterface c = temp.copy();
    c.move(Direction.WEST);
    DungeonInterface d = temp.copy();
    d.move(Direction.EAST);
    return count + numberOfTreasure(a, visited) + numberOfTreasure(b, visited) +
        numberOfTreasure(c, visited) + numberOfTreasure(d, visited);
  }

  private int numberOfMonster(DungeonInterface temp, Set<Integer> visited) {
    if (visited.contains(temp.getID())) {
      return 0;
    }
    int count = 0;
    if (temp.getMonsterHealth() > 0) {
      count ++;
    }
    visited.add(temp.getID());
    DungeonInterface a = temp.copy();
    a.move(Direction.NORTH);
    DungeonInterface b = temp.copy();
    b.move(Direction.SOUTH);
    DungeonInterface c = temp.copy();
    c.move(Direction.WEST);
    DungeonInterface d = temp.copy();
    d.move(Direction.EAST);
    return count + numberOfMonster(a, visited) + numberOfMonster(b, visited) +
        numberOfMonster(c, visited) + numberOfMonster(d, visited);
  }

  private int numberOfCave(DungeonInterface temp, Set<Integer> visited) {
    if (visited.contains(temp.getID())) {
      return 0;
    }
    int count = 0;
    if (temp.isCave()) {
      count ++;
    }
    visited.add(temp.getID());
    DungeonInterface a = temp.copy();
    a.move(Direction.NORTH);
    DungeonInterface b = temp.copy();
    b.move(Direction.SOUTH);
    DungeonInterface c = temp.copy();
    c.move(Direction.WEST);
    DungeonInterface d = temp.copy();
    d.move(Direction.EAST);
    return count + numberOfCave(a, visited) + numberOfCave(b, visited) +
        numberOfCave(c, visited) + numberOfCave(d, visited);
  }

  @Test
  public void testDistance() {
    for (int i = 0; i < 1000; i++) {
      DungeonInterface temp = new Dungeon(5, 5, 3, true, testMode());
      temp.addTreasureAndMonster(50, 0);
      assertFalse(helper(temp, new HashSet<>(), 5));
    }
  }

  @Test (expected = IllegalArgumentException.class)
  public void testDistanceException() {
    DungeonInterface temp = new Dungeon(3, 3, 10000, true, testMode());
  }

  private boolean helper(DungeonInterface temp, Set<Integer> visited, int index) {
    if (index == 0 || visited.contains(temp.getID())) {
      return false;
    }
    if (temp.isEnd()) {
      return true;
    }
    visited.add(temp.getID());
    DungeonInterface a = temp.copy();
    a.move(Direction.NORTH);
    DungeonInterface b = temp.copy();
    b.move(Direction.SOUTH);
    DungeonInterface c = temp.copy();
    c.move(Direction.WEST);
    DungeonInterface d = temp.copy();
    d.move(Direction.EAST);

    return helper(a, visited,  index - 1) || helper(b, visited,  index - 1)
        || helper(c, visited,  index - 1) || helper(d, visited,  index - 1);
  }


  private Stack<Integer> testMode() {
    Stack<Integer> result = new Stack<>();
    for (int i = 0; i < 1000; i ++) {
      result.add(0);
    }
    return result;
  }

}
