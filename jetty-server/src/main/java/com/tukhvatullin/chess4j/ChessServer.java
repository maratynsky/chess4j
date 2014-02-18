package com.tukhvatullin.chess4j;

import org.eclipse.jetty.websocket.WebSocketHandler;

/**
 * @author pokmeptb
 */
public class ChessServer {
  public static void main(String[] args) throws Exception {
    ChessServer server = new ChessServer(8080);
    WebSocketHandler wsHandler = new WebSocketHandler() {
      @Override
      public void configure(WebSocketServletFactory factory) {
        factory.register(MyWebSocketHandler.class);
      }
    };
    server.setHandler(wsHandler);
    server.start();
    server.join();
  }
}
