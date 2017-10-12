package de.d3adspace.mercantor.server.service.repository.mode;

import de.d3adspace.mercantor.server.service.repository.IServiceRepository;
import de.d3adspace.mercantor.server.service.repository.RandomServiceRepository;
import de.d3adspace.mercantor.server.service.repository.RoundRobinServiceRepository;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public enum ServiceLookupMode {

    RANDOM(RandomServiceRepository.class),
    ROUND_ROBIN(RoundRobinServiceRepository.class);

    private final Class<? extends IServiceRepository> implementationClazz;

    ServiceLookupMode(Class<? extends IServiceRepository> implementationClazz) {
        this.implementationClazz = implementationClazz;
    }

    public Class<? extends IServiceRepository> getImplementationClazz() {
        return implementationClazz;
    }
}
