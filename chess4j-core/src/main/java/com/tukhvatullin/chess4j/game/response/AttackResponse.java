package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class AttackResponse extends MoveResponse {
  private Action action;
  public AttackResponse(Action action) {
    super(Move.Type.ATTACK);
    this.action = action;
  }

  public Action getAction() {
    return action;
  }
}
