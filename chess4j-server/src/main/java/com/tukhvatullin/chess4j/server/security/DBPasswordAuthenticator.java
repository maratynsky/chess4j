package com.tukhvatullin.chess4j.server.security;

import com.tukhvatullin.chess4j.server.db.DataSource;
import org.webbitserver.HttpRequest;
import org.webbitserver.handler.authentication.PasswordAuthenticator;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Executor;

/**
 * Date: 4/7/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class DBPasswordAuthenticator implements PasswordAuthenticator {

    private DataSource dataSource;


    public DBPasswordAuthenticator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void authenticate(HttpRequest request, String username,
                             String password, ResultCallback callback,
                             Executor handlerExecutor) {
        String expectedPassword = dataSource.getPassword(username);
        if (expectedPassword != null && password.equals(expectedPassword)) {
            callback.success();
        } else {
            callback.failure();
        }
    }
}
