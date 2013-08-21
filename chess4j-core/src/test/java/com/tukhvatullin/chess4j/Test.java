package com.tukhvatullin.chess4j;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.response.MoveResponse;

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
        new Turn("b2a1n"),
        new Turn("a2a3"),
        new Turn("a1b1"),
        new Turn("c2c3"),
        new Turn("e7e6"),
        new Turn("c3c4"),
        new Turn("f8e7"),
        new Turn("d6d7"),
        new Turn("c8d7"),
        new Turn("d3c4"),
        new Turn("e6e5"),
        new Turn("a3a4"),
        new Turn("h6g4"),

        new Turn("h5f7"),

    };

    for (Turn turn : turns) {
      System.out.println("try: "+turn);
      MoveResponse moveResponse = turn.move(game);
      System.out.println(moveResponse);
      System.out.println(game.getBoard());
    }
  }

  static class Turn {
    char cf;
    int rf;
    char ct;
    int rt;
    char p;

    public Turn(String notation) {
      cf = notation.charAt(0);
      rf = notation.charAt(1) - '1' + 1;
      ct = notation.charAt(2);
      rt = notation.charAt(3) - '1' + 1;
      if(notation.length()==5){
        p = notation.charAt(4);
      }
    }

    @Override
    public String toString() {
      return new StringBuilder().append(cf).append(rf).append(ct).append(rt).toString();
    }

    public MoveResponse move(Game game) {

      return game.move(cf, rf, ct, rt, p);
    }
  }
}
