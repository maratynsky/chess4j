package com.tukhvatullin.chess4j.server.security;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import org.webbitserver.handler.authentication.PasswordAuthenticator;

import java.net.HttpCookie;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Date: 4/7/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public final class SessionManager implements HttpHandler, Runnable {

    private Map<String, Session> sessions = new HashMap<String, Session>();
    private PasswordAuthenticator passwordAuthenticator;


    public SessionManager(PasswordAuthenticator passwordAuthenticator) {
        this.passwordAuthenticator = passwordAuthenticator;
    }

    /**
     * @param username
     * @return generated session id
     */
    private String authentificate(String username) {
        User user = new User(username, User.Rank.PAWN);
        Session session = new Session(user);
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, session);
        //todo

        return sessionId;
    }

    @Override
    public void handleHttpRequest(final HttpRequest request,
                                  final HttpResponse response,
                                  final HttpControl control) throws Exception {
        if (request.uri().equalsIgnoreCase("/login") ||
                request.uri().equalsIgnoreCase("/login/")) {
            control.nextHandler();
        } else if (request.uri().equalsIgnoreCase("/signin")) {
            if (request.method().equalsIgnoreCase("POST")) {
                //authentification
                final String username = request.postParam("username");
                String password = request.postParam("password");
                passwordAuthenticator.authenticate(request, username, password,
                        new PasswordAuthenticator.ResultCallback() {
                            @Override
                            public void success() {
                                String sessionId = authentificate(username);
                                response.cookie(new HttpCookie("SSID",
                                        sessionId));
                                redirectToIndex(response);
                            }

                            @Override
                            public void failure() {
                                redirectToSignin(response);
                            }
                        }, control);
            } else {
                control.nextHandler();
            }
        } else if (request.uri().equalsIgnoreCase("/signout")) {
            String ssid = request.cookieValue("SSID");
            sessions.remove(ssid);
            redirectToSignin(response);
        } else {
            String ssid = request.cookieValue("SSID");
            if (ssid == null || ssid.isEmpty()) {
                redirectToSignin(response);
            } else {
                Session session = sessions.get(ssid);
                if (session != null) {
                    request.data("session", session);
                    control.nextHandler();
                } else {
                    redirectToSignin(response);
                }
            }

        }
    }

    private void redirectToSignin(HttpResponse response) {
        response.header("Location", "/login/");
        response.status(302);
        response.end();
    }

    private void redirectToIndex(HttpResponse response) {
        response.header("Location", "/game/");
        response.status(302);
        response.end();
    }

    @Override
    public void run() {
        while (true) {
            Date time = new Date();
            time.setTime(time.getTime() - 1 * 60 * 60 * 1000);
            for (String sessionId : sessions.keySet()) {
                Session session = sessions.get(sessionId);
                if (session.getLastCallTime().before(time)) {
                    //sessions.remove(sessionId);
                }
            }
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
