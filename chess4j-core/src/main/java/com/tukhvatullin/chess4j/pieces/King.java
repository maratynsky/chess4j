package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;

/**
 * Date: 4/1/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class King extends Piece {

  private char col;
  private int row;

  public char getCol() {
    return col;
  }

  public void setCol(char col) {
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  @Override
  protected char _code() {
    return 'k';
  }

  @Override
  public Move.Type canMove(Move move, Game game, Piece pieceTo) {
    int drow = move.getRowTo() - move.getRowFrom();
    int dcol = move.getColTo() - move.getColFrom();

    if (Math.abs(dcol) == 2 && drow == 0 && !isMoved() &&
        !underAttack(color(), move.getColFrom(),
            move.getRowFrom(), game)) {
      char rookCol = dcol < 0 ? 'a' : 'h';
      Piece rook = game.getBoard().get(rookCol, move.getRowFrom());
      if (rook.isMoved()) {
        return Move.Type.CANTMOVE;
      }
      int cd = dcol < 0 ? -1 : 1;
      for (int i = 1; i <= Math.abs(dcol); i++) {
        if (!game.getBoard().isEmpty(
            (char) (move.getColFrom() + i * cd),
            move.getRowFrom())) {
          return Move.Type.CANTMOVE;
        }
        if (underAttack(color(), (char) (move.getColFrom() + i * cd),
            move.getRowFrom(), game)) {
          return Move.Type.CANTMOVE;
        }
      }
      game.castling(rook.code(), rookCol, move.getRowFrom(),
          (char) (move.getColTo() - cd),
          move.getRowTo());
      return Move.Type.CASTLING;
    }

    if (Math.abs(drow) > 1 || Math.abs(dcol) > 1) {
      return Move.Type.CANTMOVE;
    }


    if (pieceTo == null) {
      return Move.Type.MOVENMENT;
    }
    else {
      return Move.Type.ATTACK;
    }
  }
}
