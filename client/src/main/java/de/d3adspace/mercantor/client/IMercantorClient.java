package de.d3adspace.mercantor.client;

import com.google.common.util.concurrent.ListenableFuture;
import de.d3adspace.mercantor.shared.transport.IService;

/**
 * The main interface of the client.
 *
 * Default implementation: {@link MercantorClientImpl}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IMercantorClient {

    /**
     * Register a new service with the given data.
     *
     * @param basePath The base path of the service.
     * @param role     The role of the service.
     * @return The future that will deliver the created service.
     */
    ListenableFuture<IService> registerService(String basePath, String role);

    /**
     * Unregister a given service.
     *
     * @param service the service.
     * @return If the unregistering was successful.
     */
    ListenableFuture<Boolean> unregisterService(IService service);

    /**
     * Query for a service with the given role.
     *
     * @param role The role.
     *
     * @return The future that will deliver the service or null.
     */
    ListenableFuture<IService> getService(String role);
}
