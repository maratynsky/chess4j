package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class CantMoveResponse extends MoveResponse {
  public CantMoveResponse() {
    super(Move.Type.CANTMOVE);
  }
}
