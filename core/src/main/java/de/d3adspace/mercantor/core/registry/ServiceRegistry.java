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

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The registry for all known services.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class ServiceRegistry {

    /**
     * The map that contains all known services.
     */
    private static Map<String, Service> services;

    static {
        services = new ConcurrentHashMap<>();
    }

    /**
     * Register a service by its name.
     *
     * @param serviceKey The key of the service.
     * @param service The service itself.
     */
    public static void registerService(String serviceKey, Service service) {
        services.put(serviceKey, service);
    }

    /**
     * Remove a servie by its name.
     *
     * @param serviceKey The key of the service.
     */
    public static void removeService(String serviceKey) {
        services.remove(serviceKey);
    }

    /**
     * Check if there is a service with the given name.
     *
     * @param serviceKey The service key.
     *
     * @return if there is a service with a key.
     */
    public static boolean hasService(String serviceKey) {
        return services.containsKey(serviceKey);
    }

    /**
     * Get the service by its key.
     *
     * @param serviceKey The service key.
     *
     * @return The service.
     */
    public static Service getService(String serviceKey) {
        return services.get(serviceKey);
    }

    /**
     * Get a set of all known services.
     *
     * @return The services.
     */
    public static Set<Service> getServices() {
        return Collections.unmodifiableSet(new HashSet<>(services.values()));
    }

    /**
     * Remove the given service.
     *
     * @param service The service.
     */
    public static void removeService(Service service) {
        services.values().remove(service);
    }
}
