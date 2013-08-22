package com.tukhvatullin.chess4j.pieces;

import java.util.LinkedList;
import java.util.List;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;
import com.tukhvatullin.chess4j.game.response.*;

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
  public MoveResponse canMove(Move move, Game game, Piece pieceTo) {

    if(pieceTo != null && color().equals(pieceTo.color())){
      return new CantMoveResponse();
    }

    int drow = move.getRowTo() - move.getRowFrom();
    int dcol = move.getColTo() - move.getColFrom();

    if (Math.abs(dcol) == 2 && drow == 0 && !isMoved() &&
        !underAttack(color(), move.getColFrom(),
            move.getRowFrom(), game)) {
      char rookCol = dcol < 0 ? 'a' : 'h';
      Piece rook = game.getBoard().get(rookCol, move.getRowFrom());
      if (rook == null) {
        return new CantMoveResponse();
      }
      if (rook.isMoved()) {
        return new CantMoveResponse();
      }
      int cd = dcol < 0 ? -1 : 1;
      for (int i = 1; i <= Math.abs(dcol); i++) {
        if (!game.getBoard().isEmpty(
            (char) (move.getColFrom() + i * cd),
            move.getRowFrom())) {
          return new CantMoveResponse();
        }
        if (underAttack(color(), (char) (move.getColFrom() + i * cd),
            move.getRowFrom(), game)) {
          return new CantMoveResponse();
        }
      }
      return new CastlingMoveResponse(
          new Action(move),
          new Action(
              new Position(rookCol, move.getRowFrom()),
              new Position((char) (move.getColTo() - cd), move.getRowTo())
          )
      );
    }

    if (Math.abs(drow) > 1 || Math.abs(dcol) > 1) {
      return new CantMoveResponse();
    }


    if (pieceTo == null) {
      return new MovenmentResponse(new Action(move));
    }
    else {
      return new AttackResponse(new Action(move));
    }
  }

  @Override
  public List<Move> moves(char col, int row, Game game) {
    List<Move> moves = new LinkedList<Move>();
    char pieceCode = _code();
    int[][] d = new int[][] {
        {1, 1}, {1, 1},
        {-1, 1}, {-1, 1},
        {-1, -1}, {-1, -1},
        {1, -1}, {1, -1}
    };

    for (int i = 0; i < d.length; i++) {
      char c = (char) (col + d[i][0]);
      int r = row + d[i][1];
      if (c >= 'a' && c <= 'h' && r >= 1 && r <= 8) {
        moves.add(new Move(pieceCode, col, row, c, r));
      }
    }

    return moves;
  }
}
