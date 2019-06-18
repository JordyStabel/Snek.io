package com.s3.snekio.websocketserver;

import com.s3.snekio.websocketserver.endpoints.GameEndpoint;
import com.s3.snekio.websocketserver.game.Game;
import com.s3.snekio.websocketserver.messageHandlers.GameMessageHandler;
import com.s3.snekio.websocketserver.messagegenerator.IMessageGenerator;
import com.s3.snekio.websocketserver.messagegenerator.MessageGenerator;
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

        final GameEndpoint gameEndpoint = new GameEndpoint();

        IMessageGenerator messageGenerator = new MessageGenerator(gameEndpoint);
        Game game = new Game(messageGenerator);
        messageGenerator.setGame(game);

        GameMessageHandler messageHandler = new GameMessageHandler(game);
        messageHandler.subscribe(game);

        try {
            ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(wsContext);
            logger.info("javax.websocket layer initialized");

            GameEndpoint.setMessageHandler(messageHandler);

            serverContainer.addEndpoint(GameEndpoint.class);
            logger.info("Endpoint added");

            wsServer.start();
            logger.info("Server started");

            wsServer.join();
            logger.info("Server joined");

        } catch (Exception ex) {
            logger.error("Error @ startWebSocketServer: {0}", ex);
        }
    }
}
