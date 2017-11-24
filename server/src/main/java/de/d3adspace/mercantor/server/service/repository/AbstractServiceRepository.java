package de.d3adspace.mercantor.server.service.repository;

import com.google.common.collect.Lists;
import de.d3adspace.mercantor.server.exception.IllegalServiceRegisteringException;
import de.d3adspace.mercantor.shared.transport.IService;

import java.util.List;

/**
 * Abstraction for the {@link IServiceRepository}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public abstract class AbstractServiceRepository implements IServiceRepository {

    /**
     * All known services.
     */
    private final List<IService> services;

    /**
     * Create a new service repository with default data.
     */
    public AbstractServiceRepository() {
        this(Lists.newCopyOnWriteArrayList());
    }

    /**
     * Create a new service repository by the given data.
     *
     * @param services The current services.
     */
    private AbstractServiceRepository(List<IService> services) {
        this.services = services;
    }

    @Override
    public void registerService(IService service) throws IllegalServiceRegisteringException {
        services.add(service);
    }

    @Override
    public IService getServiceByKey(String serviceKey) {
        return services.stream().filter(service -> service.getServiceKey().toString().equalsIgnoreCase(serviceKey)).findFirst().orElse(null);
    }

    @Override
    public void removeService(IService service) {
        services.remove(service);
    }

    /**
     * Get all known services.
     *
     * @return The services.
     */
    List<IService> getServices() {
        return services;
    }
}
