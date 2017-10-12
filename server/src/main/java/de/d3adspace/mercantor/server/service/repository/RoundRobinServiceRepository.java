package de.d3adspace.mercantor.server.service.repository;

import de.d3adspace.mercantor.shared.transport.IService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Will select a known services in a round robin row.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class RoundRobinServiceRepository extends AbstractServiceRepository {

    /**
     * The count id of the next selected services.
     */
    private final AtomicInteger atomicInteger;

    /**
     * Create a new round robin based repository with a default starting point.
     */
    public RoundRobinServiceRepository() {
        this(new AtomicInteger(0));
    }

    /**
     * Create a new round robin based repository with the given start count id.
     *
     * @param atomicInteger The start count id.
     */
    private RoundRobinServiceRepository(AtomicInteger atomicInteger) {
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
