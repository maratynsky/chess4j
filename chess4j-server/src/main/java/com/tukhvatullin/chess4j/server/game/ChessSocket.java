package com.tukhvatullin.chess4j.server.game;

import com.google.gson.Gson;
import com.tukhvatullin.chess4j.game.response.CantMoveResponse;
import com.tukhvatullin.chess4j.game.response.MoveResponse;
import com.tukhvatullin.chess4j.server.security.Session;
import com.tukhvatullin.chess4j.server.security.User;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;

/**
 * Date: 4/3/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class ChessSocket extends BaseWebSocketHandler{



    private Gson gson = new Gson();

    public ChessSocket() {
    }

    private Session session(WebSocketConnection connection) {
        return (Session) connection.httpRequest().data("session");
    }

    private User user(WebSocketConnection connection){
        return session(connection).getUser();
    }

    private TheGame game(WebSocketConnection connection){
        return user(connection).getGame();
    }

    @Override
    public void onOpen(WebSocketConnection connection) {
        user(connection).setConnection(connection);
    }

    @Override
    public void onClose(WebSocketConnection connection) {

    }

    @Override
    public void onMessage(WebSocketConnection connection, String msg)
            throws Throwable {
        if (msg.length() == 4) {
            char cf = msg.charAt(0);
            int rf = msg.charAt(1) - '1' + 1;
            char ct = msg.charAt(2);
            int rt = msg.charAt(3) - '1' + 1;
            User user = user(connection);
            TheGame game = user.getGame();
            if(!game.getGame().getTurn().equals(game.color(user))){
                connection.send(gson.toJson(new CantMoveResponse()));
            }else{
                MoveResponse response = game.getGame().move(cf, rf, ct, rt);
                game.getWhite().getConnection().send(gson.toJson(response));
                game.getBlack().getConnection().send(gson.toJson(response));
            }
        }
    }

}
