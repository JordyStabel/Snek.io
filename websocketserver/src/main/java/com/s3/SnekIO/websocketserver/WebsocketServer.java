package com.s3.SnekIO.websocketserver;

import com.s3.SnekIO.websocketserver.endpoints.TestEndpoint;
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

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

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

        try {
            ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(wsContext);
            logger.info("javax.websocket layer initialized");

            // Add websocket endpoint to javax.websocket layer
            ServerEndpointConfig config = ServerEndpointConfig.Builder.create(testEndpoint.getClass(), testEndpoint.getClass().getAnnotation(ServerEndpoint.class).value())
                    .configurator(new ServerEndpointConfig.Configurator() {
                        @Override
                        public <T> T getEndpointInstance(Class<T> endpointClass) {
                            return (T) testEndpoint;
                        }
                    })
                    .build();

            serverContainer.addEndpoint(config);
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
