package de.d3adspace.mercantor.client.service;

import de.d3adspace.mercantor.shared.transport.IService;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IServiceManager {

    IService registerService(String basePath, String role);

    boolean removeService(IService service);

    IService getService(String role);
}
