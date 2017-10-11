package de.d3adspace.mercantor.client.service;

import de.d3adspace.mercantor.shared.transport.IService;

/**
 * The Manager that will monitor the current known services.
 *
 * Default implementation: {@link ServiceManagerImpl}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IServiceManager {

    /**
     * Register a new service with the given options.
     * <p>
     * This will also start the task that will update the service against the mercantor server.
     *
     * @param basePath The base path of the service.
     * @param role     The role the service is made for.
     * @return The created service.
     */
    IService registerService(String basePath, String role);

    /**
     * Unregister / Remove a given service.
     *
     * @param service The service.
     *
     * @return If the unregistering was successful.
     */
    boolean removeService(IService service);

    /**
     * Query for a service with the given role.
     *
     * @param role The role.
     *
     * @return The service or null.
     */
    IService getService(String role);
}
