/*
 * Copyright (c) 2017 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.d3adspace.mercantor.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.d3adspace.mercantor.core.config.MercantorConfig;
import de.d3adspace.mercantor.core.model.ServiceModel;
import de.d3adspace.mercantor.core.registry.Service;
import de.d3adspace.mercantor.core.registry.ServiceRegistry;
import de.d3adspace.mercantor.core.resource.MercantorResource;
import de.d3adspace.mercantor.core.task.ServiceExpirationChecker;
import de.d3adspace.mercantor.core.thread.MercantorThreadFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The default implementation of the {@link IMercantor}.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class MercantorImpl implements IMercantor {

    /**
     * The config of the whole application.
     */
    private final MercantorConfig config;

    /**
     * The grizzly http server.
     */
    private HttpServer httpServer;

    /**
     * The gson instance used to parse requests.
     */
    private final Gson gson;

    /**
     * The executor service needed to check the expired services.
     */
    private final ScheduledExecutorService executorService;

    /**
     * Create a new mercantor impl by the config.
     *
     * @param config The config.
     */
    MercantorImpl(MercantorConfig config) {
        this.config = config;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        this.executorService = Executors.newSingleThreadScheduledExecutor(new MercantorThreadFactory());
    }

    @Override
    public void start() {
        URI uri = URI.create("http://" + config.getHost() + ":" + config.getPort());
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(new MercantorResource(this));

        httpServer = GrizzlyHttpServerFactory.createHttpServer(uri,resourceConfig);

        try {
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        executorService.scheduleAtFixedRate(new ServiceExpirationChecker(this), 0L, config.getServiceExpirationCheckInterval(), config.getServiceExpirationCheckIntervalTimeUnit());
    }

    @Override
    public void stop() {
        httpServer.shutdown();
    }

    @Override
    public Service getService(String serviceKey) {
        return ServiceRegistry.getService(serviceKey);
    }

    @Override
    public void removeService(String serviceKey) {
        ServiceRegistry.removeService(serviceKey);
    }

    @Override
    public Service createService(String content) {
        ServiceModel serviceModel = gson.fromJson(content, ServiceModel.class);
        return createService(serviceModel);
    }

    @Override
    public Service createService(ServiceModel serviceModel) {
        UUID uniqueId = UUID.randomUUID();
        return new Service(uniqueId, serviceModel.getBasePath());
    }

    @Override
    public void registerService(Service service) {
        ServiceRegistry.registerService(service.getServiceKey(), service);
    }

    @Override
    public void updateService(String serviceKey) {
        Service service = ServiceRegistry.getService(serviceKey);
        service.updateLastHeartBeat();
        service.setBleeding(false);
    }

    @Override
    public Set<Service> getServices() {
        return ServiceRegistry.getServices();
    }

    @Override
    public void checkService(Service service) {
        if (System.currentTimeMillis() - service.getLastHeartBeat() > config.getServiceExpirationTimeUnit().toMillis(config.getServiceExpiration())) {
            markServiceAsBleeding(service);
        }
    }

    @Override
    public void markServiceAsBleeding(Service service) {
        service.setBleeding(true);
    }

    @Override
    public void removeService(Service service) {
        ServiceRegistry.removeService(service);
    }
}
