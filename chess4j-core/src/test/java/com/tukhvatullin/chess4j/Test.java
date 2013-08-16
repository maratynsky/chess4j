package com.tukhvatullin.chess4j;

import com.tukhvatullin.chess4j.game.Game;

/**
 * Date: 4/1/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Test {

  public static void main(String[] args) {
    Game game = new Game();

    Turn[] turns = new Turn[] {
        new Turn("e2e4"),
        new Turn("a7a5"),
        new Turn("e4e5"),
        new Turn("d7d5"),
        new Turn("e5d6"),
        new Turn("g8h6"),
        new Turn("d1h5"),
        new Turn("a5a4"),
        new Turn("f1d3"),
        new Turn("a4a3"),
        new Turn("g1h3"),
        new Turn("a3b2"),
        new Turn("e1g1"),
        new Turn("b2a1"),
        new Turn("a2a3"),
        new Turn("a1b1"),
        new Turn("c2c3"),
        new Turn("e7e6"),
        new Turn("c3c4"),
        new Turn("f8e7"),
        new Turn("d6d7"),
        new Turn("h8g8"),

    };

    for (Turn turn : turns) {
      turn.move(game);
      System.out.println(game.getBoard().toString());
    }
  }

  static class Turn {
    char cf;
    int rf;
    char ct;
    int rt;

    public Turn(String notation) {
      cf = notation.charAt(0);
      rf = notation.charAt(1) - '1' + 1;
      ct = notation.charAt(2);
      rt = notation.charAt(3) - '1' + 1;
    }

    public void move(Game game) {
      game.move(cf, rf, ct, rt);
    }
  }
}
