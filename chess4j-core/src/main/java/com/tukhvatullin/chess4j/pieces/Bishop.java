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
public class Bishop extends Piece {
  @Override
  protected char _code() {
    return 'b';
  }

  @Override
  public MoveResponse canMove(Move move, Game game, Piece pieceTo) {

    if(pieceTo != null && color().equals(pieceTo.color())){
      return new CantMoveResponse();
    }

    int drow = move.getRowTo() - move.getRowFrom();
    int dcol = move.getColTo() - move.getColFrom();
    if (Math.abs(dcol) != Math.abs(drow)) {
      return new CantMoveResponse();
    }
    //check path
    int rStep = drow < 0 ? -1 : 1;
    int cStep = dcol < 0 ? -1 : 1;
    for (int i = 1; i < Math.abs(drow); i++) {
      if (!game.getBoard().isEmpty((char) (move.getColFrom() + i * cStep),
          move.getRowFrom() + i * rStep)) {
        return new CantMoveResponse();
      }
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

    int d1 = Math.min('h' - col, 8 - row);
    int d2 = Math.min('h' - col, row - 1);
    int d3 = Math.min(col - 'a', 8 - row);
    int d4 = Math.min(col - 'a', row - 1);

    for (int i = 1; i <= d1; i++) {
      moves.add(new Move(pieceCode, col, row, (char) (col + i), row + i));
    }

    for (int i = 1; i <= d2; i++) {
      moves.add(new Move(pieceCode, col, row, (char) (col + i), row - i));
    }

    for (int i = 1; i <= d3; i++) {
      moves.add(new Move(pieceCode, col, row, (char) (col - i), row + i));
    }

    for (int i = 1; i <= d4; i++) {
      moves.add(new Move(pieceCode, col, row, (char) (col - i), row - i));
    }


    return moves;
  }
}
