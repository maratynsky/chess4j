package com.tukhvatullin.chess4j.pieces;

import java.util.List;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;
import com.tukhvatullin.chess4j.game.response.MoveResponse;

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
  public MoveResponse canMove(Move move, Game game, Piece pieceTo) {
    return null;
  }

  @Override
  public List<Move> moves(char col, int row, Game game) {
    return null;  //TODO: implement this method
  }
}
