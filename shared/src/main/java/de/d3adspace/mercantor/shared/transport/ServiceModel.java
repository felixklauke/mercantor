package de.d3adspace.mercantor.shared.transport;

import java.util.UUID;

/**
 * The model for a service.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceModel implements IService {

    /**
     * The base path of the service.
     */
    private final String basePath;

    /**
     * The role of the service.
     */
    private final String role;

    /**
     * The unique service key.
     */
    private UUID serviceKey;

    /**
     * Create a new service with a random key.
     *
     * @param basePath The base path of the service.
     * @param role     The role of the service.
     */
    public ServiceModel(String basePath, String role) {
        this(basePath, role, UUID.randomUUID());
    }

    /**
     * Create a new service model by all its data.
     *
     * @param basePath The base path.
     * @param role The role of the service.
     * @param serviceKey The key of the service.
     */
    public ServiceModel(String basePath, String role, UUID serviceKey) {
        this.basePath = basePath;
        this.role = role;
        this.serviceKey = serviceKey;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getBasePath() {
        return basePath;
    }

    @Override
    public UUID getServiceKey() {
        return serviceKey;
    }
}
