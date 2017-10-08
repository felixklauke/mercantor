package de.d3adspace.mercantor.shared.transport;

import java.util.UUID;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceModel implements IService {

    private final String basePath;
    private final String role;
    private UUID serviceKey;

    public ServiceModel(String basePath, String role) {
        this(basePath, role, UUID.randomUUID());
    }

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
