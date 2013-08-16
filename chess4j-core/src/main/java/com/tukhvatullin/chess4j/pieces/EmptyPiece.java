package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;

/**
 * Date: 4/2/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class EmptyPiece extends Piece {
  @Override
  protected char _code() {
    return 0;
  }

  @Override
  public Move.Type canMove(Move move, Game game, Piece pieceTo) {
    return null;
  }
}
