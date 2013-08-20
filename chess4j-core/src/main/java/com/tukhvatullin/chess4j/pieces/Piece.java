package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;
import com.tukhvatullin.chess4j.game.response.MoveResponse;

/**
 * Date: 3/30/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public abstract class Piece {

  protected Color color;
  protected boolean moved = false;

  public static boolean underAttack(Color color, char colTo, int rowTo,
                                    Game game) {
    for (int row = 1; row <= 8; row++) {
      for (char col = 'a'; col <= 'h'; col++) {
        Piece piece = game.getBoard().get(col, row);
        if (piece != null && !piece.color().equals(color)) {
          Move move = new Move(piece.code(), col, row, colTo, rowTo);
          Move.Type moveType = piece.canMove(move, game, new EmptyPiece()).getMoveType();
          if (moveType.equals(Move.Type.ATTACK) ||
              moveType.equals(Move.Type.MOVENMENT) ||
              moveType.equals(Move.Type.PROMOTION) ||
              moveType.equals(Move.Type.ENPASSANT)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean isMoved() {
    return moved;
  }

  public void setMoved(boolean moved) {
    this.moved = moved;
  }

  public final Piece color(Color color) {
    this.color = color;
    return this;
  }

  public Color color() {
    return color;
  }

  public final Piece white() {
    return color(Color.WHITE);
  }

  public final Piece black() {
    return color(Color.BLACK);
  }

  protected abstract char _code();

  public char unicode() {
    switch (code()) {
      case 'K':
        return '\u2654';
      case 'Q':
        return '\u2655';
      case 'R':
        return '\u2656';
      case 'B':
        return '\u2657';
      case 'N':
        return '\u2658';
      case 'P':
        return '\u2659';

      case 'k':
        return '\u265A';
      case 'q':
        return '\u265B';
      case 'r':
        return '\u265C';
      case 'b':
        return '\u265D';
      case 'n':
        return '\u265E';
      case 'p':
        return '\u265F';
    }
    return '?';
  }

  public abstract MoveResponse canMove(Move move, Game game, Piece pieceTo);

  public char code() {
    return color.equals(Color.WHITE) ?
        Character.toUpperCase(_code()) : _code();
  }

  public static enum Color {
    WHITE, BLACK
  }


}
