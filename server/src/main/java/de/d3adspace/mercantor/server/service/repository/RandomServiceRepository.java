package de.d3adspace.mercantor.server.service.repository;

import de.d3adspace.mercantor.shared.transport.IService;

import java.util.Random;

/**
 * Will select a known services based on a random number.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class RandomServiceRepository extends AbstractServiceRepository {

    /**
     * The random to generate the numbers.
     */
    private final Random random;

    /**
     * Create a new random based repository by a default random.
     */
    public RandomServiceRepository() {
        this(new Random());
    }

    /**
     * Create a new random base repository on a given random.
     *
     * @param random The random.
     */
    private RandomServiceRepository(Random random) {
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
