package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class CheckResponse extends MoveResponse {
  public CheckResponse() {
    super(Move.Type.CHECK);
  }
}
