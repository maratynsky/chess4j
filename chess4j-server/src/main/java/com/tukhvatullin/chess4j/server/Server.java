package com.tukhvatullin.chess4j.server;

import java.io.File;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import com.tukhvatullin.chess4j.server.db.DataSource;
import com.tukhvatullin.chess4j.server.db.RedisDataSource;
import com.tukhvatullin.chess4j.server.game.ChessSocket;
import com.tukhvatullin.chess4j.server.game.GameManager;
import com.tukhvatullin.chess4j.server.security.DBPasswordAuthenticator;
import com.tukhvatullin.chess4j.server.security.SessionManager;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.StaticFileHandler;

/**
 * Date: 4/2/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Server {


    public static void main(String[] args) throws ExecutionException,
            InterruptedException, URISyntaxException {
        DataSource ds = new RedisDataSource("localhost");


        WebServer webServer = WebServers.createWebServer(8081)
                .add(new StaticFileHandler(new File(Server.class.getResource("/web/assets").toURI())))
                .add(new SessionManager(new DBPasswordAuthenticator(ds)))
                .add(new StaticFileHandler(new File(Server.class.getResource("/web").toURI())))
                .add("/chess", new ChessSocket())
                .add("/getgame", new GameManager())
                .start().get();
        System.out.println("Server running at " + webServer.getUri());
    }
}
