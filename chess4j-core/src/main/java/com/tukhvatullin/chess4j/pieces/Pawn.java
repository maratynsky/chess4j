package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;
import com.tukhvatullin.chess4j.game.response.*;

/**
 * Date: 3/30/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Pawn extends Piece {

  @Override
  protected char _code() {
    return 'p';
  }

  @Override
  public MoveResponse canMove(Move move, Game game, Piece pieceTo) {
    //todo check king is under attack after move
    if (move.getColFrom() == move.getColTo()) {
      if (pieceTo != null) {
        return new CantMoveResponse();
      }
      if (color().equals(Color.WHITE)) {
        if (move.getRowTo() < move.getRowFrom()) {
          return new CantMoveResponse();
        }
        if (move.getRowFrom() == 2) {
          if (move.getRowTo() - move.getRowFrom() > 2) {
            return new CantMoveResponse();
          }
          if (!game.getBoard().isEmpty(move.getColFrom(),
              move.getRowFrom() + 1)) {
            return new CantMoveResponse();
          }
        }
        else if (move.getRowTo() - move.getRowFrom() != 1) {
          return new CantMoveResponse();
        }
        if (move.getRowTo() == 8) {
          return new PromotionResponse(new Action(move));
        }

      }
      else {
        if (move.getRowTo() > move.getRowFrom()) {
          return new CantMoveResponse();
        }
        if (move.getRowFrom() == 7) {
          if (move.getRowFrom() - move.getRowTo() > 2) {
            return new CantMoveResponse();
          }
          if (!game.getBoard().isEmpty(move.getColFrom(),
              move.getRowFrom() - 1)) {
            return new CantMoveResponse();
          }
        }
        else if (move.getRowFrom() - move.getRowTo() != 1) {
          return new CantMoveResponse();
        }
        if (move.getRowTo() == 1) {
          return new PromotionResponse(new Action(move));
        }

      }
      return new MovenmentResponse(new Action(move));
    }
    else if (Math.abs(move.getColFrom() - move.getColTo()) == 1) {

      if (color().equals(Color.WHITE)) {
        if (move.getRowTo() - move.getRowFrom() != 1) {
          return new CantMoveResponse();
        }
        if (move.getRowTo() == 8) {
          return new PromotionResponse(new Action(move));
        }
      }
      else {
        if (move.getRowTo() - move.getRowFrom() != -1) {
          return new CantMoveResponse();
        }
        if (move.getRowTo() == 1) {
          return new PromotionResponse(new Action(move));
        }
      }

      if (pieceTo == null) {
        Move lastMove = game.getLastMove();
        if (Character.toLowerCase(lastMove.getPieceCode()) ==
            Character.toLowerCase(code()) &&
            lastMove.getColTo() == move.getColTo() &&
            Math.abs(lastMove.getRowFrom() -
                lastMove.getRowTo()) == 2 &&
            lastMove.getRowTo() == move.getRowFrom()) {
          return new EnpassantResponse(new Action(move), new Position(lastMove.getColTo(), lastMove.getRowTo()));
        }
        else {
          return new CantMoveResponse();
        }
      }
      else {
        return new AttackResponse(new Action(move));
      }
    }
    else {
      return new CantMoveResponse();
    }


  }
}
