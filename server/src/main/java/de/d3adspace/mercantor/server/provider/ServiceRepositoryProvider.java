package de.d3adspace.mercantor.server.provider;

import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.service.repository.IServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Dependency Injection Provider for the {@link IServiceRepository}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceRepositoryProvider implements Provider<IServiceRepository> {

    /**
     * The logger to log instance creation.
     */
    private final Logger logger = LoggerFactory.getLogger(ServiceRepositoryProvider.class);

    @Inject
    private MercantorServerConfig config;

    @Override
    public IServiceRepository get() {
        IServiceRepository serviceRepository = null;

        try {
            serviceRepository = config.getServiceLookupMode().getImplementationClazz().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error creating service repository. Used lookup mode: " + config.getServiceLookupMode(), e);
        }

        return serviceRepository;
    }
}
