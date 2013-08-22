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
public class Knight extends Piece {
  @Override
  protected char _code() {
    return 'n';
  }

  @Override
  public MoveResponse canMove(Move move, Game game, Piece pieceTo) {

    if(pieceTo != null && color().equals(pieceTo.color())){
      return new CantMoveResponse();
    }

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

  @Override
  public List<Move> moves(char col, int row, Game game) {
    List<Move> moves = new LinkedList<Move>();
    char pieceCode = _code();

    int[][] d = new int[][] {
        {1, 2}, {2, 1},
        {-1, 2}, {-2, 1},
        {-1, -2}, {-2, -1},
        {1, -2}, {2, -1}
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
