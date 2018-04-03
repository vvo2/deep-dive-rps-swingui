package edu.cnm.deepdive.rps;

import edu.cnm.deepdive.rps.controller.Player;

public class Main {

  public static void main(String[] args) {
    Player player = new Player();
    player.init();
    player.start();
  }
}
