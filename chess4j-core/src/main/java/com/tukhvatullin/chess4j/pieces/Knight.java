package com.tukhvatullin.chess4j.pieces;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.game.Move;

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
    public Move.Type canMove(Move move, Game game, Piece pieceTo) {
        int drow = Math.abs(move.getRowTo() - move.getRowFrom());
        int dcol = Math.abs(move.getColTo() - move.getColFrom());

        if (Math.max(drow, dcol) > 2) {
            return Move.Type.CANTMOVE;
        }

        if (drow + dcol != 3){
            return Move.Type.CANTMOVE;
        }

        if (pieceTo == null) {
            return Move.Type.MOVENMENT;
        }else{
            return Move.Type.ATTACK;
        }
    }
}
