package de.d3adspace.mercantor.server.service.repository;

import de.d3adspace.mercantor.shared.transport.IService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class RoundRobinServiceRepository extends AbstractServiceRepository {

    private final AtomicInteger atomicInteger;

    public RoundRobinServiceRepository() {
        this(new AtomicInteger(0));
    }

    public RoundRobinServiceRepository(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @Override
    public IService getServiceByRole(String role) {
        if (getServices().isEmpty()) {
            return null;
        }

        if (atomicInteger.get() > getServices().size() - 1) {
            atomicInteger.set(0);
        }

        return getServices().get(atomicInteger.getAndIncrement());
    }
}
