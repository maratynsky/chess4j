package com.tukhvatullin.chess4j.server.db;

/**
 * Date: 4/8/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public interface DataSource {
    String getPassword(String username);
}
