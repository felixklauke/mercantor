package de.d3adspace.mercantor.shared.transport;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Extension of the {@link ServiceModel} to provide the expiration data in the server settings.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ExtendedServiceModel extends ServiceModel {

    /**
     * The expiration period of services.
     */
    private long expiration;

    /**
     * The time unit of the expiration.
     */
    private TimeUnit expirationTimeUnit;

    /**
     * Create a new extended service model by all its data.
     *
     * @param basePath           The base path of the service.
     * @param role               The role of the service.
     * @param serviceKey         the key of the service given by the server.
     * @param expiration         The expiration period of the service.
     * @param expirationTimeUnit The time unit of the expiration.
     */
    public ExtendedServiceModel(String basePath, String role, UUID serviceKey, long expiration, TimeUnit expirationTimeUnit) {
        super(basePath, role, serviceKey);
        this.expiration = expiration;
        this.expirationTimeUnit = expirationTimeUnit;
    }

    /**
     * Get the expiration period of the service.
     *
     * @return The expiration period.
     */
    public long getExpiration() {
        return expiration;
    }

    /**
     * Set the expiration period of the service.
     *
     * @param expiration The expiration period.
     */
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    /**
     * Get the time unit of the expiration.
     *
     * @return The time unit.
     */
    public TimeUnit getExpirationTimeUnit() {
        return expirationTimeUnit;
    }

    /**
     * Set the time unit of the expiration.
     *
     * @param expirationTimeUnit The time unit.
     */
    public void setExpirationTimeUnit(TimeUnit expirationTimeUnit) {
        this.expirationTimeUnit = expirationTimeUnit;
    }
}
