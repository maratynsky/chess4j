package com.tukhvatullin.chess4j.server;

import com.tukhvatullin.chess4j.server.db.DataSource;
import com.tukhvatullin.chess4j.server.db.RedisDataSource;
import com.tukhvatullin.chess4j.server.game.GameManager;
import com.tukhvatullin.chess4j.server.security.DBPasswordAuthenticator;
import com.tukhvatullin.chess4j.server.security.SessionManager;

import java.util.Properties;

/**
 * Date: 4/8/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Beans {
    public static DataSource dataSource;
    public static SessionManager sessionManager;
    public static GameManager gameManager;

    public void init(Properties properties) {
        dataSource = new RedisDataSource(properties.getProperty("redis.host"));
        sessionManager = new SessionManager(new DBPasswordAuthenticator(dataSource));
        gameManager = new GameManager();
    }

    public void init() {
        Properties properties = new Properties();
        properties.setProperty("redis.host", "localhost");
    }
}
