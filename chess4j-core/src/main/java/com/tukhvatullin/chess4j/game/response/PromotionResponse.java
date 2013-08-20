package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class PromotionResponse extends MoveResponse {
  private Action action;
  public PromotionResponse(Action action) {
    super(Move.Type.PROMOTION);
    this.action = action;
  }

  public Action getAction() {
    return action;
  }

  @Override
  public String toString() {
    return super.toString()+" ["+action+"]";
  }
}
