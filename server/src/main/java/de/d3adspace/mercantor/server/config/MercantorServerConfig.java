package de.d3adspace.mercantor.server.config;

import java.util.concurrent.TimeUnit;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class MercantorServerConfig {

    private final String host;
    private final int port;

    private final long serviceExpiration;
    private final TimeUnit serviceExpirationTimeUnit;

    public MercantorServerConfig(String host, int port, long serviceExpiration, TimeUnit serviceExpirationTimeUnit) {
        this.host = host;
        this.port = port;
        this.serviceExpiration = serviceExpiration;
        this.serviceExpirationTimeUnit = serviceExpirationTimeUnit;
    }

    public long getServiceExpiration() {
        return serviceExpiration;
    }

    public TimeUnit getServiceExpirationTimeUnit() {
        return serviceExpirationTimeUnit;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
