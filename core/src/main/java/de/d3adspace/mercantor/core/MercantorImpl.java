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

import de.d3adspace.mercantor.core.config.MercantorConfig;
import de.d3adspace.mercantor.core.registry.Service;
import de.d3adspace.mercantor.core.registry.ServiceRegistry;
import de.d3adspace.mercantor.core.resource.MercantorResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

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
     * Create a new mercantor impl by the config.
     *
     * @param config The config.
     */
    MercantorImpl(MercantorConfig config) {
        this.config = config;
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
}
