package com.tukhvatullin.chess4j.game.response;


import com.tukhvatullin.chess4j.game.Move;

/**
 * Date: 4/5/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class MoveResponse {

  private Move.Type type;

  public MoveResponse(Move.Type type) {
    this.type = type;
  }

  public Move.Type getMoveType() {
    return type;
  }


}
