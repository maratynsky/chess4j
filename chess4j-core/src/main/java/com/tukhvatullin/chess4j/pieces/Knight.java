package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;
import com.tukhvatullin.chess4j.game.response.*;

/**
 * Date: 4/1/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Knight extends Piece {
  @Override
  protected char _code() {
    return 'n';
  }

  @Override
  public MoveResponse canMove(Move move, Game game, Piece pieceTo) {
    int drow = Math.abs(move.getRowTo() - move.getRowFrom());
    int dcol = Math.abs(move.getColTo() - move.getColFrom());

    if (Math.max(drow, dcol) > 2) {
      return new CantMoveResponse();
    }

    if (drow + dcol != 3) {
      return new CantMoveResponse();
    }

    if (pieceTo == null) {
      return new MovenmentResponse(new Action(move));
    }
    else {
      return new AttackResponse(new Action(move));
    }
  }
}
