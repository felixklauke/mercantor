package de.d3adspace.mercantor.server.service;

import com.google.common.collect.Maps;
import de.d3adspace.mercantor.server.MercantorServerConstants;
import de.d3adspace.mercantor.server.config.MercantorServerConfig;
import de.d3adspace.mercantor.server.exception.IllegalServiceRegisteringException;
import de.d3adspace.mercantor.server.service.repository.IServiceRepository;
import de.d3adspace.mercantor.shared.thread.PrefixedThreadFactory;
import de.d3adspace.mercantor.shared.transport.ExtendedServiceModel;
import de.d3adspace.mercantor.shared.transport.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of the {@link IServiceManager}.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class ServiceManagerImpl implements IServiceManager {

    /**
     * The logger to log all actions.
     */
    private final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);

    /**
     * All known services with their heart beats.
     */
    private final Map<String, Long> serviceHeartbeats;

    /**
     * The repository the services are saved in.
     */
    private final IServiceRepository serviceRepository;

    /**
     * The underlying config.
     */
    private final MercantorServerConfig mercantorServerConfig;

    @Inject
    public ServiceManagerImpl(IServiceRepository serviceRepository, MercantorServerConfig mercantorServerConfig) {
        this.serviceRepository = serviceRepository;
        this.mercantorServerConfig = mercantorServerConfig;
        this.serviceHeartbeats = Maps.newConcurrentMap();

        startCheckDeadServicesTask();
    }

    /**
     * Start the task that will mark dead services.
     */
    private void startCheckDeadServicesTask() {
        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor(new PrefixedThreadFactory(MercantorServerConstants.WORKER_THREAD_PREFIX));

        executorService.scheduleAtFixedRate(() -> {
            for (Map.Entry<String, Long> entry : this.serviceHeartbeats.entrySet()) {
                long heartbeat = entry.getValue();
                IService service = serviceRepository.getServiceByKey(entry.getKey());

                if (System.currentTimeMillis() - heartbeat > mercantorServerConfig.getServiceExpirationTimeUnit().toMillis(mercantorServerConfig.getServiceExpiration())) {
                    logger.info("The service {} for role {} at {} is bleeding hard and will be removed!", service.getServiceKey(), service.getRole(), service.getBasePath());

                    removeService(service.getServiceKey().toString());
                }
            }
        }, 0L, 1, TimeUnit.SECONDS);
    }

    @Override
    public IService getService(String role) {
        logger.info("Getting service for role {}.", role);

        return serviceRepository.getServiceByRole(role);
    }

    @Override
    public void registerService(IService service) throws IllegalServiceRegisteringException {
        logger.info("Registering service at {} for role {} with key {}.", service.getBasePath(), service.getRole(), service.getServiceKey());

        service.setServiceKey(UUID.randomUUID());

        serviceRepository.registerService(service);

        serviceHeartbeats.put(service.getServiceKey().toString(), System.currentTimeMillis());

        if (service instanceof ExtendedServiceModel) {
            ((ExtendedServiceModel) service).setExpirationTimeUnit(mercantorServerConfig.getServiceExpirationTimeUnit());
            ((ExtendedServiceModel) service).setExpiration(mercantorServerConfig.getServiceExpiration());
        }
    }

    @Override
    public void updateService(String serviceKey) {
        logger.info("Got heartbeat for service {}.", serviceKey);

        serviceHeartbeats.put(serviceKey, System.currentTimeMillis());
    }

    @Override
    public void removeService(String serviceKey) {
        logger.info("Removing service {}.", serviceKey);

        IService service = serviceRepository.getServiceByKey(serviceKey);

        serviceRepository.removeService(service);
        serviceHeartbeats.remove(serviceKey);
    }
}
