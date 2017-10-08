package de.d3adspace.mercantor.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.module.MercantorServerModule;
import de.d3adspace.mercantor.server.resource.MercantorResource;
import de.d3adspace.mercantor.shared.io.ServiceBodyReader;
import de.d3adspace.mercantor.shared.io.ServiceBodyWriter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerImpl implements IMercantorServer {

    private final MercantorServerConfig mercantorServerConfig;
    private HttpServer httpServer;

    @Inject
    private MercantorResource mercantorResource;

    @Inject
    private ServiceBodyReader serviceBodyReader;

    @Inject
    private ServiceBodyWriter serviceBodyWriter;

    MercantorServerImpl(MercantorServerConfig mercantorServerConfig) {
        this.mercantorServerConfig = mercantorServerConfig;

        Injector injector = Guice.createInjector(new MercantorServerModule(mercantorServerConfig));
        injector.injectMembers(this);
    }

    @Override
    public void start() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(mercantorResource).register(serviceBodyReader).register(serviceBodyWriter);

        httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(mercantorServerConfig.getHost() + ":" + mercantorServerConfig.getPort()), resourceConfig);

        try {
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        httpServer.shutdown();
    }

    @Override
    public boolean isRunning() {
        return httpServer == null || httpServer.isStarted();
    }
}
