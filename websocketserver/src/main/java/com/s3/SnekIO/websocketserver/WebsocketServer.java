package com.s3.SnekIO.websocketserver;

import com.s3.SnekIO.websocketserver.endpoint.TestEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebsocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);
    private static final int PORT = 1337;

    public static void main(String[] args) {
        startWebSocketServer();
    }

    private static void startWebSocketServer() {
        logger.debug("startWebSocketServer");
        Server wsServer = new Server();
        ServerConnector connector = new ServerConnector(wsServer);
        connector.setPort(PORT);
        wsServer.addConnector(connector);
        logger.info("Connector added");

        ServletContextHandler wsContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        wsContext.setContextPath("/");
        wsServer.setHandler(wsContext);
        logger.info("Context handler set");

        try {
            ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(wsContext);
            logger.info("javax.websocket layer initialized");

            serverContainer.addEndpoint(TestEndpoint.class);
            logger.info("Endpoint added");

            wsServer.start();
            logger.info("Server started");

            wsServer.join();
            logger.info("Server joined");

        } catch (Exception e) {
            logger.error("Error @ startWebSocketServer: {0}", e);
        }
    }
}
