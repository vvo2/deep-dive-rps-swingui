package edu.cnm.deepdive.rps.model;

import java.util.Comparator;
import java.util.Random;

/**
 *
 */
public enum Breed {
  ROCK,  //ordinal value 1
  PAPER, //2
  SCISSORS; //3

  /** */
  public final static Comparator<Breed> comparator = (breed1, breed2) ->
      (Math.abs(breed1.ordinal() - breed2.ordinal()) == 2)
          ? breed2.compareTo(breed1)
          : breed1.compareTo(breed2);

  /**
   *
   * @param rng
   * @return
   */
  public static Breed random(Random rng) {
    Breed[] breeds = Breed.values();
    int numBreeds = breeds.length;
    return breeds[rng.nextInt(numBreeds)];
  }

}
