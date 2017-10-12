package de.d3adspace.mercantor.server.config;

import de.d3adspace.mercantor.server.service.repository.mode.ServiceLookupMode;

import java.util.concurrent.TimeUnit;

public class MercantorServerConfigBuilder {
    private String host;
    private int port;
    private long serviceExpiration;
    private TimeUnit serviceExpirationTimeUnit;
    private ServiceLookupMode serviceLookupMode;

    public MercantorServerConfigBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public MercantorServerConfigBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public MercantorServerConfigBuilder setServiceExpiration(long serviceExpiration) {
        this.serviceExpiration = serviceExpiration;
        return this;
    }

    public MercantorServerConfigBuilder setServiceExpirationTimeUnit(TimeUnit serviceExpirationTimeUnit) {
        this.serviceExpirationTimeUnit = serviceExpirationTimeUnit;
        return this;
    }

    public MercantorServerConfigBuilder setServiceLookupMode(ServiceLookupMode serviceLookupMode) {
        this.serviceLookupMode = serviceLookupMode;
        return this;
    }

    public MercantorServerConfig createMercantorServerConfig() {
        return new MercantorServerConfig(host, port, serviceExpiration, serviceExpirationTimeUnit, serviceLookupMode);
    }
}