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

import de.d3adspace.mercantor.core.registry.Service;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public interface IMercantor {

    /**
     * Start the mercantor instance.
     */
    void start();

    /**
     * Stop the mercantor instance.
     */
    void stop();

    /**
     * Get a service by the given key.
     *
     * @param serviceKey The key of the service.
     *
     * @return The service.
     */
    Service getService(String serviceKey);

    /**
     * Remove a service by its key.
     *
     * @param serviceKey The key of the server.
     */
    void removeService(String serviceKey);

    /**
     * Create a new service by the given data.
     *
     * @param content The content of the service.
     *
     * @return The created service.
     */
    Service createService(String content);
}
