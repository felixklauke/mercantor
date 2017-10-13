package de.d3adspace.mercantor.client;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.d3adspace.mercantor.client.config.MercantorClientConfig;
import de.d3adspace.mercantor.client.config.MercantorClientConfigBuilder;
import de.d3adspace.mercantor.client.module.MercantorClientModule;

/**
 * Create new instances of the client.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientFactory {

    /**
     * Create a new client with a default config.
     *
     * @return The client.
     */
    public static IMercantorClient createMercantorClient() {
        MercantorClientConfig mercantorClientConfig = new MercantorClientConfigBuilder()
                .setServerHost(MercantorClientConstants.DEFAULT_SERVER_HOST)
                .setServerPort(MercantorClientConstants.DEFAULT_SERVER_PORT)
                .createMercantorClientConfig();

        return createMercantorClient(mercantorClientConfig);
    }

    /**
     * Create a new client by the given config.
     *
     * @param mercantorClientConfig The config.
     * @return The client.
     */
    public static IMercantorClient createMercantorClient(MercantorClientConfig mercantorClientConfig) {
        Preconditions.checkNotNull(mercantorClientConfig, "mercantorClientConfig cannot be null.");

        Injector injector = Guice.createInjector(new MercantorClientModule(mercantorClientConfig));
        return injector.getInstance(MercantorClientImpl.class);
    }
}
