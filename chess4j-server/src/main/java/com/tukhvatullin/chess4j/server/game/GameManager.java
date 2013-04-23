package com.tukhvatullin.chess4j.server.game;

import com.google.gson.Gson;
import com.tukhvatullin.chess4j.game.Board;
import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.pieces.Piece;
import com.tukhvatullin.chess4j.server.security.Session;
import com.tukhvatullin.chess4j.server.security.User;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Date: 4/8/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class GameManager implements HttpHandler {

    private List<TheGame> games = new LinkedList<TheGame>();

    private TheGame getGame(User user) {
        TheGame game = user.getGame();
        if (game == null) {
            for (TheGame g : games) {
                if (!g.isFull()) {
                    game = g;
                    game.setBlack(user);
                    break;
                }
            }
            if (game == null) {
                game = new TheGame(TheGame.Type.PUBLIC, new Game(), user);
                games.add(game);
            }
        }
        return game;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        Session session = (Session) request.data("session");
        User user = session.getUser();
        TheGame game = getGame(user);

        Map responseMap = new HashMap();
        responseMap.put("color", game.color(user));
        Map<String, Character> map = new HashMap<String, Character>();

        Board board = game.getGame().getBoard();


        for (char col = 'a'; col <= 'h'; col++) {
            for (int row = 1; row <= 8; row++) {
                Piece piece = board.get(col, row);
                if (piece == null) continue;
                map.put("" + col + row, piece.code());
            }
        }

        User oppenent = game.opponent(user);
        if(oppenent != null){
            responseMap.put("enemy_name", oppenent.getUsername());
            responseMap.put("enemy_rank", oppenent.getRank().getCode());
        }
        responseMap.put("name", user.getUsername());
        responseMap.put("rank", user.getRank().getCode());

        responseMap.put("map", map);
        response.header("Content-type", "text/json")
                .content(new Gson().toJson(responseMap))
                .end();
    }

}
