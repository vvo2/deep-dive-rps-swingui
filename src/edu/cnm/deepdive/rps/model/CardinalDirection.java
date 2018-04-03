package edu.cnm.deepdive.rps.model;

import java.util.Random;

/**
 *
 */
public enum CardinalDirection {

  NORTH(-1, 0),
  EAST(0, 1),
  SOUTH(1, 0),
  WEST(0, -1);

  /** */
  public final int rowOffset;
  /** */
  public final int columnOffset;

  private CardinalDirection(int rowOffset, int columnOffset) {
    this.rowOffset = rowOffset;
    this.columnOffset = columnOffset;
  }

  /**
   *
   * @param rng
   * @return
   */
  public static CardinalDirection random(Random rng) {
    CardinalDirection[] directions = values();
    int numDirections = directions.length;
    return directions[rng.nextInt(numDirections)];
  }

}
