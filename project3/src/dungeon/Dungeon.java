package dungeon;

import treasure.Direction;
import treasure.Treasure;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;




/**
 * This represents the dungeon in dungeon by
 * the row and column number, start and end of Dungeon.
 */
public class Dungeon implements DungeonInterface {

  private Stack<Integer> testMode;
  private boolean wrapping;
  private int row;
  private int column;
  private int interconnectivity;
  private final ElementNodeInterface start;
  private ElementNodeInterface current;
  private final ElementNodeInterface end;


  private Dungeon(ElementNodeInterface start,
                  ElementNodeInterface current, ElementNodeInterface end) {
    this.start = start;
    this.current = current;
    this.end = end;
  }

  /**
   * constructor of the Dungeon.
   * @param row number of row in dungeon
   * @param column number of column in dungeon
   * @param interconnectivity the interconnecticity of dungeon
   * @param wrapping if the dungeon is wrapped
   * @throws IllegalArgumentException when parameter is negative or below 1 or 2
   */
  public Dungeon(int row, int column, int interconnectivity,
                 boolean wrapping) throws IllegalArgumentException {
    this(row, column, interconnectivity, wrapping, new Stack<>());
  }

  /**
   * constructor of the Dungeon with testMode option.
   * @param row number of row in dungeon
   * @param column number of column in dungeon
   * @param interconnectivity the interconnecticity of dungeon
   * @param wrapping if the dungeon is wrapped
   * @param testMode if it is testMode
   * @throws IllegalArgumentException when parameter is negative or below 1 or 2
   */
  public Dungeon(int row, int column, int interconnectivity,
                 boolean wrapping, Stack<Integer> testMode) throws IllegalArgumentException {
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
    this.row = row;
    this.column = column;
    this.testMode = testMode;
    this.interconnectivity = interconnectivity;
    this.wrapping = wrapping;
    Map<Integer, List<Integer>> result = createDungeonConnectionHelper();
    Map<Integer, ElementNodeInterface> dungeonObject = new HashMap<>();
    for (int i = 0; i < row; i ++) {
      for (int j = 0; j < column; j++) {
        dungeonObject.put(toMarker(i, j), new ElementNode(toMarker(i, j)));
      }
    }

    List<Integer> caves = new ArrayList<>();
    for (int i : result.keySet()) {
      if (result.get(i).size() != 2) {
        caves.add(i);
      }
    }
    int startNode = -1;
    int theSelectedEnd = -1;
    while (caves.size() > 0) {
      int index = randHelper(caves.size());
      startNode = caves.get(index);
      caves.remove(index);
      theSelectedEnd = fiveStepsChecker(startNode, result, caves);
      if (theSelectedEnd != -1) {
        break;
      }
    }
    if (theSelectedEnd == -1) {
      throw new IllegalArgumentException("dungeon isn't " +
          "big enough to create a end with 5 steps distance from beginning");
    }
    start = dungeonObject.get(startNode);
    end = dungeonObject.get(theSelectedEnd);
    current = start;

    for (int i : result.keySet()) {
      dungeonObject.get(i);
      int currentRow = toRow(i);
      int currentColumn = toColumn(i);
      for (int j : result.get(i)) {
        int targetRow = toRow(j);
        int targetColumn = toColumn(j);
        if (currentRow == targetRow) {
          if (currentColumn + 1 == targetColumn ||
              currentColumn - column + 1 == targetColumn) {
            dungeonObject.get(i).add((ElementNode) dungeonObject.get(j),Direction.EAST);
          } else {
            dungeonObject.get(i).add((ElementNode) dungeonObject.get(j),Direction.WEST);
          }
        } else if (currentColumn == targetColumn) {
          if (currentRow + 1 == targetRow
              || currentRow - row + 1 == targetRow) {
            dungeonObject.get(i).add((ElementNode) dungeonObject.get(j),Direction.SOUTH);
          } else {
            dungeonObject.get(i).add((ElementNode) dungeonObject.get(j),Direction.NORTH);
          }
        }
      }
    }
  }

