package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class EnpassantResponse extends MoveResponse{
  private Action action;
  private Position pawn;
  public EnpassantResponse(Action action, Position pawn) {
    super(Move.Type.ENPASSANT);
    this.action = action;
    this.pawn = pawn;
  }

  public Action getAction() {
    return action;
  }

  public Position getPawn() {
    return pawn;
  }

  @Override
  public String toString() {
    return super.toString()+" ["+action+", -"+pawn+"]";
  }
}
