package de.d3adspace.mercantor.server;

import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.config.MercantorServerConfigBuilder;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerFactory {

    public static IMercantorServer createMercantorServer() {
        MercantorServerConfig mercantorServerConfig = new MercantorServerConfigBuilder()
                .setHost(MercantorServerConstants.DEFAULT_SERVER_HOST)
                .setPort(MercantorServerConstants.DEFAULT_SERVER_PORT)
                .setServiceExpiration(MercantorServerConstants.DEFAULT_SERVICE_EXPIRATION)
                .setServiceExpirationTimeUnit(MercantorServerConstants.DEFAULT_SERVICE_EXPIRATION_TIMEUNIT)
                .createMercantorServerConfig();

        return createMercantorServer(mercantorServerConfig);
    }

    private static IMercantorServer createMercantorServer(MercantorServerConfig mercantorServerConfig) {
        return new MercantorServerImpl(mercantorServerConfig);
    }
}