  private Map<Integer, List<Integer>> createDungeonConnectionHelper() {
    Map<Integer, List<Integer>> dungeonMap = new HashMap<>();
    Map<Integer, List<Integer>> result = new HashMap<>();
    Map<Integer, List<Integer>> used = new HashMap<>();
    List<Set<Integer>> sample = new ArrayList<>();

    for (int i = 0; i < row; i ++) {
      for (int j = 0; j < column;  j++) {
        List<Integer> dungeonMapSub = new ArrayList<>();
        Set<Integer> sampleSub = new HashSet<>();

        if (i != row - 1 ) {
          dungeonMapSub.add(toMarker(i + 1, j));
        } else if (wrapping) {
          dungeonMapSub.add(toMarker(0, j));
        }
        if (j != column - 1 ) {
          dungeonMapSub.add(toMarker(i, j + 1));
        } else if (wrapping) {
          dungeonMapSub.add(toMarker(i, 0));
        }
        if (dungeonMapSub.size() != 0) {
          dungeonMap.put(toMarker(i, j), dungeonMapSub);
        }
        sampleSub.add(toMarker(i, j));
        sample.add(sampleSub);
      }
    }

    while (dungeonMap.size() != 0) {
      List<Integer> dungeonMapKeySet = new ArrayList<>(dungeonMap.keySet());
      int dungeonFrom = dungeonMapKeySet.get(randHelper(dungeonMapKeySet.size()));

      int dungeonToIndex = randHelper(dungeonMap.get(dungeonFrom).size());
      int dungeonTo = dungeonMap.get(dungeonFrom).get(dungeonToIndex);


      dungeonMap.get(dungeonFrom).remove(dungeonToIndex);
      if (dungeonMap.get(dungeonFrom).size() == 0) {
        dungeonMap.remove(dungeonFrom);
      }

      int dungeonFromSampleIndex = -1;
      int dungeonToSampleIndex = -1;
      for (int i = 0; i < sample.size(); i ++) {
        if (sample.get(i).contains(dungeonFrom)) {
          dungeonFromSampleIndex = i;
        }
        if (sample.get(i).contains(dungeonTo)) {
          dungeonToSampleIndex = i;
        }
      }

      if (dungeonFromSampleIndex == dungeonToSampleIndex) {
        //add dungeonFrom/to to the used
        if (!used.containsKey(dungeonFrom)) {
          used.put(dungeonFrom, new ArrayList<>());
        }
        used.get(dungeonFrom).add(dungeonTo);
      } else {
        //merge two sets
        sample.get(dungeonFromSampleIndex).
            addAll(sample.get(dungeonToSampleIndex));
        sample.remove(dungeonToSampleIndex);
        //add dungeonFrom/to to the result
        if (!result.containsKey(dungeonFrom)) {
          result.put(dungeonFrom, new ArrayList<>());
        }
        result.get(dungeonFrom).add(dungeonTo);
      }
    }
    for (int i = 0; i < interconnectivity; i ++) {
      if (used.size() == 0) {
        break;
      }
      List<Integer> usedKeySet = new ArrayList<>(used.keySet());
      int usedFromIndex = randHelper(usedKeySet.size());
      int usedFrom = usedKeySet.get(usedFromIndex);

      int usedToIndex = randHelper(used.get(usedFrom).size());
      int usedTo = used.get(usedFrom).get(usedToIndex);

      used.get(usedFrom).remove(usedToIndex);
      if (used.get(usedFrom).size() == 0) {
        used.remove(usedFrom);
      }
      if (!result.containsKey(usedFrom)) {
        result.put(usedFrom, new ArrayList<>());
      }
      result.get(usedFrom).add(usedTo);
    }
    List<Integer> resultKeySet = new ArrayList<>(result.keySet());
    for (int i : resultKeySet) {
      for (int j : result.get(i)) {
        if (!result.containsKey(j)) {
          result.put(j, new ArrayList<>());
        }
        if (!result.get(j).contains(i)) {
          result.get(j).add(i);
        }
      }
    }
    return result;
  }

  private int fiveStepsChecker(int startNode,
                               Map<Integer, List<Integer>> result, List<Integer> caves) {
    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();
    Map<Integer, Integer> minStep = new HashMap<>();
    queue.add(startNode);
    minStep.put(startNode, 0);
    while (queue.size() > 0) {
      int currentNode = queue.poll();
      for (int i : result.get(currentNode)) {
        if (!visited.contains(i)) {
          visited.add(i);
          queue.add(i);
          if (minStep.containsKey(i)) {
            if ( minStep.get(currentNode) + 1 < minStep.get(i)) {
              minStep.put(i, minStep.get(currentNode) + 1);
            }
          } else {
            minStep.put(i, minStep.get(currentNode) + 1);
          }
        }
      }
    }
    for (int i : caves) {
      if (minStep.get(i) >= 5) {
        return i;
      }
    }
    return -1;
  }

