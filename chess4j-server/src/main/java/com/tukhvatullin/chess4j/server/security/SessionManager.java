package com.tukhvatullin.chess4j.server.security;

import java.net.HttpCookie;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import org.webbitserver.handler.authentication.PasswordAuthenticator;

/**
 * Date: 4/7/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public final class SessionManager implements HttpHandler, Runnable {

  private Map<String, Session> sessions = new HashMap<String, Session>();
  private int un = 1;

  public SessionManager(PasswordAuthenticator passwordAuthenticator) {
  }

  public String generateUsername() {
    return "player" + un++;
  }

  /**
   * @param username
   * @return generated session id
   */
  private String authentificate(String username, User.Rank rank) {
    User user = new User(username, rank);
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
    String ssid = request.cookieValue("SSID");
    request.data("session", sessions.get(ssid));
    if (request.uri().toLowerCase().startsWith("/assets")) {
      control.nextHandler();
    }
    else if (request.uri().equalsIgnoreCase("/start/") ||
        request.uri().equalsIgnoreCase("/start")) {
      if (ssid == null || ssid.isEmpty() || !sessions.containsKey(ssid)) {
        control.nextHandler();
      }
      else {
        redirectToIndex(response);
      }
    }
    else if (request.uri().equalsIgnoreCase("/start.do")) {
      if (request.method().equalsIgnoreCase("POST")) {
        final String username = request.postParam("username");
        final String level = request.postParam("level");
        User.Rank rank = User.Rank.valueOf(level.toUpperCase());
        String sessionId = authentificate(username, rank);
        response.cookie(new HttpCookie("SSID", sessionId));
        redirectToIndex(response);
      }
      else {
        control.nextHandler();
      }
    }
    else if (ssid == null || ssid.isEmpty() || !sessions.containsKey(ssid)) {
      redirectToStart(response);
    }
    else {
      control.nextHandler();
    }
  }

  private void redirectToStart(HttpResponse response) {
    response.header("Location", "/start");
    response.status(302);
    response.end();
  }

  private void redirectToIndex(HttpResponse response) {
    response.header("Location", "/");
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
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
