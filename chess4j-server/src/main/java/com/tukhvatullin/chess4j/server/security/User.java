package com.tukhvatullin.chess4j.server.security;

import com.tukhvatullin.chess4j.server.game.TheGame;
import org.webbitserver.WebSocketConnection;

/**
 * Date: 4/7/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class User {
    private String username;
    private Rank rank;
    private TheGame game;
    private WebSocketConnection connection;

    public User(String username, Rank rank) {
        this.username = username;
        this.rank = rank;
    }

    public TheGame getGame() {
        return game;
    }

    public void setGame(TheGame game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public WebSocketConnection getConnection() {
        return connection;
    }

    public void setConnection(WebSocketConnection connection) {
        this.connection = connection;
    }

    public static enum Rank {

        PAWN(0, 'p'), BISHOP(1, 'b'), KNIGHT(2, 'n'),
        ROOK(3, 'r'), QUEEN(4, 'q'), KING(5, 'k');
        private int order;
        private char code;

        private Rank(int order, char code) {
            this.order = order;
            this.code = code;
        }

        private Rank get(int order) {
            for (Rank rank : values()) {
                if (rank.order == order) {
                    return rank;
                }
            }
            return null;
        }

      private Rank get(char code) {
        for (Rank rank : values()) {
          if (rank.code == code) {
            return rank;
          }
        }
        return null;
      }

        public char getCode() {
            return code;
        }
    }
}
