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
                                 char colTo, int rowTo) {
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

    King king = turn.equals(Piece.Color.WHITE) ?
        board.getWhiteKing() : board.getBlackKing();
    if (Piece.underAttack(king.color(), king.getCol(),
        king.getRow(), this)) {
      board.rollback();
      return new KingIsUnderAttackResponse();
    }

    board.commit();
    moves.add(move);

    boolean check = checkCheck();
    moveResponse.check(check);
    if (check) {
      moveResponse.checkmate(checkCheckmate());
    }
    nextTurn();

    return moveResponse;
  }

  private void doTurn(Move move, MoveResponse response,
                      Character promotionPiece) {
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
    Piece promotion;
    if (promotionPiece != null) {
      switch (Character.toLowerCase(promotionPiece)) {
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
    }
    else {
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
    King king = turn.equals(Piece.Color.WHITE) ?
        board.getBlackKing() : board.getWhiteKing();
    if (Piece.underAttack(king.color(), king.getCol(),
        king.getRow(), this)) {
      board.rollback();
      return true;
    }
    return false;
  }

  public boolean checkCheckmate() {
    King king = turn.equals(Piece.Color.WHITE) ?
        board.getBlackKing() : board.getWhiteKing();
    Piece.Color color = king.color();
    for (char col = 'a'; col <= 'h'; col++) {
      for (int row = 1; row <= 8; row++) {
        Piece piece = board.get(col, row);
        if (piece != null && piece.color().equals(color)) {
          List<Move> moves = piece.moves(col, row, this);
          for (Move move : moves) {
            MoveResponse response = piece.canMove(move, this,
                board.get(move.getColTo(), move.getRowTo()));
            board.start();
            doTurn(move, response, null);

            if (!Piece.underAttack(king.color(), king.getCol(),
                king.getRow(), this)) {
              board.rollback();
              return false;
            }

            board.rollback();
          }
        }
      }
    }
    return true;
  }

  public Piece.Color getTurn() {
    return turn;
  }
}
