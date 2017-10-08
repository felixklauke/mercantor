package de.d3adspace.mercantor.client;

import de.d3adspace.mercantor.client.config.MercantorClientConfig;
import de.d3adspace.mercantor.client.config.MercantorClientConfigBuilder;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientFactory {

    public static IMercantorClient createMercantorClient() {
        MercantorClientConfig mercantorClientConfig = new MercantorClientConfigBuilder()
                .setServerHost(MercantorClientConstants.DEFAULT_SERVER_HOST)
                .setServerPort(MercantorClientConstants.DEFAULT_SERVER_PORT)
                .createMercantorClientConfig();

        return createMercantorClient(mercantorClientConfig);
    }

    public static IMercantorClient createMercantorClient(MercantorClientConfig mercantorClientConfig) {
        return new MercantorClientImpl(mercantorClientConfig);
    }
}
