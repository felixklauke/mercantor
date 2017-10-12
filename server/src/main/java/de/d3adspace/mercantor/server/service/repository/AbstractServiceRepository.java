package de.d3adspace.mercantor.server.service.repository;

import com.google.common.collect.Lists;
import de.d3adspace.mercantor.shared.transport.IService;

import java.util.List;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public abstract class AbstractServiceRepository implements IServiceRepository {

    private final List<IService> services;

    public AbstractServiceRepository() {
        this(Lists.newLinkedList());
    }

    private AbstractServiceRepository(List<IService> services) {
        this.services = services;
    }

    @Override
    public void registerService(IService service) {
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

    List<IService> getServices() {
        return services;
    }
}
