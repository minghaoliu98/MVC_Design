package treasure;

/**
 * This is the direction enum class.
 */
public enum Direction {
  NORTH, SOUTH, WEST, EAST;

  @Override
  public String toString() {
    switch (this) {
      case NORTH: return "N";
      case SOUTH: return "S";
      case WEST: return "W";
      case EAST: return "E";
      default: throw new IllegalArgumentException();
    }
  }
}