  private int toMarker(int row, int column) {
    return row * this.column + column;
  }

  private int toRow(int marker) {
    return marker / column;
  }

  private int toColumn(int marker) {
    return marker % column;
  }

  @Override
  public int randHelper(int result) {
    if (testMode.size() > 0) {
      return testMode.pop();
    }
    Random rand = new Random();
    int randomNum = rand.nextInt(result);
    return randomNum;
  }

  @Override
  public boolean hasMonster() {
    return current.hasMonster();
  }

  @Override
  public boolean hasTreasure() {
    return current.hasTreasure();
  }

  @Override
  public boolean hasWeakSmell() {
    return current.hasWeakSmell();
  }

  @Override
  public boolean hasStrongSmell() {
    return current.hasStrongSmell();
  }


  @Override
  public void addTreasureAndMonster(int percentage, int monster) {
    if (percentage < 0 || monster < 0) {
      throw new IllegalArgumentException(
          "percentage or monster can't be negative");
    }
    if (percentage > 100) {
      throw new IllegalArgumentException(
          "percentage can't be larger than 100");
    }
    List<Integer> treasureList = new ArrayList<>();
    caveFinder(treasureList, current, new HashSet<>());
    List<Integer> monsterList = new ArrayList<>(treasureList);
    treasureList.add(start.getID());
    treasureList.add(end.getID());

    int numMonsterToRemove = monsterList.size() - monster;
    int numTreaures = (int) Math.floor(
        1.0 * treasureList.size() * (1 - percentage * 1.0 / 100));

    for (int i = 0; i < numMonsterToRemove; i ++) {
      monsterList.remove(randHelper(monsterList.size()));
    }
    for (int i = 0; i < numTreaures; i ++) {
      treasureList.remove(randHelper(treasureList.size()));
    }
    monsterList.add(end.getID());
    treasureSetter(new HashSet<>(treasureList),
        new HashSet<>(monsterList), current, new HashSet<>());
  }

  private void caveFinder(List<Integer> result,
                          ElementNodeInterface checker, Set<Integer> visited) {
    if (checker != null && !visited.contains(checker.getID())) {
      visited.add(checker.getID());
      if (checker.isCave() && checker != start && checker != end) {
        result.add(checker.getID());
      }
      for (Direction d : Direction.values()) {
        caveFinder(result, checker.move(d), visited);
      }
    }
  }

  private void treasureSetter(Set<Integer> treasureList,
                              Set<Integer> monsterList,
                              ElementNodeInterface checker, Set<Integer> visited) {
    if (checker != null && !visited.contains(checker.getID())) {
      visited.add(checker.getID());
      if (treasureList.contains(checker.getID())) {
        List<Treasure> treasures = new ArrayList<Treasure>();
        treasures.add(Treasure.RUBIE);
        treasures.add(Treasure.SAPPHIRE);
        treasures.add(Treasure.DIAMOND);
        treasures.add(Treasure.ARROW);
        checker.putTreasure(treasures);
      }
      if (monsterList.contains(checker.getID())) {
        checker.putMonster();
      }
      for (Direction d : Direction.values()) {
        treasureSetter(treasureList, monsterList, checker.move(d), visited);
      }
    }
  }


  @Override
  public String toSign() {
    String result = "";
    if (isEnd()) {
      result = "\nYou reach the end";
    }
    return current.toSign() + result;
  }


  @Override
  public void move(Direction d) {
    current = current.move(d);
  }

  @Override
  public List<Treasure> pickTreausre() {

    return current.getTreasure();
  }

  @Override
  public List<Direction> getPossibeMove() {
    return current.getPossibeMove();
  }

  @Override
  public boolean isEnd() {
    return current == end;
  }

  @Override
  public boolean isStart() {
    return current == start;
  }

  @Override
  public int getID() {
    return current.getID();
  }

  @Override
  public DungeonInterface copy() {
    return new Dungeon(start, current, end);
  }

  @Override
  public boolean isCave() {
    return current.isCave();
  }

  @Override
  public int getMonsterHealth() {
    return current.getMonsterHealth();
  }

  @Override
  public boolean shoot(Direction d, int distance) {
    if (d == null || distance < 0) {
      throw new IllegalArgumentException("invalid distance or direction");
    }
    List<Direction> temp = current.getPossibeMove();
    for (Direction tempD : temp) {
      if (d == tempD) {
        return current.shoot(d, distance);
      }
    }
    return false;
  }
}
