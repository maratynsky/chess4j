package com.tukhvatullin.chess4j.game;

import java.util.LinkedList;
import java.util.List;

import com.tukhvatullin.chess4j.game.response.*;
import com.tukhvatullin.chess4j.pieces.*;

/**
 * Date: 4/1/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Game {

  private Board board;
  private List<Move> moves;
  private Piece.Color turn;


  public Game() {
    board = new Board();
    moves = new LinkedList<Move>();
    turn = Piece.Color.WHITE;
  }

  public Board getBoard() {
    return board;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public final MoveResponse move(char colFrom, int rowFrom,
                                 char colTo, int rowTo){
    return move(colFrom, rowFrom, colTo, rowTo, null);
  }

  public final MoveResponse move(char colFrom, int rowFrom,
                                 char colTo, int rowTo,
                                 Character promotionPiece) {

    if (colFrom < 'a' || colFrom > 'h' || rowFrom < 1 || rowFrom > 8 ||
        colTo < 'a' || colTo > 'h' || rowTo < 1 || rowTo > 8 ||
        colFrom == colTo && rowFrom == rowTo) {
      return new CantMoveResponse();
    }

    Piece pieceFrom = board.get(colFrom, rowFrom);

    if (pieceFrom == null) {
      return new CantMoveResponse();
    }

    if (!pieceFrom.color().equals(turn)) {
      return new CantMoveResponse();
    }

    Piece pieceTo = board.get(colTo, rowTo);

    if (pieceTo != null && pieceFrom.color().equals(pieceTo.color())) {
      return new CantMoveResponse();
    }


    Move move = new Move(pieceFrom.code(), colFrom, rowFrom, colTo, rowTo);

    MoveResponse moveResponse = pieceFrom.canMove(move, this, pieceTo);
    Move.Type moveType = moveResponse.getMoveType();


    if (moveType.equals(Move.Type.CANTMOVE)) {
      return moveResponse;
    }

    board.start();
    doTurn(move, moveResponse, promotionPiece);

    if (turn.equals(Piece.Color.WHITE)) {
      if (Piece.underAttack(Piece.Color.WHITE, board.getWhiteKing().getCol(),
          board.getWhiteKing().getRow(), this)) {
        board.rollback();
        return new KingIsUnderAttackResponse();
      }
    }
    else if (turn.equals(Piece.Color.BLACK)) {
      if (Piece.underAttack(Piece.Color.BLACK, board.getBlackKing().getCol(),
          board.getBlackKing().getRow(), this)) {
        board.rollback();
        return new KingIsUnderAttackResponse();
      }
    }


    board.commit();
    moves.add(move);

    moveResponse.check(checkCheck());
    nextTurn();

    return moveResponse;
  }

  private void doTurn(Move move, MoveResponse response, Character promotionPiece) {
    switch (response.getMoveType()) {
      case MOVENMENT:
      case ATTACK:
        applyMove(move);
        break;
      case CASTLING:
        applyMove(move);
        CastlingMoveResponse castling = (CastlingMoveResponse) response;
        castling(castling.getRookAction().getFrom().getCol(),
            castling.getRookAction().getFrom().getRow(),
            castling.getRookAction().getTo().getCol(),
            castling.getRookAction().getTo().getRow());
        break;
      case ENPASSANT:
        applyMove(move);
        EnpassantResponse enpassant = (EnpassantResponse) response;
        enpassant(enpassant.getPawn().getCol(), enpassant.getPawn().getRow());
        break;
      case PROMOTION:
        applyMove(move);
        PromotionResponse promotion = (PromotionResponse) response;
        promotion(move.getColTo(), move.getRowTo(), promotionPiece);
        break;
    }
  }

  private void promotion(char colTo, int rowTo, Character promotionPiece) {
    Piece promotion = null;
    if(promotionPiece != null){
      switch (Character.toLowerCase(promotionPiece)){
        case 'n':
          promotion = new Knight().color(turn);
          break;
        case 'b':
          promotion = new Bishop().color(turn);
          break;
        case 'r':
          promotion = new Rook().color(turn);
          break;
        case 'q':
        default:
          promotion = new Queen().color(turn);
          break;
      }
    }else{
      promotion = new Queen().color(turn);
    }

    board.set(colTo, rowTo, promotion);
  }

  private void castling(char rookColFrom,
                        int rookRowFrom, char rookColTo, int rookRowTo) {
    board.move(new Move('?', rookColFrom, rookRowFrom,
        rookColTo, rookRowTo));
  }

  private final void applyMove(Move move) {
    board.move(move);
  }

  private void enpassant(char col, int row) {
    board.set(col, row, null);
  }

  private final void nextTurn() {
    if (turn.equals(Piece.Color.WHITE)) {
      turn = Piece.Color.BLACK;
    }
    else {
      turn = Piece.Color.WHITE;
    }
  }

  public Move getLastMove() {
    return moves.get(moves.size() - 1);
  }

  public boolean checkCheck() {
    if (turn.equals(Piece.Color.WHITE)) {
      if (Piece.underAttack(Piece.Color.BLACK, board.getBlackKing().getCol(),
          board.getBlackKing().getRow(), this)) {
        return true;
      }
    }
    else if (turn.equals(Piece.Color.BLACK)) {
      if (Piece.underAttack(Piece.Color.WHITE, board.getWhiteKing().getCol(),
          board.getWhiteKing().getRow(), this)) {
        board.rollback();
        return true;
      }
    }
    return false;
  }

  public Piece.Color getTurn() {
    return turn;
  }
}
