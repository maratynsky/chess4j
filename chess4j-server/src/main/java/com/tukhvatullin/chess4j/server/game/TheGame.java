package com.tukhvatullin.chess4j.server.game;

import java.util.UUID;

import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.pieces.Piece;
import com.tukhvatullin.chess4j.server.security.User;

/**
 * Date: 4/5/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class TheGame {

    private String id;
    private Game game;
    private Type type;
    private User white;
    private User black;


    public TheGame(Type type, Game game, User white) {
        this.id = UUID.randomUUID().toString();
        this.game = game;
        this.type = type;
        this.white = white;
        this.white.setGame(this);
    }

    public User getWhite() {
        return white;
    }

    public User getBlack() {
        return black;
    }

    public void setBlack(User black) {
        this.black = black;
        this.black.setGame(this);
    }

    public Piece.Color color(User user) {
        return user == white ? Piece.Color.WHITE : user == black ? Piece.Color.BLACK : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isFull() {
        return black != null && white!=null;
    }

    public User opponent(User user) {
        return user == white ? black : user == black ? white : null;
    }

    public static enum Type {
        PUBLIC, PRIVATE, TOURNEY
    }
}
