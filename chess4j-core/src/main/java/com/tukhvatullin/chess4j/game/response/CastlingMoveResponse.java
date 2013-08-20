package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class CastlingMoveResponse extends MoveResponse{

  private final Action kingAction;
  private final Action rookAction;

  public CastlingMoveResponse(Action kingAction, Action rookAction) {
    super(Move.Type.CASTLING);
    this.kingAction = kingAction;
    this.rookAction = rookAction;
  }

  public Action getKingAction() {
    return kingAction;
  }

  public Action getRookAction() {
    return rookAction;
  }
}
