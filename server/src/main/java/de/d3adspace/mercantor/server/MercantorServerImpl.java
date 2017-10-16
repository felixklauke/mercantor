package de.d3adspace.mercantor.server;

import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.resource.MercantorResource;
import de.d3adspace.mercantor.shared.io.ServiceBodyReader;
import de.d3adspace.mercantor.shared.io.ServiceBodyWriter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

/**
 * Default implementation of the {@link IMercantorServer}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerImpl implements IMercantorServer {

    private final Logger logger = LoggerFactory.getLogger(MercantorServerImpl.class);

    /**
     * The underlying config.
     */
    private final MercantorServerConfig mercantorServerConfig;

    /**
     * The jersey server used for rest communication.
     */
    private HttpServer httpServer;

    private MercantorResource mercantorResource;

    private ServiceBodyReader serviceBodyReader;

    private ServiceBodyWriter serviceBodyWriter;

    /**
     * Create a new server by its underlying config.
     *
     * @param mercantorServerConfig The config.
     * @param mercantorResource The rest resource.
     * @param serviceBodyReader The body reader for our communication.
     * @param serviceBodyWriter The body writer for our communication.
     */
    @Inject
    MercantorServerImpl(MercantorServerConfig mercantorServerConfig, MercantorResource mercantorResource, ServiceBodyReader serviceBodyReader, ServiceBodyWriter serviceBodyWriter) {
        this.mercantorServerConfig = mercantorServerConfig;
        this.mercantorResource = mercantorResource;
        this.serviceBodyReader = serviceBodyReader;
        this.serviceBodyWriter = serviceBodyWriter;

        logger.info("Setting up dependency injection.");

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Override
    public void start() {
        logger.info("Setting up jersey resource config.");

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(mercantorResource).register(serviceBodyReader).register(serviceBodyWriter);

        logger.info("Setting up grizzly http server.");

        httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(mercantorServerConfig.getHost() + ":" + mercantorServerConfig.getPort()), resourceConfig);

        logger.info("Starting grizzly http server.");

        try {
            httpServer.start();
        } catch (IOException e) {
            logger.error("Error while starting grizzly http server", e);
        }

        logger.info("Server setup complete.");
    }

    @Override
    public void stop() {
        logger.info("Shutting down grizzly http server.");

        httpServer.shutdown();

        logger.info("Grizzly http server was shut down.");
    }

    @Override
    public boolean isRunning() {
        return httpServer == null || httpServer.isStarted();
    }
}
