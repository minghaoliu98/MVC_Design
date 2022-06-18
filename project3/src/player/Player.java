package player;

import treasure.Treasure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this is the player object represented by name and treasures bag.
 */
public class Player implements PlayerInterface {

  private final String name;
  private final Map<Treasure, Integer> treasureList;

  /**
   * constructor for player.
   * @param name the name of player
   * @throws IllegalArgumentException if name is empty
   */
  public Player(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("invalid name, name is null");
    }
    this.name = name;
    treasureList = new HashMap<>();
    treasureList.put(Treasure.ARROW, 3);
  }

  @Override
  public void pickTreasure(List<Treasure> treasureList) {
    if (treasureList == null) {
      throw new IllegalArgumentException("treasure list is null");
    }
    for (Treasure t : treasureList) {
      this.treasureList.put(t, this.treasureList.getOrDefault(t, 0) + 1);
    }
  }

  @Override
  public String toSign() {
    return  String.format("%s: %s", name, treasureList.toString());
  }

  @Override
  public boolean shoot() {
    if (hasArrow()) {
      treasureList.put(Treasure.ARROW, treasureList.get(Treasure.ARROW) - 1);
      return true;
    }
    return false;
  }

  @Override
  public boolean hasArrow() {
    return treasureList.get(Treasure.ARROW) > 0;
  }
}
