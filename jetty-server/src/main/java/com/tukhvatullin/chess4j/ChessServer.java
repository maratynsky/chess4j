package com.tukhvatullin.chess4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

/**
 * @author pokmeptb
 */
public class ChessServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);

    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(true);
    resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
    resourceHandler.setBaseResource(Resource.newClassPathResource("."));



    server.setHandler(resourceHandler);
    server.start();
    server.join();
  }

}
