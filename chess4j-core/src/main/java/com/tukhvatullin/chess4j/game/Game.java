package com.tukhvatullin.chess4j.game;

import com.tukhvatullin.chess4j.pieces.Piece;
import com.tukhvatullin.chess4j.pieces.Queen;

import java.util.LinkedList;
import java.util.List;

/**
 * Date: 4/1/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Game {

    private Board board;
    private List<Move> moves;
    private Piece.Color turn;
    private List<MoveResponse.Action> actions;


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

        if (colFrom < 'a' || colFrom > 'h' || rowFrom < 1 || rowFrom > 8 ||
                colTo < 'a' || colTo > 'h' || rowTo < 1 || rowTo > 8 ||
                colFrom == colTo && rowFrom == rowTo) {
            return MoveResponse.cantMove();
        }

        Piece pieceFrom = board.get(colFrom, rowFrom);

        if (pieceFrom == null) {
            return MoveResponse.cantMove();
        }

        if (!pieceFrom.color().equals(turn)) {
            return MoveResponse.cantMove();
        }

        Piece pieceTo = board.get(colTo, rowTo);

        if (pieceTo != null && pieceFrom.color().equals(pieceTo.color())) {
            return MoveResponse.cantMove();
        }

        actions = new LinkedList<MoveResponse.Action>();

        Move move = new Move(pieceFrom.code(), colFrom, rowFrom, colTo, rowTo);

        Move.Type moveType = pieceFrom.canMove(move, this, pieceTo);


        if (moveType.equals(Move.Type.CANTMOVE)) {
            System.out.println(move + " " + moveType);
            return MoveResponse.cantMove();
        }

        board.start();



        switch (moveType) {
            case MOVENMENT:
            case ATTACK:
            case CASTLING:
            case ENPASSANT:
                applyMove(move);
                break;
            case PROMOTION:
                applyMove(move);
                promotion(move.getColTo(), move.getRowTo());
                break;
        }

        if (turn.equals(Piece.Color.WHITE)) {
            if (Piece.underAttack(Piece.Color.WHITE, board.getWhiteKing().getCol(),
                    board.getWhiteKing().getRow(), this)) {
                moveType = Move.Type.KINGISUNDERATTACK;
                board.rollback();
            }
        } else if (turn.equals(Piece.Color.BLACK)) {
            if (Piece.underAttack(Piece.Color.BLACK, board.getBlackKing().getCol(),
                    board.getBlackKing().getRow(), this)) {
                moveType = Move.Type.KINGISUNDERATTACK;
                board.rollback();
            }
        }

        System.out.println(move + " " + moveType);
        if (moveType.equals(Move.Type.KINGISUNDERATTACK)) {
            return MoveResponse.kingIsUnderAttack();
        }

        board.commit();
        moves.add(move);
        nextTurn();

        return new MoveResponse(moveType, actions);


    }

    public void promotion(char colTo, int rowTo) {
        board.set(colTo, rowTo, new Queen().color(turn));
    }

    public void castling(char rookCode, char rookColFrom,
                         int rookRowFrom, char rookColTo, int rookRowTo) {
        board.move(new Move(rookCode, rookColFrom, rookRowFrom,
                rookColTo, rookRowTo));
        actions.add(new MoveResponse.Action(rookColFrom+""+rookRowFrom, rookColTo+""+rookRowTo));
    }

    private final void applyMove(Move move) {
        board.move(move);
        actions.add(new MoveResponse.Action(move.getFrom(), move.getTo()));
    }

    public void enpassant(char col, int row) {
        board.set(col, row, null);
        actions.add(new MoveResponse.Action(null, col+""+row));
    }

    private final void nextTurn() {
        if (turn.equals(Piece.Color.WHITE)) {
            turn = Piece.Color.BLACK;
        } else {
            turn = Piece.Color.WHITE;
        }
    }

    public Move getLastMove() {
        return moves.get(moves.size() - 1);
    }

    public void checkCheck() {

    }

    public Piece.Color getTurn() {
        return turn;
    }
}
