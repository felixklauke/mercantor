package de.d3adspace.mercantor.core.config;

import java.util.concurrent.TimeUnit;

public class MercantorConfigBuilder {
    private String host;
    private int port;
    private long serviceExpiration;
    private TimeUnit serviceExpirationTimeUnit;
    private long serviceExpirationCheckInterval;
    private TimeUnit serviceExpirationCheckIntervalTimeUnit;

    public MercantorConfigBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public MercantorConfigBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public MercantorConfigBuilder setServiceExpiration(long serviceExpiration) {
        this.serviceExpiration = serviceExpiration;
        return this;
    }

    public MercantorConfigBuilder setServiceExpirationTimeUnit(TimeUnit serviceExpirationTimeUnit) {
        this.serviceExpirationTimeUnit = serviceExpirationTimeUnit;
        return this;
    }

    public MercantorConfigBuilder setServiceExpirationCheckInterval(long serviceExpirationCheckInterval) {
        this.serviceExpirationCheckInterval = serviceExpirationCheckInterval;
        return this;
    }

    public MercantorConfigBuilder setServiceExpirationCheckIntervalTimeUnit(TimeUnit serviceExpirationCheckIntervalTimeUnit) {
        this.serviceExpirationCheckIntervalTimeUnit = serviceExpirationCheckIntervalTimeUnit;
        return this;
    }

    public MercantorConfig createMercantorConfig() {
        return new MercantorConfig(host, port, serviceExpiration, serviceExpirationTimeUnit, serviceExpirationCheckInterval, serviceExpirationCheckIntervalTimeUnit);
    }
}