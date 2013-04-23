package com.tukhvatullin.chess4j.server.security;

import java.util.Date;

/**
 * Date: 4/7/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Session {
    private Date lastCallTime;
    private User user;

    public Session(User user) {
        this.user = user;
        this.lastCallTime = new Date();
    }

    public Date getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(Date lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public User getUser() {
        return user;
    }

}
