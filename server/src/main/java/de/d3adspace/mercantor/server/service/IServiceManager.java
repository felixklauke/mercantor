package de.d3adspace.mercantor.server.service;

import de.d3adspace.mercantor.server.exception.IllegalServiceRegisteringException;
import de.d3adspace.mercantor.shared.transport.IService;

/**
 * Default interface for service management.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IServiceManager {

    /**
     * Get the service associated with the given role.
     *
     * @param role The role.
     * @return The service or null.
     */
    IService getService(String role);

    /**
     * Register the given service.
     *
     * @param service the service.
     */
    void registerService(IService service) throws IllegalServiceRegisteringException;

    /**
     * Handle a received heartbeat for the service with the given key.
     *
     * @param serviceKey The service key.
     */
    void updateService(String serviceKey);

    /**
     * Unregister the service with the given key.
     *
     * @param serviceKey The key of the service.
     */
    void removeService(String serviceKey);
}
