package de.d3adspace.mercantor.server.service.repository;

import de.d3adspace.mercantor.shared.transport.IService;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IServiceRepository {

    /**
     * Get a service by its role.
     *
     * @param role The role.
     * @return The service or null.
     */
    IService getServiceByRole(String role);

    /**
     * Register the given service.
     *
     * @param service The service.
     */
    void registerService(IService service);

    /**
     * Get the service that is registered for the given key.
     *
     * @param serviceKey The service key.
     * @return The service.
     */
    IService getServiceByKey(String serviceKey);

    /**
     * Unregister the given service.
     *
     * @param service The service.
     */
    void removeService(IService service);
}
