package de.d3adspace.mercantor.shared.transport;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ExtendedServiceModel extends ServiceModel {

    private long expiration;
    private TimeUnit expirationTimeUnit;

    public ExtendedServiceModel(String basePath, String role, UUID serviceKey, long expiration, TimeUnit expirationTimeUnit) {
        super(basePath, role, serviceKey);
        this.expiration = expiration;
        this.expirationTimeUnit = expirationTimeUnit;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public TimeUnit getExpirationTimeUnit() {
        return expirationTimeUnit;
    }

    public void setExpirationTimeUnit(TimeUnit expirationTimeUnit) {
        this.expirationTimeUnit = expirationTimeUnit;
    }
}
