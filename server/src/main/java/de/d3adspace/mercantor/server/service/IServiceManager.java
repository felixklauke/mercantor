package de.d3adspace.mercantor.server.service;

import de.d3adspace.mercantor.shared.transport.IService;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public interface IServiceManager {

    IService getService(String role);

    void registerService(IService service);

    void updateService(String serviceKey);

    void removeService(String serviceKey);
}
