package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class PromotionResponse extends MoveResponse {
  private Action action;
  private char piece;

  public PromotionResponse(Action action) {
    super(Move.Type.PROMOTION);
    this.action = action;
    this.piece = piece;
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public char getPiece() {
    return piece;
  }

  public void setPiece(char piece) {
    this.piece = piece;
  }

  @Override
  public String toString() {
    return super.toString() + " [" + action + piece + "]";
  }
}
