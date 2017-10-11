package de.d3adspace.mercantor.client.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import de.d3adspace.mercantor.client.config.MercantorClientConfig;
import de.d3adspace.mercantor.client.service.IServiceManager;
import de.d3adspace.mercantor.client.service.ServiceManagerImpl;
import de.d3adspace.mercantor.shared.io.ServiceBodyReader;
import de.d3adspace.mercantor.shared.io.ServiceBodyWriter;

/**
 * The dependency injection module.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorClientModule extends AbstractModule {

    /**
     * The instance of the config to provide.
     */
    private final MercantorClientConfig mercantorClientConfig;

    /**
     * Create a new module by the underlying config.
     *
     * @param mercantorClientConfig The config.
     */
    public MercantorClientModule(MercantorClientConfig mercantorClientConfig) {
        this.mercantorClientConfig = mercantorClientConfig;
    }

    @Override
    protected void configure() {
        bind(MercantorClientConfig.class).toInstance(mercantorClientConfig);
        bind(Gson.class).toInstance(new GsonBuilder().setPrettyPrinting().create());
        bind(ServiceBodyWriter.class);
        bind(ServiceBodyReader.class);
        bind(IServiceManager.class).to(ServiceManagerImpl.class);
    }
}
