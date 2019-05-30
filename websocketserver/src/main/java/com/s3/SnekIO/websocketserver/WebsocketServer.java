package com.s3.SnekIO.websocketserver;

import com.s3.SnekIO.websocketserver.game.Game;
import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;
import com.s3.SnekIO.websocketserver.messageHandlers.GameMessageHandler;
import com.s3.SnekIO.websocketserver.messagegenerator.IMessageGenerator;
import com.s3.SnekIO.websocketserver.messagegenerator.MessageGenerator;
import com.s3.SnekIO.websocketserver.messagelogic.GameMessageLogic;
import com.s3.SnekIO.websocketserver.messagelogic.IGameMessageLogic;
import com.s3.SnekIO.websocketserver.messageprocessor.GameMessageProcessor;
import com.s3.SnekIO.websocketserver.messageprocessor.IGameMessageProcessor;
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

        IGameMessageLogic gameMessageLogic = new GameMessageLogic();
        final IGameMessageProcessor gameMessageProcessor = new GameMessageProcessor(gameMessageLogic);
        final TestEndpoint testEndpoint = new TestEndpoint(gameMessageLogic, gameMessageProcessor);
        gameMessageLogic.setEndPoint(testEndpoint);

        IMessageGenerator messageGenerator = new MessageGenerator(testEndpoint);
        Game game = new Game(messageGenerator, 500,500);
        messageGenerator.setGame(game);

        GameMessageHandler messageHandler = new GameMessageHandler(game);

        try {
            ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(wsContext);
            logger.info("javax.websocket layer initialized");

            TestEndpoint.setMessageHandler(messageHandler);

            serverContainer.addEndpoint(TestEndpoint.class);
            logger.info("Endpoint added");

            wsServer.start();
            logger.info("Server started");

            game.start();
            logger.info("Game started");

            wsServer.join();
            logger.info("Server joined");

        } catch (Exception ex) {
            logger.error("Error @ startWebSocketServer: {0}", ex);
        }
    }
}
