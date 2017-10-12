package de.d3adspace.mercantor.server.provider;

import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.service.repository.IServiceRepository;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceRepositoryProvider implements Provider<IServiceRepository> {

    @Inject
    private MercantorServerConfig config;

    @Override
    public IServiceRepository get() {
        IServiceRepository serviceRepository = null;

        try {
            serviceRepository = config.getServiceLookupMode().getImplementationClazz().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return serviceRepository;
    }
}
