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

package de.d3adspace.mercantor.core.registry;

import java.util.UUID;

/**
 * Represents a service that is known to mercantor.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class Service {

    /**
     * The key of the service.
     */
    private final UUID uniqueId;

    /**
     * The base path of the service.
     */
    private final String basePath;

    /**
     * The timestamp of the last heart beat.
     */
    private long lastHeartBeat;
    private boolean bleeding;

    /**
     * Create a new service by its unique id and its path.
     *
     * @param uniqueId The unique id.
     * @param basePath The base path.
     */
    public Service(UUID uniqueId, String basePath) {
        this.uniqueId = uniqueId;
        this.basePath = basePath;

        updateLastHeartBeat();
    }

    /**
     * Get the service key.
     *
     * @return The key of the service.
     */
    public String getServiceKey() {
        return uniqueId.toString();
    }

    /**
     * Get the base path of the service.
     *
     * @return The base path of the service.
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * Update the last heart beat.
     */
    public void updateLastHeartBeat() {
        lastHeartBeat = System.currentTimeMillis();
    }

    /**
     * Get the time stamp of the last heart beat.
     *
     * @return The time stamp of the last heart beat.
     */
    public long getLastHeartBeat() {
        return lastHeartBeat;
    }

    /**
     * Check if the service is bleeding.
     *
     * @return If the service is bleeding.
     */
    public boolean isBleeding() {
        return bleeding;
    }

    /**
     * Update if the server is bleeding.
     *
     * @param bleeding If the service is bleeding.
     */
    public void setBleeding(boolean bleeding) {
        this.bleeding = bleeding;
    }
}
