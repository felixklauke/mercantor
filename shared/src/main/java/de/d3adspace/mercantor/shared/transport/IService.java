package de.d3adspace.mercantor.shared.transport;

import java.util.UUID;

/**
 * The default interface of the service. Implemented by the {@link ServiceModel} and extended by the {@link ExtendedServiceModel}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IService {

    /**
     * The unique key of the service.
     *
     * @return The key.
     */
    UUID getServiceKey();

    /**
     * Set the key of the service.
     *
     * @param serviceKey The key of the service.
     */
    void setServiceKey(UUID serviceKey);

    /**
     * The base path of the service.
     *
     * @return The base path.
     */
    String getBasePath();

    /**
     * Get the role of the service.
     *
     * @return The role of the service.
     */
    String getRole();
}
