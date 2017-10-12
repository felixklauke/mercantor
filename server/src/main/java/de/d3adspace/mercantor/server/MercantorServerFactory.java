package de.d3adspace.mercantor.server;

import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.config.MercantorServerConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for all server instances.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerFactory {

    /**
     * The logger to log all actions.
     */
    private static Logger logger = LoggerFactory.getLogger(MercantorServerFactory.class);

    /**
     * Create a new mercantor server with a default config.
     *
     * @return The server instance.
     */
    public static IMercantorServer createMercantorServer() {
        logger.info("No config given, server will be created with a default config.");

        MercantorServerConfig mercantorServerConfig = new MercantorServerConfigBuilder()
                .setHost(MercantorServerConstants.DEFAULT_SERVER_HOST)
                .setPort(MercantorServerConstants.DEFAULT_SERVER_PORT)
                .setServiceExpiration(MercantorServerConstants.DEFAULT_SERVICE_EXPIRATION)
                .setServiceExpirationTimeUnit(MercantorServerConstants.DEFAULT_SERVICE_EXPIRATION_TIMEUNIT)
                .setServiceLookupMode(MercantorServerConstants.DEFAULT_SERVICE_LOOKUP_MODE)
                .createMercantorServerConfig();

        return createMercantorServer(mercantorServerConfig);
    }

    /**
     * Create a new mercantor server by the given config.
     *
     * @param mercantorServerConfig The config.
     * @return The server instance.
     */
    private static IMercantorServer createMercantorServer(MercantorServerConfig mercantorServerConfig) {
        logger.info("Server will be created using config {}.", mercantorServerConfig);

        return new MercantorServerImpl(mercantorServerConfig);
    }
}
