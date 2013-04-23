package com.tukhvatullin.chess4j.server;

import com.google.gson.Gson;
import com.tukhvatullin.chess4j.game.Board;
import com.tukhvatullin.chess4j.game.Game;
import com.tukhvatullin.chess4j.pieces.Piece;
import com.tukhvatullin.chess4j.server.security.Session;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 4/3/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class DataHttpHandler implements HttpHandler {



    public DataHttpHandler(Game game) {
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response,
                                  HttpControl control) throws Exception {

    }
}
