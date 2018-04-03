package edu.cnm.deepdive.rps.model;

import java.util.Random;

/**
 *
 */
public class Terrain {

  /** */
  public static final int DEFAULT_SIZE = 50;

  private Random rng;
  private int size;
  private Breed[][] population;
  private long steps;

  /**
   *
   */
  public Terrain() {
    this(DEFAULT_SIZE);
  }

  /**
   *
   * @param size
   */
  public Terrain(int size) {
    this.size = size;
    population = new Breed[size][size];
    rng = new Random();
    reset();
  }

  /**
   *
   */
  public void reset() {
    for (Breed[] row : population) {
      for (int i = 0; i < row.length; i++) {
        row[i] = Breed.random(rng);
      }
    }
    steps = 0;
  }

  /**
   *
   */
  public void step() {
    int attackerRow = rng.nextInt(size);
    int attackerColumn = rng.nextInt(size);
    Breed attacker = population[attackerRow][attackerColumn];
    CardinalDirection attackDirection = CardinalDirection.random(rng);
    int defenderRow = (attackerRow + size + attackDirection.rowOffset) % size;
    int defenderColumn = (attackerColumn + size + attackDirection.columnOffset) % size;
    Breed defender = population[defenderRow][defenderColumn];
    int result = Breed.comparator.compare(attacker, defender);
    if (result < 0) {
      population[attackerRow][attackerColumn] = defender;
    } else if (result > 0) {
      population[defenderRow][defenderColumn] = attacker;
    }
    steps++;
  }

  /**
   *
   * @return
   */
  public int getSize() {
    return size;
  }

  /**
   *
   * @return
   */
  public Breed[][] getPopulation() {
    return population;
  }

  /**
   *
   * @return
   */
  public long getSteps() {
    return steps;
  }

}
