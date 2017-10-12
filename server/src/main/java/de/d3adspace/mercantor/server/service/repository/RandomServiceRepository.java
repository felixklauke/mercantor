package de.d3adspace.mercantor.server.service.repository;

import de.d3adspace.mercantor.shared.transport.IService;

import java.util.Random;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class RandomServiceRepository extends AbstractServiceRepository {

    private final Random random;

    public RandomServiceRepository() {
        this(new Random());
    }

    public RandomServiceRepository(Random random) {
        this.random = random;
    }

    @Override
    public IService getServiceByRole(String role) {
        if (getServices().isEmpty()) {
            return null;
        }

        return getServices().get(random.nextInt() * getServices().size());
    }
}
