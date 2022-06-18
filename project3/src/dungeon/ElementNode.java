package dungeon;

import treasure.Direction;
import treasure.Treasure;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


/**
 * This is a non-empty node in the dungeon list. It contains the caveID, term data and
 * 4 direction. The entire class has no public method or object.
 * This is package private since it will only be accessed by Dungeon.
 */
class ElementNode implements ElementNodeInterface {
  private boolean isCave;
  private int monsterHeath;
  private final List<Treasure> treasures;
  private final Map<Direction, ElementNode> elementNodeMap;
  private final int id;

  /**
   * constructor for dungeon.
   * @param id id of dungeon
   */
  public ElementNode(int id) {
    this.id = id;
    this.isCave = false;
    this.treasures = new ArrayList<>();
    this.monsterHeath = 0;
    this.elementNodeMap = new HashMap<>();
  }

  @Override
  public void add(ElementNode temp, Direction d) {
    if (d == null || temp == null) {
      throw new IllegalArgumentException("null direction or dungeon");
    }
    elementNodeMap.put(d, temp);
    isCaveHelper();
  }

  @Override
  public String toSign() {
    String result = String.format("ID: %s || possible moves:" +
        " %s \nTreasures: %s",id , getPossibeMove().toString(), treasures.toString());
    if (isCave()) {
      result = "You are in a cave " + result;
    } else {
      result = "You are in a tunnel " + result;
    }
    if (this.hasStrongSmell()) {
      result += "You smell something terrible nearby";
    }
    if (this.hasWeakSmell()) {
      result += "You smell something terrible in a distance";
    }

    return result;
  }

  private void isCaveHelper() {
    isCave = elementNodeMap.size() != 2;
  }

  @Override
  public List<Direction> getPossibeMove() {
    return new ArrayList<>(elementNodeMap.keySet());
  }

  @Override
  public int getID() {
    return this.id;
  }

  @Override
  public boolean isCave() {
    return isCave;
  }

  @Override
  public int getMonsterHealth() {
    return monsterHeath;
  }

  @Override
  public boolean hasMonster() {
    return monsterHeath > 0;
  }

  @Override
  public void putMonster() {
    monsterHeath = 2;
  }

  @Override
  public void putTreasure(List<Treasure> treasures) {
    if (treasures == null) {
      throw new IllegalArgumentException("null treasure");
    }
    this.treasures.addAll(treasures);
  }

  @Override
  public ElementNode move(Direction d) {
    if (d == null) {
      throw new IllegalArgumentException("direction is null");
    }
    ElementNode temp = elementNodeMap.getOrDefault(d, null);
    if (temp != null) {
      return temp;
    }
    return this;
  }

  @Override
  public List<Treasure> getTreasure() {
    List<Treasure> result = new ArrayList<>(treasures);
    treasures.clear();
    return result;
  }

  @Override
  public boolean hasTreasure() {
    return treasures.size() > 0;
  }

  private int numberOfMonsterDistanceTwo(int distance, Set<Integer> visited) {
    if (!visited.contains(getID())) {
      visited.add(getID());
      if (distance == 0) {
        if (hasMonster()) {
          return 1;
        } else {
          return 0;
        }
      }
      int result = 0;
      if (hasMonster()) {
        result = 1;
      }
      for (Direction d : elementNodeMap.keySet()) {
        result += elementNodeMap.get(d).numberOfMonsterDistanceTwo(distance - 1, visited);
      }
      return result;
    }
    return 0;
  }

  @Override
  public boolean hasStrongSmell() {
    boolean result = hasMonster();
    for (Direction d : elementNodeMap.keySet()) {
      result = result || elementNodeMap.get(d).hasMonster();
    }
    return result;
  }

  @Override
  public boolean hasWeakSmell() {
    return numberOfMonsterDistanceTwo(2, new HashSet<Integer>()) >= 2;
  }

  @Override
  public boolean shoot(Direction d, int distance) {
    if (d == null) {
      throw new IllegalArgumentException("direction is null");
    }
    if (distance < 0) {
      throw new IllegalArgumentException("negative distance");
    }
    if (distance == 0) {
      if (monsterHeath > 0) {
        monsterHeath -= 1;
        return true;
      }
      return false;
    } else if (!isCave()) {
      Set<Direction> temp = elementNodeMap.keySet();
      if (temp.contains(d)) {
        return elementNodeMap.get(d).shoot(d, distance - 1);
      }
      temp.remove(getInverseDirection(d));
      Direction direction = null;
      for (Direction tempD : temp) {
        direction = tempD;
        break;
      }
      return elementNodeMap.get(direction).shoot(direction, distance - 1);
    } else {
      if (elementNodeMap.containsKey(d)) {
        return elementNodeMap.get(d).shoot(d,distance - 1);
      }
      return false;
    }
  }

  private Direction getInverseDirection(Direction d) {
    Map<Direction, Direction> temp = new HashMap<>();
    temp.put(Direction.NORTH, Direction.SOUTH);
    temp.put(Direction.SOUTH, Direction.NORTH);
    temp.put(Direction.EAST, Direction.WEST);
    temp.put(Direction.WEST, Direction.EAST);
    return temp.get(d);
  }
}
