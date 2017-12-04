package de.d3adspace.mercantor.server.service.repository.mode;

import de.d3adspace.mercantor.server.service.repository.IServiceRepository;
import de.d3adspace.mercantor.server.service.repository.RandomServiceRepository;
import de.d3adspace.mercantor.server.service.repository.RoundRobinServiceRepository;
import de.d3adspace.mercantor.server.service.repository.SingleServiceRepository;

/**
 * The mode how the services will be lookup up their role.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public enum ServiceLookupMode {

    RANDOM(RandomServiceRepository.class),
    ROUND_ROBIN(RoundRobinServiceRepository.class),
    SINGLE(SingleServiceRepository.class);

    /**
     * The class that implements the given mode.
     */
    private final Class<? extends IServiceRepository> implementationClazz;

    ServiceLookupMode(Class<? extends IServiceRepository> implementationClazz) {
        this.implementationClazz = implementationClazz;
    }

    /**
     * Get the class that implements the given mode.
     *
     * @return The class that implements the given mode.
     */
    public Class<? extends IServiceRepository> getImplementationClazz() {
        return implementationClazz;
    }

    @Override
    public String toString() {
        return "ServiceLookupMode{" +
                "implementationClazz=" + implementationClazz +
                '}';
    }
}
