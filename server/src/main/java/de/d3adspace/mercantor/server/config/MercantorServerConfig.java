package de.d3adspace.mercantor.server.config;

import java.util.concurrent.TimeUnit;

/**
 * The config for the server.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerConfig {

    /**
     * The host of the server.
     */
    private final String host;

    /**
     * The port of the service.
     */
    private final int port;

    /**
     * The expiration period of services.
     */
    private final long serviceExpiration;

    /**
     * The time unit of the service expiration.
     */
    private final TimeUnit serviceExpirationTimeUnit;

    /**
     * Create a new server config by all its data. Mainly used by the {@link MercantorServerConfigBuilder}.
     *
     * @param host                      The host of the server.
     * @param port                      The port of the server.
     * @param serviceExpiration         The expiration period of the services.
     * @param serviceExpirationTimeUnit The time unit of the expiration.
     */
    public MercantorServerConfig(String host, int port, long serviceExpiration, TimeUnit serviceExpirationTimeUnit) {
        this.host = host;
        this.port = port;
        this.serviceExpiration = serviceExpiration;
        this.serviceExpirationTimeUnit = serviceExpirationTimeUnit;
    }

    /**
     * Get the expiration period of the services.
     *
     * @return The expiration period of the services.
     */
    public long getServiceExpiration() {
        return serviceExpiration;
    }

    /**
     * Get the time unit of the expiration.
     *
     * @return The time unit of the expiration.
     */
    public TimeUnit getServiceExpirationTimeUnit() {
        return serviceExpirationTimeUnit;
    }

    /**
     * Get the port of the server.
     *
     * @return The port of the server.
     */
    public int getPort() {
        return port;
    }

    /**
     * Get the host of the server.
     *
     * @return The host of the server.
     */
    public String getHost() {
        return host;
    }
}
