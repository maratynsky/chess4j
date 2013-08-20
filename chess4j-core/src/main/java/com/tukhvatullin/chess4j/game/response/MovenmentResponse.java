package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class MovenmentResponse extends MoveResponse {
  private Action action;
  public MovenmentResponse(Action action) {
    super(Move.Type.MOVENMENT);
    this.action = action;
  }

  public Action getAction() {
    return action;
  }
}
