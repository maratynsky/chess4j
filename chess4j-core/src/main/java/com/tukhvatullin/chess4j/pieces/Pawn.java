package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;

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
    public Move.Type canMove(Move move, Game game, Piece pieceTo) {
        //todo check king is under attack after move
        if (move.getColFrom() == move.getColTo()) {
            if (pieceTo != null) {
                return Move.Type.CANTMOVE;
            }
            if (color().equals(Color.WHITE)) {
                if (move.getRowTo() < move.getRowFrom()) {
                    return Move.Type.CANTMOVE;
                }
                if (move.getRowFrom() == 2) {
                    if (move.getRowTo() - move.getRowFrom() > 2) {
                        return Move.Type.CANTMOVE;
                    }
                    if (!game.getBoard().isEmpty(move.getColFrom(),
                            move.getRowFrom() + 1)) {
                        return Move.Type.CANTMOVE;
                    }
                } else if (move.getRowTo() - move.getRowFrom() != 1) {
                    return Move.Type.CANTMOVE;
                }
                if (move.getRowTo() == 8) {
                    return Move.Type.PROMOTION;
                }


            } else {
                if (move.getRowTo() > move.getRowFrom()) {
                    return Move.Type.CANTMOVE;
                }
                if (move.getRowFrom() == 7) {
                    if (move.getRowFrom() - move.getRowTo() > 2) {
                        return Move.Type.CANTMOVE;
                    }
                    if (!game.getBoard().isEmpty(move.getColFrom(),
                            move.getRowFrom() - 1)) {
                        return Move.Type.CANTMOVE;
                    }
                } else if (move.getRowFrom() - move.getRowTo() != 1) {
                    return Move.Type.CANTMOVE;
                }
                if (move.getRowTo() == 1) {
                    return Move.Type.PROMOTION;
                }

            }
            return Move.Type.MOVENMENT;
        } else if (Math.abs(move.getColFrom() - move.getColTo()) == 1) {

            if (color().equals(Color.WHITE)) {
                if (move.getRowTo() - move.getRowFrom() != 1) {
                    return Move.Type.CANTMOVE;
                }
                if (move.getRowTo() == 8) {
                    return Move.Type.PROMOTION;
                }
            } else {
                if (move.getRowTo() - move.getRowFrom() != -1) {
                    return Move.Type.CANTMOVE;
                }
                if (move.getRowTo() == 1) {
                    return Move.Type.PROMOTION;
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
                    game.enpassant(lastMove.getColTo(), lastMove.getRowTo());
                    return Move.Type.ENPASSANT;
                }else{
                    return Move.Type.CANTMOVE;
                }
            } else {
                return Move.Type.ATTACK;
            }
        } else {
            return Move.Type.CANTMOVE;
        }


    }
}
