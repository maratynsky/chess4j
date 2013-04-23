package com.tukhvatullin.chess4j.server.db;

import redis.clients.jedis.Jedis;

/**
 * Date: 4/8/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class RedisDataSource implements DataSource {

    private static String USERNAME_PREFIX = "user_";

    private Jedis jedis;

    public RedisDataSource(String host, int port, String password) {
        this.jedis = new Jedis(host, port);
        this.jedis.auth(password);
    }

    public RedisDataSource(String host) {
        this.jedis = new Jedis(host);
    }

    public RedisDataSource(String host, int port) {
        this.jedis = new Jedis(host, port);
    }

    @Override
    public String getPassword(String username) {
        return jedis.get(USERNAME_PREFIX+username);
    }
}
